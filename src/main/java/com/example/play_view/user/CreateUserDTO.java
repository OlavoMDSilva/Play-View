package com.example.play_view.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateUserDTO(
        long userId,

        @NotNull(message = "Name is mandatory")
        @NotEmpty(message = "Name is mandatory")
        String userName,

        @NotNull(message = "Email is mandatory")
        @NotEmpty(message = "Email is mandatory")
        String email,

        @NotNull(message = "Password is mandatory")
        @NotEmpty(message = "Password is mandatory")
        String password,

        String profileUrl,

        String userPhoneNum,

        @NotNull(message = "Birth date is mandatory")
        @NotEmpty(message = "Birth date is mandatory")
        LocalDate birthDate,

        boolean status
) {
}
