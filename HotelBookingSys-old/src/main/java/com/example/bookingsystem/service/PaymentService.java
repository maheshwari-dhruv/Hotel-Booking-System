package com.example.bookingsystem.service;

import com.example.bookingsystem.domain.Payment;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<String> makePayment(Long bookingId, Payment payment);
}
