package org.example.Services;

import org.example.Entities.Payment;
import org.example.Entities.PaymentStatus;
import org.example.Repositories.PaymentRepository;
import org.example.DTOs.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentDTO confirmPayment(Long id) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);
        if (paymentOpt.isPresent() && paymentOpt.get().getStatus() == PaymentStatus.PAYABLE) {
            Payment payment = paymentOpt.get();
            payment.setPaymentDate(LocalDate.now());
            payment.setStatus(PaymentStatus.CONFIRMED);
            paymentRepository.save(payment);
            return convertToDTO(payment);
        }
        return null;
    }

    public PaymentDTO cancelPayment(Long id) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);
        if (paymentOpt.isPresent() && paymentOpt.get().getStatus() == PaymentStatus.PAYABLE) {
            Payment payment = paymentOpt.get();
            payment.setStatus(PaymentStatus.CANCELED);
            paymentRepository.save(payment);
            return convertToDTO(payment);
        }
        return null;
    }

    public Page<PaymentDTO> getPaymentsByUser(Long userId, Pageable pageable) {
        return paymentRepository.findByUser_UserId(userId, pageable)
                .map(this::convertToDTO);
    }

    public Page<PaymentDTO> findByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate, pageable)
                .map(this::convertToDTO);
    }

    public Page<PaymentDTO> findByStatus(String status, Pageable pageable) {
        return paymentRepository.findByStatus(status, pageable)
                .map(this::convertToDTO);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        if (payment == null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setUserId(payment.getUser().getUserId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setStatus(payment.getStatus().toString());
        return dto;
    }
}