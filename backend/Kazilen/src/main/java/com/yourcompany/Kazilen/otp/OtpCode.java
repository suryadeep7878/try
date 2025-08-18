package com.kazilen.backend.otp;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "otp_codes", indexes = {
        @Index(name = "idx_otp_phone", columnList = "phone")
})
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OtpCode {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(nullable = false)
    private Instant expiresAt;

    private boolean used;
}
