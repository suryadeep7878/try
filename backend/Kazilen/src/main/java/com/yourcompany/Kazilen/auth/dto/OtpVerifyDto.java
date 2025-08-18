package com.kazilen.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OtpVerifyDto(
        @NotBlank
        @Pattern(regexp = "\\d{10}", message = "phone must be 10 digits")
        String phone,

        @NotBlank
        @Pattern(regexp = "\\d{6}", message = "otp must be 6 digits")
        String otp
) {}
