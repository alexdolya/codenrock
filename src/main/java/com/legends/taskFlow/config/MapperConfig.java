package com.legends.taskFlow.config;

import com.legends.taskFlow.mapper.TaskMapper;
import com.legends.taskFlow.mapper.UserMapper;
import com.legends.taskFlow.model.dto.TaskDTO;
import com.legends.taskFlow.model.entity.Status;
import com.legends.taskFlow.model.entity.Task;
import com.legends.taskFlow.model.entity.User;
import com.legends.taskFlow.model.enums.StatusEnum;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public TaskMapper taskMapper() {
        return Mappers.getMapper(TaskMapper.class);
    }

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
