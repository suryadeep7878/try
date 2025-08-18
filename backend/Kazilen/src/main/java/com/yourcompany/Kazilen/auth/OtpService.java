package com.kazilen.backend.auth;

import com.kazilen.backend.auth.dto.OtpRequestDto;
import com.kazilen.backend.auth.dto.OtpVerifyDto;
import com.kazilen.backend.otp.OtpCode;
import com.kazilen.backend.otp.OtpCodeRepository;
import com.kazilen.backend.user.User;
import com.kazilen.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpCodeRepository otpRepo;
    private final UserRepository userRepo;
    private final SecureRandom random = new SecureRandom();

    @Transactional
    public void requestOtp(OtpRequestDto req) {
        otpRepo.deleteByExpiresAtBefore(Instant.now()); // clean old

        String code = String.format("%06d", random.nextInt(1_000_000));
        Instant expires = Instant.now().plus(2, ChronoUnit.MINUTES);

        OtpCode otp = OtpCode.builder()
                .phone(req.phone())
                .code(code)
                .expiresAt(expires)
                .used(false)
                .build();

        otpRepo.save(otp);

        // TODO: integrate SMS; for now just log to console
        System.out.println("OTP for " + req.phone() + " = " + code);
    }

    @Transactional
    public String verifyOtp(OtpVerifyDto req) {
        OtpCode latest = otpRepo.findTopByPhoneAndUsedIsFalseOrderByIdDesc(req.phone())
                .orElseThrow(() -> new IllegalArgumentException("No OTP found for this phone"));

        if (latest.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("OTP expired");
        }
        if (!latest.getCode().equals(req.otp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        latest.setUsed(true);
        otpRepo.save(latest);

        userRepo.findByPhone(req.phone()).orElseGet(() ->
                userRepo.save(User.builder().phone(req.phone()).build())
        );

        // return a mock token (replace with JWT later)
        return UUID.randomUUID().toString();
    }
}
