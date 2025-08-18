package com.yourcompany.Kazilen.professional.dto;

public record ProfessionalDto(
        Long id,
        String name,
        String imageUrl,
        String categoryName,
        Double distanceKm
) {}
