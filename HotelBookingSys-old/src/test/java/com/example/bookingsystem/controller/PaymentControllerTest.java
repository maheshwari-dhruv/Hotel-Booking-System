package com.example.bookingsystem.controller;

import com.example.bookingsystem.domain.Booking;
import com.example.bookingsystem.domain.Payment;

import com.example.bookingsystem.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Test
    void testPayment() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentMode("cash");
        payment.setTotalPaymentAmount(23.0);
        payment.setBooking(new Booking());
        payment.setId(123L);

        String content = (new ObjectMapper()).writeValueAsString(payment);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/payment/booking={bookingId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }
}

