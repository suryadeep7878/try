package com.kazilen.backend.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_phone", columnList = "phone", unique = true)
})
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    private String name; // optional

    @CreationTimestamp
    private Instant createdAt;
}
