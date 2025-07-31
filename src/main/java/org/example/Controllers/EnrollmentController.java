package org.example.Controllers;

import org.example.DTOs.EnrollmentDTO;
import org.example.Services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO created = enrollmentService.createEnrollment(enrollmentDTO);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<EnrollmentDTO>> getEnrollmentsByUser(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        Page<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByUser(userId, pageable);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<EnrollmentDTO>> getEnrollmentsByCourse(
            @PathVariable Long courseId,
            Pageable pageable
    ) {
        Page<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByCourse(courseId, pageable);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<EnrollmentDTO>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable
    ) {
        Page<EnrollmentDTO> enrollments = enrollmentService.findByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(enrollments);
    }
}
