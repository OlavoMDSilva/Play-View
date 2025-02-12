package com.example.play_view.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CompanyDTO(
        long companyId,

        @NotNull(message = "Company name is mandatory")
        @NotBlank(message = "Company name is mandatory")
        String companyName
) {
}
