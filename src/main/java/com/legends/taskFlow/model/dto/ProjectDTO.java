package com.legends.taskFlow.model.dto;

import com.legends.taskFlow.model.enums.ProjectType;
import com.legends.taskFlow.model.enums.StatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProjectDTO {

    private Long projectId;

    private String projectTitle;

    private ProjectType projectType;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDate startDateFact;
    private LocalDate endDateFact;

    private String projectDescription;

    private StatusEnum projectStatus;

    private Integer projectOwnerId;

    private List<CommentDTO> comments;

    private List<TaskDTO> tasks;

    private LocalDate creationDate;
}
