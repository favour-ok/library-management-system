package com.nagarro.assignment.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookRequest {
        @Positive(message = "ID must be positive")
        Long id;

        @NotNull(message = "ISBN cannot be null")
        @ISBN(type = ISBN.Type.ISBN_13, message = "Invalid ISBN format")
        String isbn;

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be ≤100 characters")
        String name;

        @Size(max = 50, message = "Serial name must be ≤50 characters")
        String serialName;

        @Size(max = 1000, message = "Description must be ≤500 characters")
        String description;

        @NotEmpty(message = "At least one author is required")
        List<Long> authorIds = new ArrayList<>();
}