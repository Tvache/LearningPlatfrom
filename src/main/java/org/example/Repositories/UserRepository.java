package org.example.Repositories;

import org.example.Entities.User;
import org.example.DTOs.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


    @Query("SELECT new org.example.DTOs.UserDTO(u.userId, u.name, u.username, u.email, SUM(p.amount)) " +
            "FROM User u JOIN u.payments p " +
            "WHERE p.status = 'COMPLETED' " +
            "GROUP BY u.userId, u.name, u.username, u.email " +
            "ORDER BY SUM(p.amount) DESC " +
            "LIMIT 3")
    List<UserDTO> findTop3UsersByTotalPayment();

    @Query("SELECT new org.example.DTOs.UserDTO(u.userId, u.name, u.username, u.email) " +
            "FROM User u LEFT JOIN u.payments p " +
            "WHERE p IS NULL OR p.status != 'COMPLETED' " +
            "GROUP BY u.userId, u.name, u.username, u.email")
    List<UserDTO> findUsersWithNoSuccessfulPayments();

    @Query("SELECT new org.example.DTOs.UserDTO(u.userId, u.name, u.username, u.email, MAX(p.paymentDate)) " +
            "FROM User u JOIN u.payments p " +
            "WHERE p.status = 'COMPLETED' " +
            "GROUP BY u.userId, u.name, u.username, u.email")
    List<UserDTO> findMostRecentSuccessfulPaymentPerUser();

    @Query("SELECT new org.example.DTOs.UserDTO(u.userId, u.name, u.username, u.email, AVG(e.progress), true) " +
            "FROM User u JOIN u.enrollments e " +
            "GROUP BY u.userId, u.name, u.username, u.email " +
            "ORDER BY AVG(e.progress) DESC")
    List<UserDTO> findAverageProgressPerUser();

    Page<User> findByEmailContaining(String email, Pageable pageable);
}