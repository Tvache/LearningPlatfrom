package org.example.Repositories;

import org.example.Entities.Course;
import org.example.DTOs.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT new org.example.DTOs.CourseDTO(c.courseId, c.title, AVG(e.progress)) " +
            "FROM Course c JOIN c.enrollments e " +
            "GROUP BY c.courseId, c.title " +
            "ORDER BY AVG(e.progress) DESC")
    List<CourseDTO> findCourseWithHighestAverageProgress();

    @Query("SELECT new org.example.DTOs.CourseDTO(c.courseId, c.title, c.category, c.difficulty) " +
            "FROM Course c LEFT JOIN c.enrollments e " +
            "WHERE e IS NULL")
    List<CourseDTO> findCoursesNotTaken();

    @Query("SELECT new org.example.DTOs.CourseDTO(c.courseId, c.title, c.difficulty, COUNT(e.user)) " +
            "FROM Course c JOIN c.enrollments e " +
            "GROUP BY c.courseId, c.title, c.difficulty " +
            "HAVING COUNT(e.user) = (" +
            "SELECT MAX(COUNT(e2.user)) " +
            "FROM Course c2 JOIN c2.enrollments e2 " +
            "WHERE c2.difficulty = c.difficulty " +
            "GROUP BY c2.courseId)")
    List<CourseDTO> findMostPopularCoursePerDifficulty();

    @Query("SELECT new org.example.DTOs.CourseDTO(c.courseId, c.title) " +
            "FROM Course c JOIN c.enrollments e " +
            "GROUP BY c.courseId, c.title " +
            "HAVING SUM(CASE WHEN e.progress >= 80 THEN 1.0 ELSE 0 END) / COUNT(e.user) > 0.5")
    List<CourseDTO> findCoursesWithMajorityHighProgress();

    Page<Course> findByTitleContaining(String title, Pageable pageable);
}