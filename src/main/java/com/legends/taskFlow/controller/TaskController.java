package com.legends.taskFlow.controller;

import com.legends.taskFlow.model.dto.TaskDTO;
import com.legends.taskFlow.service.Impl.TaskServiceImpl;
import com.legends.taskFlow.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="Task")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Создание задачи")
    @PostMapping("/create/task")
    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.createNewTask(taskDTO);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Получение задачи по id")
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findTaskById(id));
    }

    @Operation(summary = "Получение всех задач")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTasks( @RequestParam (required = false, defaultValue = "0") int page,
                                                   @RequestParam (required = false, defaultValue = "10") int size)   {
        return ResponseEntity.ok(taskService.findAll(PageRequest.of(page, size)));
    }

    @Operation(summary = "Получение всех задач пользователя")
    @GetMapping("user/tasks/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable Integer userId) {
        return ResponseEntity.ok(taskService.findAllByUserId(userId));
    }

//    @PatchMapping("/edit/task") //todo create!
//    public ResponseEntity<String> editProject(@RequestBody TaskDTO taskDTO) {
//        taskService.editTask(taskDTO);
//        return ResponseEntity.ok("");
//    }
}
