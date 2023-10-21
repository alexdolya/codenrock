package com.legends.taskFlow.model.dto;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String secondName;
}
