package org.example.DTOs;

import jakarta.validation.constraints.NotBlank;
import org.example.Entities.Category;
import org.example.Entities.Difficulty;

public class CourseDTO {
    private Long courseId;

    @NotBlank
    private String title;

    private String category; // Stored as String, converted from Category enum
    private String difficulty; // Stored as String, converted from Difficulty enum

    // Default constructor
    public CourseDTO() {}

    // Constructor for queries returning courseId, title
    public CourseDTO(Long courseId, String title) {
        this.courseId = courseId;
        this.title = title;
    }

    // Constructor for findCoursesNotTaken query
    public CourseDTO(Long courseId, String title, Category category, Difficulty difficulty) {
        this.courseId = courseId;
        this.title = title;
        this.category = category != null ? category.name() : null;
        this.difficulty = difficulty != null ? difficulty.name() : null;
    }

    // Constructor for queries with additional fields (e.g., average progress)
    public CourseDTO(Long courseId, String title, Double averageProgress) {
        this.courseId = courseId;
        this.title = title;
    }

    // Constructor for queries with difficulty and count
    public CourseDTO(Long courseId, String title, Difficulty difficulty, Long count) {
        this.courseId = courseId;
        this.title = title;
        this.difficulty = difficulty != null ? difficulty.name() : null;
    }

    // Getters and Setters
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
}