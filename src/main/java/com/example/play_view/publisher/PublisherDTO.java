package com.example.play_view.publisher;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PublisherDTO(
        @Valid

        long publisherId,

        @NotNull(message = "Publisher name is mandatory")
        @NotBlank(message = "Publisher name is mandatory")
        String publisherName

) {
}
