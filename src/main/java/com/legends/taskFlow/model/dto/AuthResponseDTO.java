package com.legends.taskFlow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    private String role;
}
