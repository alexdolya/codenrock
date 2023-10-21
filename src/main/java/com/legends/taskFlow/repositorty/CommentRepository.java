package com.legends.taskFlow.repositorty;


import com.legends.taskFlow.model.entity.Comment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    List<Comment> findAllByTaskTaskIdOrderByCreatedDateDesc (Long taskId, PageRequest pageRequest);
}
