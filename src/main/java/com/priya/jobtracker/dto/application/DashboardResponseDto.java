package com.priya.jobtracker.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDto {

    private Long total;

    private Long applied;

    private Long shortlisted;

    private Long interview;

    private Long rejected;

    private Long offered;
}
