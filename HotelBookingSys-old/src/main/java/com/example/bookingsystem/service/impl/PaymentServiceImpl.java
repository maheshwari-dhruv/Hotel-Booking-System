package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.Booking;
import com.example.bookingsystem.domain.Payment;
import com.example.bookingsystem.repository.PaymentRepository;
import com.example.bookingsystem.service.BookingService;
import com.example.bookingsystem.service.PaymentService;
import com.example.bookingsystem.validation.BookingValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;

@Service
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private Double totalCost = 0.0;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public ResponseEntity<String> makePayment(Long bookingId, Payment payment) {
        log.debug("Function makePayment - bookingId: " + bookingId + " | payment: " + payment);
        Booking booking = bookingService.getBookingById(bookingId);

        log.debug("booking fetched: " + booking);

        if (BookingValidation.checkBooking(booking)) {
            log.error("booking details incorrect");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "booking details incorrect");
        }

        int numOfDaysStay = Period.between(booking.getCheckInDate(), booking.getCheckOutDate()).getDays();

        booking.getBookedRooms().forEach(room -> {
            totalCost += room.getCost();
        });

        booking.getCatalog().getCatalogFacilities().forEach(facility -> {
            totalCost += facility.getPrice();
        });

        totalCost *= numOfDaysStay;

        log.info("making payment");
        Payment makePayment = new Payment();

        log.debug("Payment mode: " + payment.getPaymentMode());
        makePayment.setPaymentMode(payment.getPaymentMode());

        log.debug("Date: " + LocalDate.now());
        makePayment.setPaymentDate(LocalDate.now());
        booking.setIsPaymentMade(true);

        log.debug("Booking: " + booking);
        makePayment.setBooking(booking);

        log.debug("Total cost: " + totalCost);
        makePayment.setTotalPaymentAmount(totalCost);

        log.debug("Payment: " + makePayment);
        booking.setPayment(makePayment);

        paymentRepository.save(makePayment);
        log.info("payment made successfully" + makePayment);
        return ResponseEntity.ok().body("payment made successfully");
    }
}
