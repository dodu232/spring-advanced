package org.example.expert.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.auth.dto.AuthRequestDto;
import org.example.expert.domain.auth.dto.AuthResponseDto;
import org.example.expert.domain.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthResponseDto.Signup> signup(@Valid @RequestBody AuthRequestDto.Signup signupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signupRequest));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<AuthResponseDto.Signin> signin(@Valid @RequestBody AuthRequestDto.Signin signinRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signin(signinRequest));
    }
}
