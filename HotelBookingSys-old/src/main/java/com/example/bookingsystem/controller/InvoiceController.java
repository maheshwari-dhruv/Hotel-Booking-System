package com.example.bookingsystem.controller;

import com.example.bookingsystem.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v2/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/generate/{bookingId}")
    public ResponseEntity<String> printInvoice(HttpServletResponse response, @PathVariable Long bookingId) {
        invoiceService.printInvoice(response, bookingId);
        return ResponseEntity.ok().body("Pdf generated successfully");
    }
}
