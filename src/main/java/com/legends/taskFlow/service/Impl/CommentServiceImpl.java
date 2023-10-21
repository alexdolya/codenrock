package com.legends.taskFlow.service.Impl;

import com.legends.taskFlow.exception.NotFoundException;
import com.legends.taskFlow.model.dto.CommentDTO;
import com.legends.taskFlow.model.entity.Comment;
import com.legends.taskFlow.model.entity.Task;
import com.legends.taskFlow.model.entity.User;
import com.legends.taskFlow.model.enums.Role;
import com.legends.taskFlow.repositorty.CommentRepository;
import com.legends.taskFlow.service.CommentService;
import com.legends.taskFlow.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserServiceImpl userService;
    private final TaskService taskService;
    private final ModelMapper mapper;

    @Transactional
    public void postComment(Long taskId, Integer userId, String content) {
        Task task = taskService.findTaskEntityById(taskId);
        Comment comment = new Comment()
                .setDescription(content)
                .setCreatedDate(LocalDateTime.now())
                .setTask(task)
                .setProject(task.getProject())
                .setUser(userService.findById(userId));
        commentRepository.save(comment);
        log.info("Комментарий от пользователя с id" + userId + "успешно добавлен");
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));
    }

    public void deleteComment(Long commentID, Integer userId) {
        Comment comment = findById(commentID);
        User user = userService.findById(userId);

        if (user.getRole().equals(Role.ROLE_ADMIN) || user.getRole().equals(Role.ROLE_PROJECT_MANAGER) || user.getUserId().equals(comment.getUser().getUserId())) {
            commentRepository.delete(comment);
            log.info("Комментарий с id: " + commentID + "успешно удален");
        } else throw new RuntimeException("Недостаточно прав для удаления комметария с id: " + commentID);
    }

    public List<CommentDTO> getAllCommentsByTaskId(Long taskId, PageRequest pageRequest) {
        List<Comment> comments = commentRepository.findAllByTaskTaskIdOrderByCreatedDateDesc(taskId, pageRequest);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments){
           commentDTOS.add(new CommentDTO().setCommentId(comment.getCommentId())
                    .setContent(comment.getDescription()))                    ;
        }
        return commentDTOS;

    }
}
