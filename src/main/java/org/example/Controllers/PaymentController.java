package org.example.Controllers;

import org.example.DTOs.PaymentDTO;
import org.example.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<PaymentDTO> confirmPayment(@PathVariable Long id) {
        PaymentDTO paymentDTO = paymentService.confirmPayment(id);
        if (paymentDTO != null) {
            return ResponseEntity.ok(paymentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<PaymentDTO> cancelPayment(@PathVariable Long id) {
        PaymentDTO paymentDTO = paymentService.cancelPayment(id);
        if (paymentDTO != null) {
            return ResponseEntity.ok(paymentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PaymentDTO>> getPaymentsByUser(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        Page<PaymentDTO> payments = paymentService.getPaymentsByUser(userId, pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Page<PaymentDTO>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable
    ) {
        Page<PaymentDTO> payments = paymentService.findByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/status")
    public ResponseEntity<Page<PaymentDTO>> findByStatus(
            @RequestParam String status,
            Pageable pageable
    ) {
        Page<PaymentDTO> payments = paymentService.findByStatus(status, pageable);
        return ResponseEntity.ok(payments);
    }
}
