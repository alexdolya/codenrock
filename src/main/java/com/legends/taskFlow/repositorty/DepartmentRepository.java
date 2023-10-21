package com.legends.taskFlow.repositorty;

import com.legends.taskFlow.model.entity.Department;
import com.legends.taskFlow.model.entity.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    Optional<Department> findByNameDepartment(String name);
}
