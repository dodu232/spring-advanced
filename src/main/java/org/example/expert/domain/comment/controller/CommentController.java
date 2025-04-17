package org.example.expert.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.CommentRequestDto;
import org.example.expert.domain.comment.dto.CommentResponseDto;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentResponseDto.Create> saveComment(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody CommentRequestDto.Create commentSaveRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.saveComment(authUser, todoId, commentSaveRequest));
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponseDto.Get>> getComments(@PathVariable long todoId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments(todoId));
    }
}
