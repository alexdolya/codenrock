package com.legends.taskFlow.repositorty;

import com.legends.taskFlow.model.entity.Task;
import com.legends.taskFlow.model.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByOrderByCreationOnDesc(PageRequest pageRequest);
    List<Task> findAllByProjectProjectId(Long projectId); //todo переделать
    List<Task> findAllByAssignedTo(User user);

}
