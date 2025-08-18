package com.yourcompany.Kazilen.professional;

import com.yourcompany.Kazilen.category.Category;
import com.yourcompany.Kazilen.category.CategoryRepository;
import com.yourcompany.Kazilen.professional.dto.ProfessionalDetailDto;
import com.yourcompany.Kazilen.professional.dto.ProfessionalDto;
import com.yourcompany.Kazilen.professional.dto.ProfessionalsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final ProfessionalRepository repo;
    private final CategoryRepository categoryRepo;

    public ProfessionalsResponse findNearby(String categoryName, double lat, double lng, int limit, int offset) {
        boolean all = (categoryName == null) || categoryName.equalsIgnoreCase("All Services");

        List<ProfessionalRepository.NearbyProjection> rows;

        if (all) {
            rows = repo.findNearbyAll(lat, lng, limit, offset);
        } else {
            Category cat = categoryRepo.findByNameIgnoreCase(categoryName)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown category: " + categoryName));
            rows = repo.findNearbyByCategory(lat, lng, cat.getId(), limit, offset);
        }

        var items = rows.stream()
                .map(r -> new ProfessionalDto(
                        r.getId(),
                        r.getName(),
                        r.getImageUrl(),
                        r.getCategoryName(),
                        r.getDistanceKm()
                ))
                .toList();

        return new ProfessionalsResponse(items, 0);
    }

    public ProfessionalDetailDto getBySlug(String slug, Double lat, Double lng) {
        var p = repo.detailBySlug(slug, lat, lng);
        if (p == null) {
            throw new IllegalArgumentException("Professional not found");
        }
        return new ProfessionalDetailDto(
                p.getId(),
                p.getName(),
                p.getImageUrl(),
                p.getCategoryName(),
                p.getRating(),
                p.getLatitude(),
                p.getLongitude(),
                p.getDistanceKm(),
                null, // phone (add to entity + query if you need)
                null  // description
        );
    }
}
