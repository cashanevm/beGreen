package ua.knu.beGreen.service.api.impl;

import ua.knu.beGreen.service.api.MailingService;
import ua.knu.beGreen.service.model.UserModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
    @Override
    public void sendMessage(UserModel userModel, String domain) {
        if (!StringUtils.isEmpty(userModel.getEmail())) {
            String message = String.format(
                    "Hello, %s! Welcome to Be Green. Please, visit next link: %s/be-green/app/auth/activate/%s",
                    userModel.getUsername(),
                    domain,
                    userModel.getActivationCode()
            );
            this.send(userModel.getEmail(), "Activation code", message);
        }
    }
}
