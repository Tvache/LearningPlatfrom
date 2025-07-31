package org.example.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EnrollmentId implements java.io.Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "course_id")
    private Long courseId;

    // Default constructor
    public EnrollmentId() {
    }

    // Constructor
    public EnrollmentId(Long userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return userId.equals(that.userId) && courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(userId, courseId);
    }
}