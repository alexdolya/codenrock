package com.legends.taskFlow.model.dto;

import com.legends.taskFlow.model.entity.Project;
import com.legends.taskFlow.model.entity.Task;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CommentDTO {

    private Long commentId;

    private String content;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Task task;

    private Project project;

    private UserDTO user;
}
