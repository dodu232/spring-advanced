package org.example.expert.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class PasswordEncoderTest {

    @InjectMocks
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("BCryptPasswordEncoder가 패스워드를 정상적으로 인코딩하고 매칭한다\"")
    void matches_() {
        // given
        String rawPassword = "testPassword";

        // when
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // then
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
