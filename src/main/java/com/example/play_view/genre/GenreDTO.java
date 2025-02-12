package com.example.play_view.genre;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GenreDTO(
        long genreId,

        @NotNull(message = "Genre is mandatory")
        @NotEmpty(message = "Genre is mandatory")
        String genre
) {
}
