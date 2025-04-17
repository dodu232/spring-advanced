package org.example.expert.domain.todo.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

public class TodoResponseDto {

    @Getter
    public static class Create {

        private final Long id;
        private final String title;
        private final String contents;
        private final String weather;
        private final UserResponse user;

        public Create(Long id, String title, String contents, String weather, UserResponse user) {
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.weather = weather;
            this.user = user;
        }
    }

    @Getter
    public static class Get {

        private final Long id;
        private final String title;
        private final String contents;
        private final String weather;
        private final UserResponse user;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;

        public Get(Long id, String title, String contents, String weather,
            UserResponse user, LocalDateTime createdAt, LocalDateTime modifiedAt) {
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.weather = weather;
            this.user = user;
            this.createdAt = createdAt;
            this.modifiedAt = modifiedAt;
        }
    }


}
