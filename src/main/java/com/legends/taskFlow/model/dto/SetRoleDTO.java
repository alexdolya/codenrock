package com.legends.taskFlow.model.dto;

import com.legends.taskFlow.model.enums.Role;
import lombok.Data;

@Data
public class SetRoleDTO {
    private String email;
    private Role role;
}
