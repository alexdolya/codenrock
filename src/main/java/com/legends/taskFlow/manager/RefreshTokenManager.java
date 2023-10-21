package com.legends.taskFlow.manager;

import com.legends.taskFlow.model.entity.User;
import com.legends.taskFlow.repositorty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenManager {
    @Value("${jwt.refreshTokenLifetime}")
    private Long refreshTokenLifetime;
    private final UserRepository userRepository;

    @Modifying
    public String createRefreshToken(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setExpirationDateToken(LocalDateTime.now().plusSeconds(refreshTokenLifetime / 1000));
        String refreshToken = UUID.randomUUID().toString();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        return refreshToken;
    }

}
