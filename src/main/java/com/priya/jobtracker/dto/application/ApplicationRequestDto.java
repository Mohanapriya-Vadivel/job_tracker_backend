package com.priya.jobtracker.dto.application;
import com.priya.jobtracker.enums.ApplicationStatus;
import com.priya.jobtracker.enums.JobSource;
import com.priya.jobtracker.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationRequestDto {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Applied date is required")
    private LocalDate appliedDate;

    @NotNull(message = "Application status is required")
    private ApplicationStatus applicationStatus;

    @NotNull(message = "Job source is required")
    private JobSource jobSource;

    private String jobLink;
    private String notes;

}