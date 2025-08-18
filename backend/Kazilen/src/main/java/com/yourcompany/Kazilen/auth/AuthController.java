package com.yourcompany.Kazilen.auth;

import com.kazilen.backend.auth.dto.AuthResponseDto;
import com.kazilen.backend.auth.dto.OtpRequestDto;
import com.kazilen.backend.auth.dto.OtpVerifyDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/otp")
@RequiredArgsConstructor
public class AuthController {

    private final com.kazilen.backend.auth.OtpService otpService;

    @PostMapping("/request")
    public ResponseEntity<Void> request(@Valid @RequestBody OtpRequestDto body) {
        otpService.requestOtp(body);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthResponseDto> verify(@Valid @RequestBody OtpVerifyDto body) {
        String token = otpService.verifyOtp(body);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
