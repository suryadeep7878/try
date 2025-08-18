package com.yourcompany.Kazilen.professional.dto;

public record ProfessionalDetailDto(
        Long id,
        String name,
        String imageUrl,
        String categoryName,
        Double rating,
        Double latitude,
        Double longitude,
        Double distanceKm,
        String phone,        // optional: add column if needed
        String description   // optional: add column if needed
) {}
