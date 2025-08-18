package com.kazilen.backend.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findTopByPhoneAndUsedIsFalseOrderByIdDesc(String phone);
    long deleteByExpiresAtBefore(Instant cutoff);
}
