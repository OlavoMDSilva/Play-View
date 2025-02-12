package com.example.play_view.publisher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PublisherDTO(
        long publisherId,

        @NotNull(message = "Publisher name is mandatory")
        @NotBlank(message = "Publisher name is mandatory")
        String publisherName

) {
}
