package com.priya.jobtracker.controller;

import com.priya.jobtracker.dto.auth.AuthResponseDto;
import com.priya.jobtracker.dto.auth.LoginRequestDto;
import com.priya.jobtracker.dto.auth.RegisterRequestDto;
import com.priya.jobtracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@ControllerAdvice
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody RegisterRequestDto request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        return authService.login(request);
    }
}