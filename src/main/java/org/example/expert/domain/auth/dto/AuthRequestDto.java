package org.example.expert.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Signup {

        @NotBlank @Email
        private String email;
        @NotBlank
        private String password;
        @NotBlank
        private String userRole;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Signin {

        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
    }

}
