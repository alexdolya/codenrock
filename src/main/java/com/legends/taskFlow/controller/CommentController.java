package com.legends.taskFlow.controller;

import com.legends.taskFlow.model.dto.CommentDTO;
import com.legends.taskFlow.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Размещает комментарий к задаче")
    @PostMapping("/task/{taskId}/comment")
    public void postComment(@PathVariable Long taskId,  @RequestParam("userId") Integer userId, @RequestBody String content) {
        commentService.postComment(taskId, userId, content);
    }

    @Operation(summary = "Возвращает все комментарии к задаче")
    @GetMapping("/task/{taskId}/comments")
    public List<CommentDTO> getAllCommentsByTaskId(@PathVariable Long taskId,
                                                   @RequestParam (required = false, defaultValue = "0") int page,
                                                   @RequestParam (required = false, defaultValue = "10") int size)     {
        return commentService.getAllCommentsByTaskId(taskId, PageRequest.of(page, size));

    }

    @Operation(summary = "Удаляет комментарий" )
    @DeleteMapping("/comment/{commentID}/delete")
    public void deleteComment(@PathVariable Long commentID,   @RequestParam("userId") Integer userId) {
        commentService.deleteComment(commentID, userId);
    }




}
