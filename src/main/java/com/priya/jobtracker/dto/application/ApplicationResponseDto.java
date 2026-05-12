package com.priya.jobtracker.dto.application;

import com.priya.jobtracker.enums.ApplicationStatus;
import com.priya.jobtracker.enums.JobSource;
import com.priya.jobtracker.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDto {

    private Long id;

    private String companyName;

    private String jobTitle;

    private JobType jobType;

    private String location;

    private LocalDate appliedDate;

    private ApplicationStatus applicationStatus;

    private JobSource jobSource;

    private String jobLink;

    private String notes;

    private Long userId;

    private String userName;

}