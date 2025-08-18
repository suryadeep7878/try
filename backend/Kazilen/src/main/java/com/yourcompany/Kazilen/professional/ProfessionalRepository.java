package com.yourcompany.Kazilen.professional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    interface NearbyProjection {
        Long getId();
        String getName();
        String getImageUrl();
        Double getLatitude();
        Double getLongitude();
        Long getCategoryId();
        String getCategoryName();
        Double getDistanceKm();
    }

    interface DetailProjection {
        Long getId();
        String getName();
        String getImageUrl();
        Double getLatitude();
        Double getLongitude();
        Double getRating();
        String getCategoryName();
        Double getDistanceKm();
    }

    // Nearby for ALL categories
    @Query(value = """
    SELECT p.id,
           p.name,
           p.image_url AS imageUrl,
           p.latitude,
           p.longitude,
           p.category_id AS categoryId,
           c.name AS categoryName,
           (6371 * acos(
               cos(radians(:lat)) * cos(radians(p.latitude)) *
               cos(radians(p.longitude) - radians(:lng)) +
               sin(radians(:lat)) * sin(radians(p.latitude))
           )) AS distance_km
    FROM professionals p
    JOIN categories c ON c.id = p.category_id
    WHERE COALESCE(p.active, true) = true
    ORDER BY distance_km ASC
    LIMIT :limit OFFSET :offset
  """, nativeQuery = true)
    List<NearbyProjection> findNearbyAll(@Param("lat") double lat,
                                         @Param("lng") double lng,
                                         @Param("limit") int limit,
                                         @Param("offset") int offset);

    // Nearby for a single category
    @Query(value = """
    SELECT p.id,
           p.name,
           p.image_url AS imageUrl,
           p.latitude,
           p.longitude,
           p.category_id AS categoryId,
           c.name AS categoryName,
           (6371 * acos(
               cos(radians(:lat)) * cos(radians(p.latitude)) *
               cos(radians(p.longitude) - radians(:lng)) +
               sin(radians(:lat)) * sin(radians(p.latitude))
           )) AS distance_km
    FROM professionals p
    JOIN categories c ON c.id = p.category_id
    WHERE COALESCE(p.active, true) = true
      AND p.category_id = :categoryId
    ORDER BY distance_km ASC
    LIMIT :limit OFFSET :offset
  """, nativeQuery = true)
    List<NearbyProjection> findNearbyByCategory(@Param("lat") double lat,
                                                @Param("lng") double lng,
                                                @Param("categoryId") long categoryId,
                                                @Param("limit") int limit,
                                                @Param("offset") int offset);

    // Detail by slug (optionally with lat/lng for distance)
    @Query(value = """
    SELECT p.id,
           p.name,
           p.image_url AS imageUrl,
           p.latitude,
           p.longitude,
           p.rating,
           c.name AS categoryName,
           CASE WHEN :lat IS NULL OR :lng IS NULL THEN NULL
                ELSE (6371 * acos(
                        cos(radians(:lat)) * cos(radians(p.latitude)) *
                        cos(radians(p.longitude) - radians(:lng)) +
                        sin(radians(:lat)) * sin(radians(p.latitude))
                      ))
           END AS distance_km
    FROM professionals p
    JOIN categories c ON c.id = p.category_id
    WHERE p.slug = :slug
    LIMIT 1
  """, nativeQuery = true)
    DetailProjection detailBySlug(@Param("slug") String slug,
                                  @Param("lat") Double lat,
                                  @Param("lng") Double lng);
}
