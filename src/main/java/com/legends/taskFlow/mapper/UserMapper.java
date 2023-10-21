package com.legends.taskFlow.mapper;

import com.legends.taskFlow.model.dto.UserDTO;
import com.legends.taskFlow.model.entity.Department;
import com.legends.taskFlow.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    @Mapping(source = "jobTitle", target = "jobTitle.nameJobTitle")
    @Mapping(source = "location.city", target = "location.city")
    @Mapping(source = "location.country", target = "location.country")
    @Mapping(source = "department", target = "department.nameDepartment")
    User mapUserDTOToUser(UserDTO userDTO, @MappingTarget User user);

}
