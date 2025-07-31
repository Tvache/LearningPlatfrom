package org.example.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "Enrollments")
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    @NotNull
    private Course course;

    @Column(name = "enrollment_date", nullable = false)
    @NotNull
    private LocalDate enrollmentDate;

    @Column(name = "progress")
    @Min(0)
    @Max(100)
    private Integer progress;

    // Default constructor
    public Enrollment() {
    }

    // Constructor
    public Enrollment(User user, Course course, LocalDate enrollmentDate, Integer progress) {
        if (user == null || course == null) {
            throw new IllegalArgumentException("User and Course cannot be null");
        }
        this.user = user;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.progress = progress;
        this.id = new EnrollmentId(user.getUserId(), course.getCourseId());
    }

    // Getters and Setters
    public EnrollmentId getId() { return id; }
    public void setId(EnrollmentId id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
}