package com.jlog.domain.log;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record LogRequestV1(

        @NotNull
        @Positive
        Long id,

        @NotBlank
        @Size(min = 8, max = 8)
        String roomCode,

        @NotBlank
        @Size(min = 2, max = 16)
        String username,

        @NotNull
        @Positive
        Long expense,

        @Size(max = 255)
        String memo
) implements LogDto {
}
