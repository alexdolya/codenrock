package com.legends.taskFlow.controller;

import com.legends.taskFlow.model.dto.AuthRequestDTO;
import com.legends.taskFlow.model.dto.AuthResponseDTO;
import com.legends.taskFlow.model.dto.RegistrationDTO;
import com.legends.taskFlow.model.dto.SetRoleDTO;
import com.legends.taskFlow.model.entity.User;
import com.legends.taskFlow.repositorty.UserRepository;
import com.legends.taskFlow.service.AuthService;
import com.legends.taskFlow.service.Impl.UserServiceImpl;
import com.legends.taskFlow.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;
    private final AuthService authService;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;


    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/registration")
    public void registration(@RequestBody RegistrationDTO registrationDTO) {
        log.info("Registration request: " + registrationDTO);
        registrationService.registration(registrationDTO);
    }

    @Operation(summary = "Верификация email пользователя")
    @PostMapping("/verification/{userId}")
    public void verify(@PathVariable Integer userId, @RequestBody Integer verificationCode) {
        registrationService.verify(userId, verificationCode);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/auth")
    public AuthResponseDTO auth(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            User user = userRepository.findByEmail("alexdolya90@yandex.ru").orElseThrow();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getLogin(), authRequestDTO.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Wrong email or password");
        }
        return authService.auth(authRequestDTO);
    }

    @Operation(summary = "Получение нового access токена с помощью refresh токена")
    @PostMapping("/refresh-token")
    public AuthResponseDTO refreshToken(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @Operation(summary = "Установить роль для пользователя")
    @PatchMapping("/admin/setrole")
    public void setRoleForUser(@RequestBody SetRoleDTO setRoleDTO) {
        userService.setRoleForUser(setRoleDTO);
    }

    @Operation(summary = "Удалить пользователя")
    @PatchMapping("/admin/deleteuser")
    public void deleteUser(@RequestBody String email) {
        userService.deleteUser(email);
    }
}
