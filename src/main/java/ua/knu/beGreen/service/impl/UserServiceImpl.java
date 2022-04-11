package ua.knu.beGreen.service.impl;

import ua.knu.beGreen.persistence.entity.Role;
import ua.knu.beGreen.service.api.MailingService;
import ua.knu.beGreen.service.mapper.ServiceLayerMapper;
import ua.knu.beGreen.service.model.UserModel;
import ua.knu.beGreen.persistence.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    private final MailingService mailingServiceImpl;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username).orElse(null);
    }

    public UserModel saveUser(UserModel model) {
       return ServiceLayerMapper.I.toUserModel(userRepository.save(ServiceLayerMapper.I.toUserEntity(model)));
    }

    public Optional<UserModel> findByUsername(String userName) {
        return userRepository.findByUsername(userName).map(ServiceLayerMapper.I::toUserModel);
    }
    public Optional<UserModel> findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode).map(ServiceLayerMapper.I::toUserModel);
    }

    public boolean addUser(UserModel userModel) {
        if (this.findByUsername(userModel.getUsername()).isPresent()) {
            return false;
        }
        userModel.setActive(true);
        userModel.setRoles(Collections.singleton(Role.USER));
        userModel.setActivationCode(UUID.randomUUID().toString());
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        this.saveUser(userModel);

        return true;
    }

    public boolean activateUser(String code) {
        UserModel userModel = this.findByActivationCode(code).orElse(null);

        if (userModel == null) {
            return false;

        }
        userModel.setActivationCode(null);

        this.saveUser(userModel);

        return true;
    }

    public void deleteUser(UserModel userModel) {
        userRepository.delete(ServiceLayerMapper.I.toUserEntity(userModel));
    }

    public List<UserModel> findAll() {
        return userRepository.findAll().stream().map(ServiceLayerMapper.I::toUserModel).collect(Collectors.toList());
    }

    public void saveUserj(UserModel userModel, String username, Map<String, String> form) {
        userModel.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        userModel.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                userModel.getRoles().add(Role.valueOf(key));
            }
        }

        this.saveUser(userModel);
    }

    public void updateProfile(UserModel userModel, String password, String email) {
        String userEmail = userModel.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            userModel.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                userModel.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            userModel.setPassword(password);
        }

        this.saveUser(userModel);

        if (isEmailChanged) {
            sendMessage(userModel);
        }
    }
//need to remove
    private void sendMessage(UserModel userModel) {
        if (!StringUtils.isEmpty(userModel.getEmail())) {
            String message = String.format(
                    "Hello, %s! Welcome to Be Green. Please, visit next link: http://localhost:8080/be-green/app/auth/activate/%s",
                    userModel.getUsername(),
                    userModel.getActivationCode()
            );
            mailingServiceImpl.send(userModel.getEmail(), "Activation code", message);
        }
    }
}
