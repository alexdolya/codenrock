package com.legends.taskFlow.mapper;

import com.legends.taskFlow.model.dto.TaskDTO;
import com.legends.taskFlow.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface TaskMapper {

    @Mapping(source = "task.taskStatus.name", target = "taskDTO.taskStatus")
    @Mapping(source = "task.assignedTo.jobTitle.nameJobTitle", target = "taskDTO.assignedTo.jobTitle")
    @Mapping(source = "task.assignedTo.department.nameDepartment", target = "taskDTO.assignedTo.department")
    @Mapping(source = "task.project.projectId", target = "taskDTO.projectId")
//    @Mapping(source = "task.parent.taskId", target = "taskDTO.parentId")
    TaskDTO mapTaskToTaskDTO(Task task, @MappingTarget TaskDTO taskDTO);

}
