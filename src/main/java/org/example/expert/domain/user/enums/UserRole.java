package org.example.expert.domain.user.enums;

import java.util.Arrays;
import org.example.expert.global.exception.ApiException;
import org.example.expert.global.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum UserRole {
    ADMIN, USER;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "유효하지 않은 UerRole"));
    }
}
