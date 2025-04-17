package org.example.expert.domain.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TodoRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        @NotBlank
        private String title;
        @NotBlank
        private String contents;
    }

}
