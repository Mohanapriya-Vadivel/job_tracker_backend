package com.priya.jobtracker.controller;

import com.priya.jobtracker.dto.user.UserResponseDto;
import com.priya.jobtracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }



}
