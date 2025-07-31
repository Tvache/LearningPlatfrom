package org.example.Services;

import org.example.Entities.Category;
import org.example.Entities.Course;
import org.example.Entities.Difficulty;
import org.example.Repositories.CourseRepository;
import org.example.DTOs.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO getCourseById(Long id) {
        return convertToDTO(courseRepository.findById(id).orElse(null));
    }

    public CourseDTO saveCourse(CourseDTO courseDTO) {
        return convertToDTO(courseRepository.save(convertToEntity(courseDTO)));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Page<CourseDTO> findByTitleContaining(String title, Pageable pageable) {
        return courseRepository.findByTitleContaining(title, pageable)
                .map(this::convertToDTO);
    }

    public List<CourseDTO> findCourseWithHighestAverageProgress() {
        return courseRepository.findCourseWithHighestAverageProgress();
    }

    public List<CourseDTO> findCoursesNotTaken() {
        return courseRepository.findCoursesNotTaken();
    }

    public List<CourseDTO> findMostPopularCoursePerDifficulty() {
        return courseRepository.findMostPopularCoursePerDifficulty();
    }

    public List<CourseDTO> findCoursesWithMajorityHighProgress() {
        return courseRepository.findCoursesWithMajorityHighProgress();
    }

    private CourseDTO convertToDTO(Course course) {
        if (course == null) return null;
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
        dto.setCategory(course.getCategory() != null ? course.getCategory().name() : null);
        dto.setDifficulty(course.getDifficulty() != null ? course.getDifficulty().name() : null);
        return dto;
    }

    private Course convertToEntity(CourseDTO dto) {
        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setTitle(dto.getTitle());
        course.setCategory(dto.getCategory() != null ? Category.valueOf(dto.getCategory()) : null);
        course.setDifficulty(dto.getDifficulty() != null ? Difficulty.valueOf(dto.getDifficulty()) : null);
        return course;
    }
}