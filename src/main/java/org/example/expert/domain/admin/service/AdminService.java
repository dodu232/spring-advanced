package org.example.expert.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final CommentService commentService;

    @Transactional
    public void changeUserRole(long userId, UserRoleChangeRequest userRoleChangeRequest) {
        User user = userService.getById(userId);
        user.updateRole(UserRole.of(userRoleChangeRequest.getRole()));
    }

    @Transactional
    public void deleteComment(long commentId) {
        commentService.deleteComment(commentId);
    }

}
