package com.priya.jobtracker.service;

import com.priya.jobtracker.dto.application.ApplicationRequestDto;
import com.priya.jobtracker.dto.application.ApplicationResponseDto;
import com.priya.jobtracker.dto.application.DashboardResponseDto;
import com.priya.jobtracker.entity.JobApplication;
import com.priya.jobtracker.entity.User;
import com.priya.jobtracker.enums.ApplicationStatus;

import java.util.List;

public interface JobApplicationService {
    ApplicationResponseDto createApplication(ApplicationRequestDto request);

    List<ApplicationResponseDto> getAllApplications(int page, int size, ApplicationStatus status,
                                                    String search);

    ApplicationResponseDto getApplicationById(Long id);

    ApplicationResponseDto updateApplication(Long id, ApplicationRequestDto request);

    DashboardResponseDto getDashboard();

    DashboardResponseDto getAdminDashboard();

    void deleteApplication(Long id);
}