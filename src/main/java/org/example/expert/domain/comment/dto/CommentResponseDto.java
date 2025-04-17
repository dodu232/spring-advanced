package org.example.expert.domain.comment.dto;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

public class CommentResponseDto {

    @Getter
    public static class Create {

        private final Long id;
        private final String contents;
        private final UserResponse user;

        public Create(Long id, String contents, UserResponse user) {
            this.id = id;
            this.contents = contents;
            this.user = user;
        }
    }

    @Getter
    public static class Get {

        private final Long id;
        private final String contents;
        private final UserResponse user;

        public Get(Long id, String contents, UserResponse user) {
            this.id = id;
            this.contents = contents;
            this.user = user;
        }
    }

}
