package com.nagarro.assignment.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthResponse(
        @NotBlank(message = "Access token must be provided")
        @Size(min = 256, message = "Access token appears malformed")
        String accessToken,

        @NotBlank(message = "Refresh token must be provided")
        @Size(min = 256, message = "Refresh token appears malformed")
        String refreshToken) {}
