package com.legends.taskFlow.repositorty;

import com.legends.taskFlow.model.entity.Project;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    boolean existsByProjectTitle(String title);
    List<Project> findAllByOrderByCreationOnDesc(PageRequest pageRequest);
    List<Project> findProjectsByProjectOwnerUserId(Integer ownerId);
}
