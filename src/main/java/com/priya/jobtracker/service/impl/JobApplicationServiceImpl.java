package com.priya.jobtracker.service.impl;

import com.priya.jobtracker.dto.application.ApplicationRequestDto;
import com.priya.jobtracker.dto.application.ApplicationResponseDto;
import com.priya.jobtracker.dto.application.DashboardResponseDto;
import com.priya.jobtracker.entity.JobApplication;
import com.priya.jobtracker.enums.ApplicationStatus;
import com.priya.jobtracker.enums.Role;
import com.priya.jobtracker.repository.JobApplicationRepository;
import com.priya.jobtracker.repository.UserRepository;
import com.priya.jobtracker.service.JobApplicationService;
import com.priya.jobtracker.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobApplicationServiceImpl implements JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    public ApplicationResponseDto mapToResponse(JobApplication saved) {

        return ApplicationResponseDto.builder()
                .id(saved.getId())
                .companyName(saved.getCompanyName())
                .jobTitle(saved.getJobTitle())
                .jobType(saved.getJobType())
                .location(saved.getLocation())
                .appliedDate(saved.getAppliedDate())
                .applicationStatus(saved.getApplicationStatus())
                .jobSource(saved.getJobSource())
                .jobLink(saved.getJobLink())
                .notes(saved.getNotes())
                .userId(saved.getUser().getId())
                .userName(saved.getUser().getName())
                .build();
    }

    private JobApplication findApplicationEntityById(Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
    @Override
    public ApplicationResponseDto createApplication(ApplicationRequestDto request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobApplication jobApplication = JobApplication.builder()
                .companyName(request.getCompanyName())
                .jobTitle(request.getJobTitle())
                .jobType(request.getJobType())
                .location(request.getLocation())
                .appliedDate(request.getAppliedDate())
                .applicationStatus(request.getApplicationStatus())
                .jobSource(request.getJobSource())
                .jobLink(request.getJobLink())
                .notes(request.getNotes())
                .user(user)
                .build();

        JobApplication saved = jobApplicationRepository.save(jobApplication);

        return mapToResponse(saved);
    }

    @Override
    public List<ApplicationResponseDto> getAllApplications(
            int page,
            int size,
            ApplicationStatus status,
            String search
    ) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page, size);

        Page<JobApplication> result;
        if(user.getRole().equals(Role.ADMIN)){
            result=jobApplicationRepository.findAll(pageable);
        }else if (status != null && search != null && !search.isBlank()) {
            result = jobApplicationRepository
                    .findByUserAndApplicationStatusAndCompanyNameContainingIgnoreCase(
                            user, status, search, pageable
                    );
        } else if (status != null) {
            result = jobApplicationRepository
                    .findByUserAndApplicationStatus(user, status, pageable);
        } else if (search != null && !search.isBlank()) {
            result = jobApplicationRepository
                    .findByUserAndCompanyNameContainingIgnoreCase(user, search, pageable);
        } else {
            result = jobApplicationRepository
                    .findByUser(user, pageable);
        }

        return result.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public ApplicationResponseDto getApplicationById(Long id) {

        return mapToResponse(findApplicationEntityById(id));
    }


    @Override
    public ApplicationResponseDto updateApplication(Long id, ApplicationRequestDto request) {
        JobApplication existing = findApplicationEntityById(id);

        existing.setCompanyName(request.getCompanyName());
        existing.setJobTitle(request.getJobTitle());
        existing.setJobType(request.getJobType());
        existing.setLocation(request.getLocation());
        existing.setAppliedDate(request.getAppliedDate());
        existing.setApplicationStatus(request.getApplicationStatus());
        existing.setJobSource(request.getJobSource());
        existing.setJobLink(request.getJobLink());
        existing.setNotes(request.getNotes());

        return mapToResponse(jobApplicationRepository.save(existing));
    }

    @Override
    public DashboardResponseDto getDashboard(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return DashboardResponseDto.builder()
                .total(jobApplicationRepository.countByUser(user))
                .rejected(jobApplicationRepository.countByUserAndApplicationStatus(user,ApplicationStatus.REJECTED))
                .offered(jobApplicationRepository.countByUserAndApplicationStatus(user,ApplicationStatus.OFFERED))
                .shortlisted(jobApplicationRepository.countByUserAndApplicationStatus(user,ApplicationStatus.SHORTLISTED))
                .interview(jobApplicationRepository.countByUserAndApplicationStatus(user,ApplicationStatus.INTERVIEW))
                .applied(jobApplicationRepository.countByUserAndApplicationStatus(user,ApplicationStatus.APPLIED))
                .build();
    }

    @Override
    public DashboardResponseDto getAdminDashboard() {

        return DashboardResponseDto.builder()
                .total(jobApplicationRepository.count())
                .applied(jobApplicationRepository.countByApplicationStatus(ApplicationStatus.APPLIED))
                .shortlisted(jobApplicationRepository.countByApplicationStatus(ApplicationStatus.SHORTLISTED))
                .interview(jobApplicationRepository.countByApplicationStatus(ApplicationStatus.INTERVIEW))
                .rejected(jobApplicationRepository.countByApplicationStatus(ApplicationStatus.REJECTED))
                .offered(jobApplicationRepository.countByApplicationStatus(ApplicationStatus.OFFERED))
                .build();
    }

    @Override
    public void deleteApplication(Long id) {
        JobApplication existing = findApplicationEntityById(id);
        jobApplicationRepository.delete(existing);
    }

}
