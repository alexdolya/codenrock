package com.legends.taskFlow.controller;

import com.legends.taskFlow.model.dto.ProjectDTO;
import com.legends.taskFlow.service.Impl.ProjectServiceImpl;
import com.legends.taskFlow.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Создание проекта и сохранение в БД")
    @PostMapping("/create/project")
    public ResponseEntity<String> createProject(@RequestBody ProjectDTO projectDTO) {
        log.info("Request to POST /create/project: " + projectDTO);
        projectService.createNewProject(projectDTO);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Присвоение проекту статуса отмененного(закрытого до завершения по каким-либо причинам)")
    @PatchMapping("/cancelled/project/{id}")
    public ResponseEntity<String> cancelledProject(@PathVariable Long id) {
        log.info("Request to PATCH /cancelled/project/" + id);
        projectService.setProjectStatusCancelled(id);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Удаление проекта")
    @DeleteMapping("/project/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        log.info("Request to DELETE /project/" + id);
        projectService.deleteProject(id);
        return ResponseEntity.ok("");
    }

    @Operation(summary = "Получение проекта по id")
    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {
        log.info("Request to GET /project/" + id);
        return ResponseEntity.ok(projectService.findProjectById(id));
    }

    @Operation(summary = "Получение всех проектов")
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getProjects( @RequestParam (required = false, defaultValue = "0") int page,
                                                         @RequestParam (required = false, defaultValue = "10") int size)      {
        log.info("Request to GET /projects");
        return ResponseEntity.ok(projectService.findAll(PageRequest.of(page, size)));
    }

    @Operation(summary = "Получение всех проектов пользователя")
    @GetMapping("/projectsByUser/{id}")
    public ResponseEntity<List<ProjectDTO>> getProjects(@PathVariable Integer id) {
        log.info("Request to GET /projectsByUser/" + id);
        return ResponseEntity.ok(projectService.findProjectsByUserId(id));
    }

    @Operation(summary = "Изменение проекта")
    @PatchMapping("/edit/project")
    public ResponseEntity<String> editProject(@RequestBody ProjectDTO projectDTO) {
        log.info("Request to PATCH /edit/project: " + projectDTO);
        projectService.editProject(projectDTO);
        return ResponseEntity.ok("");
    }
}
