package com.legends.taskFlow.model.dto;

import com.legends.taskFlow.model.enums.Priority;
import com.legends.taskFlow.model.enums.StatusEnum;
import com.legends.taskFlow.model.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long taskId;

    private String subject;
    private String description;

    private TaskType taskType;

    private LocalDate startPlanDate;
    private LocalDate endPlanDate;

    private LocalDate startOn;
    private LocalDate endOn;

    private Priority priority;

    private StatusEnum taskStatus;

    private LocalDate creationDate;

    //private UserDTO parent;
    private Long parentId;
    private UserDTO assignedTo;
    //private Integer assignedToId;

    //private ProjectDTO project;
    private Long projectId;

    private String estimateTimePlan;
    private String estimateTimeFact;
}
