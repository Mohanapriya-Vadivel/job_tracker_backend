package com.priya.jobtracker.controller;

import com.priya.jobtracker.dto.application.ApplicationRequestDto;
import com.priya.jobtracker.dto.application.ApplicationResponseDto;
import com.priya.jobtracker.dto.application.DashboardResponseDto;
import com.priya.jobtracker.entity.JobApplication;
import com.priya.jobtracker.entity.User;
import com.priya.jobtracker.enums.ApplicationStatus;
import com.priya.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping()
    public ResponseEntity<ApplicationResponseDto> createApplication(
            @Valid @RequestBody ApplicationRequestDto request
    ) {
        ApplicationResponseDto savedApplication = jobApplicationService.createApplication(request);
        return ResponseEntity.ok(savedApplication);
    }

    @GetMapping()
    public ResponseEntity<List<ApplicationResponseDto>> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) String search
            ) {
        List<ApplicationResponseDto> applications = jobApplicationService.getAllApplications(page,size,status,search);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDto> getDashboard() {
        return ResponseEntity.ok(jobApplicationService.getDashboard());
    }
    @GetMapping("/admin/dashboard")
    public ResponseEntity<DashboardResponseDto> getAdminDashboard() {
        return ResponseEntity.ok(jobApplicationService.getAdminDashboard());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDto> getApplicationById(@PathVariable Long id) {
        ApplicationResponseDto application = jobApplicationService.getApplicationById(id);
        return ResponseEntity.ok(application);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDto> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationRequestDto request
    ) {
        ApplicationResponseDto updatedApplication = jobApplicationService.updateApplication(id, request);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable Long id) {
        jobApplicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully");
    }
}