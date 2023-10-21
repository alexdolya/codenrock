package com.legends.taskFlow.service.Impl;

import com.legends.taskFlow.exception.EndDateBeforeStartDateException;
import com.legends.taskFlow.exception.EntityNotExistsException;
import com.legends.taskFlow.exception.NotFoundException;
import com.legends.taskFlow.mapper.TaskMapper;
import com.legends.taskFlow.model.dto.TaskDTO;
import com.legends.taskFlow.model.entity.Task;
import com.legends.taskFlow.repositorty.ProjectRepository;
import com.legends.taskFlow.repositorty.TaskRepository;
import com.legends.taskFlow.repositorty.UserRepository;
import com.legends.taskFlow.service.StatusService;
import com.legends.taskFlow.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {

    private final ModelMapper mapper;

    private final TaskMapper taskMapper;

    private final StatusService statusService;

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private void validateTaskDates(@NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        if (endDate.isBefore(startDate) || endDate.isBefore(LocalDate.now())) {
            throw new EndDateBeforeStartDateException("Task's end date is before start date or today");
        }
    }

    public void createNewTask(TaskDTO taskDTO) {
        validateTaskDates(taskDTO.getStartPlanDate(), taskDTO.getEndPlanDate());

        Task task = mapper.map(taskDTO, Task.class);
//        task.setParent(taskRepository.findById(taskDTO.getParentId()).orElseThrow(() -> new EntityNotExistsException("Task with id " + taskDTO.getParentId() + " does not exists")));
        task.setAssignedTo(userRepository.findById(taskDTO.getAssignedTo().getUserId()).orElseThrow(() -> new EntityNotExistsException("User with id " + taskDTO.getAssignedTo().getUserId() + " does not exists")));
        task.setProject(projectRepository.findById(taskDTO.getProjectId()).orElseThrow(() -> new EntityNotExistsException("Project with id " + taskDTO.getProjectId() + " does not exists")));
        task.setTaskStatus(statusService.findProjectStatus(taskDTO.getTaskStatus()));
        task.setCreationOn(LocalDateTime.now());
        task.setStartPlanDate(taskDTO.getStartPlanDate().atStartOfDay())                ;
        task.setEndPlanDate(taskDTO.getEndPlanDate().atStartOfDay());

        taskRepository.save(task);
        log.info("Задача успешно создана");
    }

    public TaskDTO findTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
        (new TaskDTO()).setTaskId(task.getTaskId());
        return taskMapper.mapTaskToTaskDTO(task, new TaskDTO());
    }

    public Task findTaskEntityById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
    }

    public List<TaskDTO> findAll(PageRequest pageRequest) {
        List<Task> tasks = taskRepository.findAllByOrderByCreationOnDesc(pageRequest);
        return mapper.map(tasks, new TypeToken<List<TaskDTO>>() {}.getType());
    }

    public List<TaskDTO> findAllByUserId(Integer id) {
        return mapper.map(taskRepository.findAllByAssignedTo(userRepository.findById(id).orElseThrow(() -> new EntityNotExistsException("User with id " + id + " does not exists"))),
                new TypeToken<List<TaskDTO>>() {}.getType());
    }
}
