package com.priya.jobtracker.repository;

import com.priya.jobtracker.entity.JobApplication;
import com.priya.jobtracker.enums.ApplicationStatus;
import com.priya.jobtracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long>{

    Page<JobApplication> findByUserAndApplicationStatus(User user, ApplicationStatus status, Pageable pageable);

    Page<JobApplication> findByUserAndCompanyNameContainingIgnoreCase(User user, String company, Pageable pageable);

    Page<JobApplication> findByUserAndApplicationStatusAndCompanyNameContainingIgnoreCase(
            User user,
            ApplicationStatus status,
            String company,
            Pageable pageable
    );


    Page<JobApplication> findAll(Pageable pageable);

    long countByUser(User user);

    long countByUserAndApplicationStatus(User user, ApplicationStatus status);

    long countByApplicationStatus(ApplicationStatus status);

    Page<JobApplication> findByUser(User user, Pageable pageable);
}