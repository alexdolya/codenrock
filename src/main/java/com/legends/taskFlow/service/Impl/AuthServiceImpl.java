package com.legends.taskFlow.service.Impl;

import com.legends.taskFlow.exception.NotFoundException;
import com.legends.taskFlow.manager.JwtManager;
import com.legends.taskFlow.manager.RefreshTokenManager;
import com.legends.taskFlow.model.dto.AuthRequestDTO;
import com.legends.taskFlow.model.dto.AuthResponseDTO;
import com.legends.taskFlow.model.entity.User;
import com.legends.taskFlow.repositorty.UserRepository;
import com.legends.taskFlow.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final UserServiceImpl userService;
    private final JwtManager jwtManager;

    private final RefreshTokenManager refreshTokenManager;

    public AuthResponseDTO auth(AuthRequestDTO authRequestDTO) {
        log.info("Запрос авторизации: {}", authRequestDTO);
        User user = userRepository.findByEmail(authRequestDTO.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        UserDetails userDetails = userService.loadUserByUsername(authRequestDTO.getLogin());
        String accessToken = jwtManager.generateToken(userDetails);
        String refreshToken = refreshTokenManager.createRefreshToken(authRequestDTO.getLogin());
        log.info("Токены сгенерированы");

        return new AuthResponseDTO()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setUserId(user.getUserId())
                .setRole(user.getRole().name());
    }

    public AuthResponseDTO refresh(String refreshToken) {
        log.info("Запрос на обновление токена");
        User user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new NotFoundException("Refresh token has not found in DB"));
        String token = user.getRefreshToken();
        LocalDateTime expirationDateToken = user.getExpirationDateToken();
        if (LocalDateTime.now().isBefore(expirationDateToken)) {
            UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
            String accessToken = jwtManager.generateToken(userDetails);

            return new AuthResponseDTO()
                    .setAccessToken(accessToken)
                    .setRefreshToken(token)
                    .setUserId(user.getUserId())
                    .setRole(user.getRole().name());
        }
        else {
            throw new RuntimeException("Refresh token expiated, please sign in");
        }
    }
}
