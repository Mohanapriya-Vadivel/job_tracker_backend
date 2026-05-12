package com.priya.jobtracker.dto.user;

import com.priya.jobtracker.enums.Role;
import jakarta.persistence.Column;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDto {
    private Long id;

    private String name;

    private String email;

    private Role role;
}
