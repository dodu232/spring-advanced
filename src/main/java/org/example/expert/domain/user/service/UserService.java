package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.example.expert.global.exception.ApiException;
import org.example.expert.global.exception.ErrorType;
import org.example.expert.global.util.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER,
                "User not found"));
        return new UserResponse(user.getId(), user.getEmail());
    }

    @Transactional
    public void changePassword(long userId, UserChangePasswordRequest userChangePasswordRequest) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER,
                "User not found"));

        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(),
            user.getPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER,
                "새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(),
            user.getPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER,
                "잘못된 비밀번호입니다.");
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
    }

    @Transactional
    public User getById(long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER,
                "User not found"));
    }

    @Transactional
    public void existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER,
                "이미 존재하는 이메일입니다.");
        }
    }

    @Transactional
    public User getByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
            () -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "가입되지 않은 유저입니다."));
    }
}
