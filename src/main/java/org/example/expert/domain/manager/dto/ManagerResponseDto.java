package org.example.expert.domain.manager.dto;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

public class ManagerResponseDto {

    @Getter
    public static class Create {

        private final Long id;
        private final UserResponse user;

        public Create(Long id, UserResponse user) {
            this.id = id;
            this.user = user;
        }
    }

    @Getter
    public static class Get {

        private final Long id;
        private final UserResponse user;

        public Get(Long id, UserResponse user) {
            this.id = id;
            this.user = user;
        }
    }


}
