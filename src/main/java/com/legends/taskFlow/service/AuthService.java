package com.legends.taskFlow.service;

import com.legends.taskFlow.model.dto.AuthRequestDTO;
import com.legends.taskFlow.model.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO auth(AuthRequestDTO authRequestDTO);

    AuthResponseDTO refresh(String refreshToken);
}
