package org.example.Repositories;

import org.example.Entities.Enrollment;
import org.example.Entities.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
    Page<Enrollment> findById_UserId(Long userId, Pageable pageable);
    Page<Enrollment> findById_CourseId(Long courseId, Pageable pageable);
    Page<Enrollment> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}