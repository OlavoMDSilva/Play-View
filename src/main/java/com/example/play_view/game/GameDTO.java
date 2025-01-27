package com.example.play_view.game;

import com.example.play_view.company.CompanyDTO;
import com.example.play_view.company.CompanyEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.sql.Date;

@Builder
public record GameDTO(
        @Valid
        long gameId,

        @NotNull(message = "Cod Company is mandatory")
        CompanyDTO company,

        @NotNull(message = "Title is mandatory")
        @NotBlank(message = "Title is mandatory")
        String title,

        String cover_url,

        @NotNull(message = "Release date is mandatory")
        Date releaseDate,

        @NotNull(message = "Description is mandatory")
        @NotBlank(message = "Description is mandatory")
        String gameDescription,

        @NotNull(message = "Indication is mandatory")
        @NotBlank(message = "Indication is mandatory")
        String indication
) {
}
