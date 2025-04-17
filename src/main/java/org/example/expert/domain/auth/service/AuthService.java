package org.example.expert.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.auth.dto.AuthRequestDto;
import org.example.expert.domain.auth.dto.AuthResponseDto;
import org.example.expert.domain.auth.jwt.JwtUtil;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.service.UserService;
import org.example.expert.global.exception.ApiException;
import org.example.expert.global.exception.ErrorType;
import org.example.expert.global.util.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponseDto.Signup signup(AuthRequestDto.Signup signupRequest) {

        userService.existsByEmail(signupRequest.getEmail());

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User newUser = new User(
                signupRequest.getEmail(),
                encodedPassword,
                userRole
        );
        User savedUser = userService.createUser(newUser);

        String bearerToken = jwtUtil.createToken(savedUser.getId(), savedUser.getEmail(), userRole);

        return new AuthResponseDto.Signup(bearerToken);
    }

    @Transactional(readOnly = true)
    public AuthResponseDto.Signin signin(AuthRequestDto.Signin signinRequest) {
        User user = userService.getByEmail(signinRequest.getEmail());

        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401을 반환합니다.
        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, ErrorType.INVALID_PARAMETER, "잘못된 비밀번호입니다.");
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getUserRole());

        return new AuthResponseDto.Signin(bearerToken);
    }
}
