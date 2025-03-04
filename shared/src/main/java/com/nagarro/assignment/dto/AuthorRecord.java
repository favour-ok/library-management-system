package com.nagarro.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AuthorRecord(
        @NotNull(message = "ID cannot be null")
        @Positive(message = "ID must be positive")
        Long id,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be ≤100 characters")
        String name,

        @Size(max = 1000, message = "Description must be ≤1000 characters")
        String description
) {}