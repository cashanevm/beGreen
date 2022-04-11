package ua.knu.beGreen.service.api;

import ua.knu.beGreen.service.model.UserModel;

public interface MailingService {
    void send(String emailTo, String subject, String message);

    void sendMessage(UserModel userModel, String domain);
}
