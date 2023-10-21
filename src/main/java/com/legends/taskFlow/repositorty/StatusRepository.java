package com.legends.taskFlow.repositorty;

import com.legends.taskFlow.model.entity.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatusRepository  extends CrudRepository<Status, Integer> {
    Optional<Status> findByName(String statusName);
}
