package com.legends.taskFlow.service;

import com.legends.taskFlow.model.dto.TaskDTO;
import com.legends.taskFlow.model.entity.Task;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TaskService {

    void createNewTask(TaskDTO taskDTO);

    TaskDTO findTaskById(Long id);

    Task findTaskEntityById(Long id);

    List<TaskDTO> findAll(PageRequest pageRequest);

    List<TaskDTO> findAllByUserId(Integer id);
}
