package com.nagarro.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;

public record BookRecord(
        @NotNull(message = "ID cannot be null")
        @Positive(message = "ID must be positive")
        Long id,

        @ISBN(message = "ISBN cannot be null")
        String isbn,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be ≤100 characters")
        String name,

        @Size(max = 50, message = "Serial name must be ≤50 characters")
        String serialName,

        @Size(max = 1000, message = "Description must be ≤500 characters")
        String description
) {}
