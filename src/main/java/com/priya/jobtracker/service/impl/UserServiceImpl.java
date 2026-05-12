package com.priya.jobtracker.service.impl;

import com.priya.jobtracker.repository.JobApplicationRepository;
import com.priya.jobtracker.dto.user.UserResponseDto;
import com.priya.jobtracker.entity.User;
import com.priya.jobtracker.exception.ResourceNotFoundException;
import com.priya.jobtracker.repository.UserRepository;
import com.priya.jobtracker.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JobApplicationRepository taskRepository;
    private UserResponseDto mapToResponse(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }

    @Override
    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


}
