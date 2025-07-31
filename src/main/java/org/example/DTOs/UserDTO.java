package org.example.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    private Long userId;

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    private String password;

    @Email
    private String email;

    private Double totalPayment; // For total payment queries
    private Double averageProgress; // For progress queries
    private java.time.LocalDate lastPaymentDate; // For payment date queries

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Double getTotalPayment() { return totalPayment; }
    public void setTotalPayment(Double totalPayment) { this.totalPayment = totalPayment; }
    public Double getAverageProgress() { return averageProgress; }
    public void setAverageProgress(Double averageProgress) { this.averageProgress = averageProgress; }
    public java.time.LocalDate getLastPaymentDate() { return lastPaymentDate; }
    public void setLastPaymentDate(java.time.LocalDate lastPaymentDate) { this.lastPaymentDate = lastPaymentDate; }

    // Constructors for query projections
    public UserDTO() {}
    public UserDTO(Long userId, String name, String username, String email) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
    }
    public UserDTO(Long userId, String name, String username, String email, Double totalPayment) {
        this(userId, name, username, email);
        this.totalPayment = totalPayment;
    }
    public UserDTO(Long userId, String name, String username, String email, java.time.LocalDate lastPaymentDate) {
        this(userId, name, username, email);
        this.lastPaymentDate = lastPaymentDate;
    }
    public UserDTO(Long userId, String name, String username, String email, Double averageProgress, boolean isProgress) {
        this(userId, name, username, email);
        this.averageProgress = averageProgress;
    }
}