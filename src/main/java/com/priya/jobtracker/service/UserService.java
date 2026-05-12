package com.priya.jobtracker.service;

import com.priya.jobtracker.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();


}
