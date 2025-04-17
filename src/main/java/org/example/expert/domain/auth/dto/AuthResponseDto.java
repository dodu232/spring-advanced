package org.example.expert.domain.auth.dto;

import lombok.Getter;

public class AuthResponseDto {

    @Getter
    public static class Signup {

        private final String bearerToken;

        public Signup(String bearerToken) {
            this.bearerToken = bearerToken;
        }
    }

    @Getter
    public static class Signin {

        private final String bearerToken;

        public Signin(String bearerToken) {
            this.bearerToken = bearerToken;
        }
    }

}
