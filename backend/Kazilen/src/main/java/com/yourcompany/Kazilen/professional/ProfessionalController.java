package com.yourcompany.Kazilen.professional;

import com.yourcompany.Kazilen.professional.dto.ProfessionalDetailDto;
import com.yourcompany.Kazilen.professional.dto.ProfessionalsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService service;

    @GetMapping("/professionals")
    public ResponseEntity<ProfessionalsResponse> nearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(name = "category", required = false, defaultValue = "All Services") String category,
            @RequestParam(required = false, defaultValue = "20") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        return ResponseEntity.ok(service.findNearby(category, lat, lng, limit, offset));
    }

    @GetMapping("/professionals/{slug}")
    public ResponseEntity<ProfessionalDetailDto> bySlug(
            @PathVariable String slug,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng
    ) {
        return ResponseEntity.ok(service.getBySlug(slug, lat, lng));
    }
}
