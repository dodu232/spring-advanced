package org.example.expert.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        @NotBlank
        private String contents;
    }

}
