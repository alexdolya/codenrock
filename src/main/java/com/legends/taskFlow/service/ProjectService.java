package com.legends.taskFlow.service;

import com.legends.taskFlow.model.dto.ProjectDTO;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProjectService {

    void createNewProject(@NonNull ProjectDTO projectDTO);

    void deleteProject(Long id);

    ProjectDTO findProjectById(Long id);

    public List<ProjectDTO> findAll(PageRequest pageRequest);

    List<ProjectDTO> findProjectsByUserId(Integer id);

    void editProject(ProjectDTO projectDTO);

    void setProjectStatusCancelled(Long id);
}
