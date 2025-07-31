package org.example.Repositories;

import org.example.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findByUser_UserId(Long userId, Pageable pageable);
    Page<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Payment> findByStatus(String status, Pageable pageable);
}