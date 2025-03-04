package com.nagarro.assignment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorRequest {
    @Positive(message = "ID must be positive")
    Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be ≤100 characters")
    String name;

    @Size(max = 1000, message = "Description must be ≤500 characters")
    String description;
}
