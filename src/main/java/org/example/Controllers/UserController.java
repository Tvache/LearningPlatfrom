package org.example.Controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.example.Repositories.CourseRepository;
import org.example.Repositories.UserRepository;
import org.example.Services.UserService;
import org.example.DTOs.CourseDTO;
import org.example.DTOs.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserService userService;

    //transactional
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.saveUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/top3-users-by-payment")
    @PermitAll
    @RolesAllowed("Admin")
    public ResponseEntity<List<UserDTO>> getTop3UsersByPayment() {
        List<UserDTO> results = userRepository.findTop3UsersByTotalPayment();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/course-highest-progress")
    @PermitAll
    public ResponseEntity<List<CourseDTO>> getCourseWithHighestProgress() {
        List<CourseDTO> results = courseRepository.findCourseWithHighestAverageProgress();
        return ResponseEntity.ok(results.isEmpty() ? List.of() : results.subList(0, 1));
    }

    @GetMapping("/users-no-payments")
    public ResponseEntity<List<UserDTO>> getUsersWithNoPayments() {
        List<UserDTO> results = userRepository.findUsersWithNoSuccessfulPayments();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/unenrolled-courses")
    public ResponseEntity<List<CourseDTO>> getUnenrolledCourses() {
        List<CourseDTO> results = courseRepository.findCoursesNotTaken();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/average-progress-per-user")
    public ResponseEntity<List<UserDTO>> getAverageProgressPerUser() {
        List<UserDTO> results = userRepository.findAverageProgressPerUser();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/most-popular-courses")
    public ResponseEntity<List<CourseDTO>> getMostPopularCourses() {
        List<CourseDTO> results = courseRepository.findMostPopularCoursePerDifficulty();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/high-progress-courses")
    public ResponseEntity<List<CourseDTO>> getHighProgressCourses() {
        List<CourseDTO> results = courseRepository.findCoursesWithMajorityHighProgress();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/recent-payments")
    public ResponseEntity<List<UserDTO>> getRecentPayments() {
        List<UserDTO> results = userRepository.findMostRecentSuccessfulPaymentPerUser();
        return ResponseEntity.ok(results);
    }

    //create enrollment
}