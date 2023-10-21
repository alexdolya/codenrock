package com.legends.taskFlow.service;

import com.legends.taskFlow.model.dto.CommentDTO;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CommentService {
    void postComment(Long taskId, Integer userId, String content);

    List<CommentDTO> getAllCommentsByTaskId(Long taskId, PageRequest of);

    void deleteComment(Long commentID, Integer userId);
}
