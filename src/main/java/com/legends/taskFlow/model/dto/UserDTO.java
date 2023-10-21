package com.legends.taskFlow.model.dto;

import com.legends.taskFlow.model.entity.JobTitle;
import com.legends.taskFlow.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO {

    private Integer userId;

    private String jobTitle;

    private String firstName;

    private String lastName;

    private String email;

    private String department;

    private String telephone;

    private LocalDate createdDate;

    private LocationDTO location;

    private Role role;

    private String propertyDescription;
}



