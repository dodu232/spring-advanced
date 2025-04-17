package org.example.expert.domain.comment.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.CommentRequestDto;
import org.example.expert.domain.comment.dto.CommentResponseDto;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.global.exception.ApiException;
import org.example.expert.global.exception.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto.Create saveComment(AuthUser authUser, long todoId, CommentRequestDto.Create commentSaveRequest) {
        User user = User.fromAuthUser(authUser);
        Todo todo = todoRepository.findById(todoId).orElseThrow(() ->
                new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "Todo not found"));

        Comment newComment = new Comment(
                commentSaveRequest.getContents(),
                user,
                todo
        );

        Comment savedComment = commentRepository.save(newComment);

        return new CommentResponseDto.Create(
                savedComment.getId(),
                savedComment.getContents(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto.Get> getComments(long todoId) {
        List<Comment> commentList = commentRepository.findByTodoIdWithUser(todoId);

        List<CommentResponseDto.Get> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = comment.getUser();
            CommentResponseDto.Get dto = new CommentResponseDto.Get(
                    comment.getId(),
                    comment.getContents(),
                    new UserResponse(user.getId(), user.getEmail())
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public void deleteComment(long commentId){
        commentRepository.deleteById(commentId);
    }

}
