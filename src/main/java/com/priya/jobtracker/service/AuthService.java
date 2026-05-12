package com.priya.jobtracker.service;

import com.priya.jobtracker.dto.auth.AuthResponseDto;
import com.priya.jobtracker.dto.auth.LoginRequestDto;
import com.priya.jobtracker.dto.auth.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}