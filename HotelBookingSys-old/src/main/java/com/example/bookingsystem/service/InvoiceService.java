package com.example.bookingsystem.service;

import javax.servlet.http.HttpServletResponse;

public interface InvoiceService {
    void printInvoice(HttpServletResponse httpServletResponse, Long bookingId);
}
