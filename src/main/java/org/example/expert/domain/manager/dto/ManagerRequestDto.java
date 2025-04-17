package org.example.expert.domain.manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ManagerRequestDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {

        @NotNull
        private Long managerUserId; // 일정 작상자가 배치하는 유저 id
    }

}
