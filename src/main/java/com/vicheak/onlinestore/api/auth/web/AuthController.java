package com.vicheak.onlinestore.api.auth.web;

import com.vicheak.onlinestore.api.auth.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${app.base-uri}")
    private String appBaseUri;

    @PostMapping("/login")
    public AuthDto login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public Map<String, String> register(@Valid @RequestBody RegisterDto registerDto) throws MessagingException {
        authService.register(registerDto);
        return Map.of("message", "Please check email and verify...!",
                "verifyUri", appBaseUri + "auth/verify?email=" + registerDto.email());
    }

    @PostMapping("/verify")
    public Map<String, String> verify(@Valid @RequestBody VerifyDto verifyDto) {
        authService.verify(verifyDto);
        return Map.of("message", "Congratulation! Email has been verified...!");
    }

}
