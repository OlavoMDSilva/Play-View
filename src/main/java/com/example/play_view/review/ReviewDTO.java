package com.example.play_view.review;

import com.example.play_view.game.GameDTO;
import com.example.play_view.user.UserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReviewDTO(
        long reviewId,

        @NotNull(message = "Cod game is mandatory")
        GameDTO game,

        @NotNull(message = "Cod user is mandatory")
        UserDTO user,

        @NotNull(message = "Review is mandatory")
        @NotBlank(message = "Review is mandatory")
        String review
) {
}
