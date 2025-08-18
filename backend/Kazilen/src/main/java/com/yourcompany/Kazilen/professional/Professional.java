package com.yourcompany.Kazilen.professional;

import com.yourcompany.Kazilen.category.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professionals", indexes = {
        @Index(name = "idx_prof_category", columnList = "category_id")
})
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Professional {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "image_url")
    private String imageUrl;

    private Double rating;
    private Boolean active;

    @Column(nullable = false, unique = true, length = 200)
    private String slug;
}
