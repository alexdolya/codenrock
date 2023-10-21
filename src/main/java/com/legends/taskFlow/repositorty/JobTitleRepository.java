package com.legends.taskFlow.repositorty;

import com.legends.taskFlow.model.entity.Department;
import com.legends.taskFlow.model.entity.JobTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JobTitleRepository extends CrudRepository<JobTitle, Integer> {
    boolean existsByNameJobTitle(String name);

    Optional<JobTitle> findByNameJobTitle(String name);
}
