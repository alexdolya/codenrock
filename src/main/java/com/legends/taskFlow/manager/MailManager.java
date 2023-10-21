package com.legends.taskFlow.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailManager {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;

    public void sendVerificationCode(Integer userId, int verificationCode, String emailAddress) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(username);
            message.setTo(emailAddress);
            message.setSubject("Код верификации в сервисе TaskFlow");
            message.setText("Ваш код верификации: " + verificationCode + ". Проследуйте по ссылке и введите код https://cnrprod-team-58439.nh2023.codenrock.com/verify/" + userId);
            javaMailSender.send(message);
        } catch (MailException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        log.info("Message sent to: {}", emailAddress);
    }
}