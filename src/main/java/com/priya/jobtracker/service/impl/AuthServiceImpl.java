package com.priya.jobtracker.service.impl;

import com.priya.jobtracker.dto.auth.AuthResponseDto;
import com.priya.jobtracker.dto.auth.LoginRequestDto;
import com.priya.jobtracker.dto.auth.RegisterRequestDto;
import com.priya.jobtracker.entity.User;
import com.priya.jobtracker.enums.Role;
import com.priya.jobtracker.exception.BadRequestException;
import com.priya.jobtracker.repository.UserRepository;
import com.priya.jobtracker.security.JwtUtil;
import com.priya.jobtracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return AuthResponseDto.builder()
                .token(jwtUtil.generateToken(savedUser.getEmail()))
                .name(savedUser.getName())
                .role(savedUser.getRole().name())
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!matches) {
            throw new BadRequestException("Invalid email or password");
        }

        return AuthResponseDto.builder()
                .token(jwtUtil.generateToken(user.getEmail()))
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }
}