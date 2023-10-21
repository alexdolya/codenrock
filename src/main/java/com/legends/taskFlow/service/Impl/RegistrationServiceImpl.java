package com.legends.taskFlow.service.Impl;

import com.legends.taskFlow.manager.MailManager;
import com.legends.taskFlow.model.entity.User;
import com.legends.taskFlow.model.enums.Role;
import com.legends.taskFlow.repositorty.UserRepository;
import com.legends.taskFlow.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.legends.taskFlow.model.dto.RegistrationDTO;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegistrationServiceImpl implements RegistrationService {
    private final MailManager mailManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public void registration(RegistrationDTO registrationDTO) {
        log.info("Входящий запрос на регистрацию: {}", registrationDTO);
        if (validateRequest(registrationDTO)) {
            int verificationCode = generateVerificationCode();
            String encodedPassword = bCryptPasswordEncoder.encode(registrationDTO.getPassword());
            User user = userRepository.save(new User()
                    .setFirstName(registrationDTO.getName())
                    .setLastName(registrationDTO.getLastName())
                    .setPassword(encodedPassword)
                    .setEmail(registrationDTO.getEmail())
                    .setVerifyCode(verificationCode)
                    .setCreatedDate(LocalDate.now())
                    .setVerifyStatus(false)
                    .setRole(Role.ROLE_USER)
            );
            mailManager.sendVerificationCode(user.getUserId(), verificationCode, registrationDTO.getEmail());
            log.info("Регистрация прошла успешна, письмо с кодом верификации отправлено");
        } else throw new RuntimeException("Запрос на регистрацию не валидирован");
    }

    public void verify(Integer userId, Integer verificationCode) {
        log.info("Запрос на верификацию от пользователя с Id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Пользователь с данным id не найден"));
        if (verificationCode.equals(user.getVerifyCode())) {
            user.setVerifyStatus(true);
            log.info("Верификация прошла успешно");
        }
        userRepository.save(user);
    }

    private boolean validateRequest(RegistrationDTO registrationDTO) {
        Optional<User> user = userRepository.findByEmail(registrationDTO.getEmail());
        if (user.isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        String emailPattern = "[\\w\\.]{2,50}@[\\w\\.]{2,20}";
        if (registrationDTO.getEmail() == null || !Pattern.matches(emailPattern, registrationDTO.getEmail())) {
            throw new RuntimeException("Неверный email");
        }
        if (registrationDTO.getPassword().length() < 6) {
            throw new RuntimeException("Пароль должен состоять из 6 или более символов");
        }
        log.info("Валидация запроса на регистрацию пользователя с email: {} прошла успешно", registrationDTO.getEmail());

        return true;
    }

    private int generateVerificationCode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}
