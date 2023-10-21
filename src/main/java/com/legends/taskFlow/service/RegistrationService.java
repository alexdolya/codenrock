package com.legends.taskFlow.service;

import com.legends.taskFlow.model.dto.RegistrationDTO;

public interface RegistrationService {
    void registration(RegistrationDTO registrationDTO);

    void verify(Integer userId, Integer verificationCode);
}
