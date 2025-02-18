package com.example.play_view.user;

import java.time.LocalDate;

public record UserDTO(
        long userId,

        String userName,
        String email,
        String profileUrl,
        String userPhoneNum,
        LocalDate birthDate,
        boolean status
) {
}
