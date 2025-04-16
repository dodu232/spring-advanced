package org.example.expert.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.admin.service.AdminService;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/users/{userId}")
    public void changeUserRole(@PathVariable long userId, @RequestBody UserRoleChangeRequest userRoleChangeRequest) {
        adminService.changeUserRole(userId, userRoleChangeRequest);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable long commentId) {
        adminService.deleteComment(commentId);
    }


}
