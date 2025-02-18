package com.example.play_view.game;

import com.example.play_view.company.CompanyDTO;
import com.example.play_view.genre.GenreDTO;
import com.example.play_view.publisher.PublisherDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record GameDTO(
        long gameId,

        @NotNull(message = "Title is mandatory")
        @NotBlank(message = "Title is mandatory")
        String title,

        @NotNull(message = "Cod Company is mandatory")
        CompanyDTO company,

        @NotNull(message = "Publishers are mandatory")
        @NotEmpty(message = "Publishers are mandatory")
        Set<PublisherDTO> publishers,

        @NotNull(message = "Genres are mandatory")
        @NotEmpty(message = "Genres are mandatory")
        Set<GenreDTO> genres,

        String coverUrl,

        @NotNull(message = "Release date is mandatory")
        LocalDate releaseDate,

        @NotNull(message = "Description is mandatory")
        @NotBlank(message = "Description is mandatory")
        String description,

        @NotNull(message = "Restriction is mandatory")
        @NotBlank(message = "Restriction is mandatory")
        String restriction
) {
}
