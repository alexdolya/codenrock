package com.legends.taskFlow.service.Impl;

import com.legends.taskFlow.exception.*;
import com.legends.taskFlow.model.dto.ProjectDTO;
import com.legends.taskFlow.model.entity.Project;
import com.legends.taskFlow.model.entity.Status;
import com.legends.taskFlow.model.entity.User;
import com.legends.taskFlow.model.enums.StatusEnum;
import com.legends.taskFlow.repositorty.ProjectRepository;
import com.legends.taskFlow.repositorty.UserRepository;
import com.legends.taskFlow.service.EstimateTimeConverterService;
import com.legends.taskFlow.service.ProjectService;
import com.legends.taskFlow.service.StatusService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ModelMapper mapper;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final StatusService statusService;

    private final EstimateTimeConverterService timeConverter;

    private void validateProjectDates(@NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        if (endDate.isEqual(startDate) || endDate.isBefore(startDate) || endDate.isBefore(LocalDate.now())) {
            throw new EndDateBeforeStartDateException("Project's end date is before start date or today");
        }
    }


    public void createNewProject(@NonNull ProjectDTO projectDTO) {
        log.info("Запрос на создание нового проекта: {}", projectDTO);
        validateProjectDates(projectDTO.getStartDate(), projectDTO.getEndDate());

        if (projectRepository.existsByProjectTitle(projectDTO.getProjectTitle())) {
            throw new EntityAlreadyExistsException("Project with title " + projectDTO.getProjectTitle() + " already exists");
        }

        User user = userRepository.findById(projectDTO.getProjectOwnerId()).orElseThrow(() -> new EntityNotExistsException("Project owner does not exists"));
        Status created = statusService.findProjectStatus(StatusEnum.Create);
        Project project = mapProjectDTOToProject(projectDTO);
        project.setProjectOwner(user);
        project.setCreationOn(LocalDateTime.now());
        project.setProjectStatus(created);

        projectRepository.save(project);
        log.info("Проект успешно сохранен");
    }

    private Project mapProjectDTOToProject(ProjectDTO projectDTO) {
        Project project = mapper.map(projectDTO, Project.class);
        project.setGoal("");
        project.setStartPlanDate(projectDTO.getStartDate().atStartOfDay());
        project.setEndPlanDate(projectDTO.getEndDate().atStartOfDay());
        project.setProjectTypeName(projectDTO.getProjectType());
        project.setEstimateTimePlan(timeConverter.generateEstimateTimeString(project.getStartPlanDate(), project.getEndPlanDate()));
        return project;
    }

    private ProjectDTO mapProjectToProjectDTO(Project project) {
        ProjectDTO projectDTO = mapper.map(project, ProjectDTO.class);
        projectDTO.setProjectStatus(StatusEnum.valueOf(project.getProjectStatus().getName()));
        projectDTO.setStartDate(project.getStartPlanDate().toLocalDate());
        projectDTO.setEndDate(project.getEndPlanDate().toLocalDate());
        projectDTO.setCreationDate(project.getCreationOn().toLocalDate());
        return projectDTO;
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project with id " + id + " not found"));

        if (project.getTasks() != null && project.getTasks().size() > 0) {
            throw new DeletingProjectException("Project " + id + " has tasks and cannot be deleted");
        }
        projectRepository.delete(project);
        log.info("Проект успешно удален");
    }

    public ProjectDTO findProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Project with id " + id + " not found"));
        return new ProjectDTO().setProjectId(project.getProjectId())
                .setProjectTitle(project.getProjectTitle())
                .setProjectType(project.getProjectTypeName())
                .setProjectStatus(StatusEnum.valueOf(project.getProjectStatus().getName()))
                .setStartDate(project.getStartPlanDate().toLocalDate())
                .setEndDate(project.getEndPlanDate().toLocalDate())
                .setProjectDescription(project.getProjectDescription())
                .setProjectOwnerId(project.getProjectOwner().getUserId());
    }

    public List<ProjectDTO> findAll(PageRequest pageRequest) {
        List<Project> projects = projectRepository.findAllByOrderByCreationOnDesc(pageRequest);
        return mapProjectDTOSList(projects);
    }

    public List<ProjectDTO> findProjectsByUserId(Integer id) {
        List<Project> projects = projectRepository.findProjectsByProjectOwnerUserId(id);
        return mapProjectDTOSList(projects);
    }

    private List<ProjectDTO> mapProjectDTOSList(List<Project> projects) {
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for (Project project : projects) {
            projectDTOS.add(new ProjectDTO().setProjectId(project.getProjectId())
                    .setProjectTitle(project.getProjectTitle())
                    .setProjectType(project.getProjectTypeName())
                    .setProjectStatus(StatusEnum.valueOf(project.getProjectStatus().getName()))
                    .setStartDate(project.getStartPlanDate().toLocalDate())
                    .setEndDate(project.getEndPlanDate().toLocalDate())
                    .setProjectDescription(project.getProjectDescription())
                    .setProjectOwnerId(project.getProjectOwner().getUserId()));
        }
        return projectDTOS;
    }

    @Transactional
    public void editProject(ProjectDTO projectDTO) {
        validateProjectDates(projectDTO.getStartDate(), projectDTO.getEndDate());

        Project project = projectRepository.findById(projectDTO.getProjectId()).orElseThrow(() -> new NotFoundException("Project with id " + projectDTO.getProjectId() + " not found"));
        mapper.map(projectDTO, project);

        Status status = statusService.findProjectStatus(projectDTO.getProjectStatus());
        project.setProjectStatus(status);
        projectRepository.save(project);
    }

    @Transactional
    public void setProjectStatusCancelled(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project with id " + id + " not found"));
        Status status = statusService.findProjectStatus(StatusEnum.Cancelled);
        project.setProjectStatus(status);
        projectRepository.save(project);
        log.info("Статус проекта установлен: Cancelled");
    }
}
