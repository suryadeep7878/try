package com.yourcompany.Kazilen.professional.dto;

import java.util.List;

public record ProfessionalsResponse(
        List<ProfessionalDto> items,
        long total // optional, 0 for now
) {}
