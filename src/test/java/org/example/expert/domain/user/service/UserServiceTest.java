package org.example.expert.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.example.expert.global.exception.ApiException;
import org.example.expert.global.util.PasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("User Id로 User를 찾지 못하면 API 예외를 발생시킨다.")
    void getById() {
        // given
        long userId = 1;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when & then
        ApiException exception = assertThrows(ApiException.class, () -> userService.getById(userId));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("생성한 User가 제대로 저장된다.")
    void createUser() {
        // given
        String email = "test@email.com";
        String password = "1212";
        UserRole userRole = UserRole.USER;
        User user = new User(email, password, userRole);

        // when
        userService.createUser(user);

        // then
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("기존 비밀번호와 다른 비밀번호만 변경한다.")
    void changePassword(){
        // given
        String currentPw = "currentPw";
        String checkPw = "currentPw";
        String newPw = "newpassword";
        User user = new User("test@email.com", currentPw, UserRole.USER);
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(newPw, currentPw)).willReturn(false);
        given(passwordEncoder.matches(checkPw, currentPw)).willReturn(true);
        given(passwordEncoder.encode(newPw)).willReturn(newPw);

        UserChangePasswordRequest request = new UserChangePasswordRequest(checkPw, newPw);

        // when
        userService.changePassword(anyLong(), request);

        // then
        verify(userRepository, times(1)).findById(any());
        assertNotEquals(currentPw, user.getPassword());
        assertEquals(newPw, user.getPassword());
        verify(passwordEncoder, times(2)).matches(any(), any());
        verify(passwordEncoder, times(1)).encode(any());
    }

    @Test
    @DisplayName("이미 존재하는 이메일이면 예외를 발생 시킨다.")
    void existsByEmail() {
        // given
        String email = "test@email.com";
        given(userRepository.existsByEmail(email)).willReturn(true);

        // when & then
        ApiException exception = assertThrows(ApiException.class, () -> userService.existsByEmail(email));
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("email로 유저를 찾지 못하면 API 예외 발생")
    void getByEmail() {
        // given
        String email = "test@email";
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        // when & then
        ApiException exception = assertThrows(ApiException.class, () -> userService.getByEmail(email));
        assertEquals("가입되지 않은 유저입니다.", exception.getMessage());
    }
}
