package org.example.expert.domain.manager.controller;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.auth.jwt.JwtUtil;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.dto.ManagerRequestDto;
import org.example.expert.domain.manager.dto.ManagerResponseDto;
import org.example.expert.domain.manager.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final JwtUtil jwtUtil;

    @PostMapping("/todos/{todoId}/managers")
    public ResponseEntity<ManagerResponseDto.Create> saveManager(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody ManagerRequestDto.Create managerSaveRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(managerService.saveManager(authUser, todoId, managerSaveRequest));
    }

    @GetMapping("/todos/{todoId}/managers")
    public ResponseEntity<List<ManagerResponseDto.Get>> getMembers(@PathVariable long todoId) {
        return ResponseEntity.status(HttpStatus.OK).body(managerService.getManagers(todoId));
    }

    @DeleteMapping("/todos/{todoId}/managers/{managerId}")
    public ResponseEntity<Void> deleteManager(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable long todoId,
            @PathVariable long managerId
    ) {
        Claims claims = jwtUtil.extractClaims(bearerToken.substring(7));
        long userId = Long.parseLong(claims.getSubject());
        managerService.deleteManager(userId, todoId, managerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
