package com.example.bookingsystem.controller;

import com.example.bookingsystem.domain.Payment;
import com.example.bookingsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/booking={bookingId}")
    public ResponseEntity<String> makePayment(@PathVariable Long bookingId, @RequestBody Payment payment) {
        return paymentService.makePayment(bookingId, payment);
    }
}
