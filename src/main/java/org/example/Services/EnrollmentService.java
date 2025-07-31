package org.example.Services;

import org.example.Entities.Enrollment;
import org.example.Entities.EnrollmentId;
import org.example.Entities.User;
import org.example.Entities.Course;
import org.example.Repositories.EnrollmentRepository;
import org.example.Repositories.UserRepository;
import org.example.Repositories.CourseRepository;
import org.example.DTOs.EnrollmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        EnrollmentId id = new EnrollmentId(enrollmentDTO.getUserId(), enrollmentDTO.getCourseId());
        Optional<Enrollment> existing = enrollmentRepository.findById(id);
        if (existing.isPresent()) {
            throw new IllegalStateException("Enrollment already exists");
        }

        User user = userRepository.findById(enrollmentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Enrollment enrollment = new Enrollment(user, course, enrollmentDTO.getEnrollmentDate(), enrollmentDTO.getProgress());
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return convertToDTO(savedEnrollment);
    }

    public Page<EnrollmentDTO> getEnrollmentsByUser(Long userId, Pageable pageable) {
        return enrollmentRepository.findById_UserId(userId, pageable).map(this::convertToDTO);
    }

    public Page<EnrollmentDTO> getEnrollmentsByCourse(Long courseId, Pageable pageable) {
        return enrollmentRepository.findById_CourseId(courseId, pageable).map(this::convertToDTO);
    }

    public Page<EnrollmentDTO> findByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return enrollmentRepository.findByEnrollmentDateBetween(startDate, endDate, pageable).map(this::convertToDTO);
    }

    private EnrollmentDTO convertToDTO(Enrollment enrollment) {
        if (enrollment == null) return null;
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setUserId(enrollment.getId().getUserId());
        dto.setCourseId(enrollment.getId().getCourseId());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setProgress(enrollment.getProgress());
        return dto;
    }
}