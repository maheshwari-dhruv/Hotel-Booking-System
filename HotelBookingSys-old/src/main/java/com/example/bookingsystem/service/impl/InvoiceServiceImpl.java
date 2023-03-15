package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.*;
import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.service.BookingService;
import com.example.bookingsystem.service.InvoiceService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
@Transactional
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
    private static final String breaker = "------------------------------------------------------------------------";
    private int invoiceCounter = 0;

    @Autowired
    private BookingService bookingService;

    @Override
    public void printInvoice(HttpServletResponse httpServletResponse, Long bookingId) {
        log.debug("Booking Id: " + bookingId);

        Booking booking = bookingService.getBookingById(bookingId);
        log.debug("Booking: " + booking);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=invoice_" + booking.getUser().getUsername() + "_" + LocalDate.now() + ".pdf";
        log.debug(headerValue);

        httpServletResponse.setHeader(headerKey, headerValue);
        printInvoicePdf(httpServletResponse, booking);
    }

    private void printInvoicePdf(HttpServletResponse httpServletResponse, Booking booking) {
        Double totalRoomCost = 0.0;
        Double totalFacilityCost = 0.0;

        Document document = new Document(PageSize.A4);

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(12);

        List<Room> bookedRooms = booking.getBookedRooms();
        log.debug("Booked Rooms: " + bookedRooms);

        User user = booking.getUser();
        log.debug("User: " + user);

        List<Guest> bookedRoomsGuest = booking.getBookedRoomsGuest();
        log.debug("Booked Rooms Guest: " + bookedRoomsGuest);

        List<Facility> facilities = booking.getCatalog().getCatalogFacilities();
        log.debug("Facilities: " + facilities);

        Payment payment = booking.getPayment();
        log.debug("Payment: " + payment);

        try {
            PdfWriter.getInstance(document, httpServletResponse.getOutputStream());
            document.open();
            Paragraph spacer = new Paragraph(breaker);

            printInvoiceHeader(document, spacer, fontTitle, fontParagraph);
            printBookingDetails(document, spacer, booking, fontTitle);
            printRoomDetails(document, spacer, bookedRooms, fontTitle);
            printGuestsDetails(document, spacer, bookedRoomsGuest, fontTitle);
            printFacilitiesDetails(document, spacer, facilities, fontTitle);
            printTotalCostOfBooking(document, spacer, totalRoomCost, totalFacilityCost, booking, fontTitle);

            if (booking.getIsPaymentMade()) {
                printPaymentDetails(document, spacer, payment, fontTitle);
            }

            printBillingTo(document, user, fontTitle, fontParagraph);
            log.info("Pdf generated successfully");
        } catch (IOException e) {
            log.error("Error creating pdf: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    private void printPaymentDetails(Document document, Paragraph spacer, Payment payment, Font fontTitle) {
        Paragraph paymentDetailsHeading = new Paragraph("Payment Details", fontTitle);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 2, 2, 2, 2 });
        table.setSpacingBefore(5);

        // Create Table Header
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Payment Id", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payment Made On", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payment Mode", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Payment Amount", fontTitle));
        table.addCell(cell);

        table.addCell(String.valueOf(payment.getId()));
        table.addCell(String.valueOf(payment.getPaymentDate()));
        table.addCell(payment.getPaymentMode());
        table.addCell(String.valueOf(payment.getTotalPaymentAmount()));

        document.add(paymentDetailsHeading);
        document.add(table);
        document.add(spacer);
        log.info("payment detail section generated");
    }

    private void printBillingTo(Document document, User user, Font fontTitle, Font fontParagraph) {
        Paragraph billingDetailsHeading = new Paragraph("Billing To Details", fontTitle);
        Paragraph userFullName = new Paragraph(user.getFirstName().toUpperCase() + " " + user.getLastName().toUpperCase(), fontTitle);
        Paragraph userEmail = new Paragraph(user.getEmail().toUpperCase(), fontParagraph);
        Paragraph userPhoneNo = new Paragraph(user.getPhone(), fontParagraph);

        document.add(billingDetailsHeading);
        document.add(userFullName);
        document.add(userEmail);
        document.add(userPhoneNo);
        log.info("Billing To section generated");
    }

    private void printTotalCostOfBooking(Document document, Paragraph spacer, Double totalRoomCost, Double totalFacilityCost, Booking booking, Font fontTitle) {
        Paragraph totalCostDetailsHeading = new Paragraph("Total Cost Details", fontTitle);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 2, 2, 2 });
        table.setSpacingBefore(5);

        // Create Table Header
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Net Room Cost", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Net Facility Cost", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total Net Cost", fontTitle));
        table.addCell(cell);

        for (Room room: booking.getBookedRooms()) {
            totalRoomCost += room.getCost();
        }

        for (Facility facility: booking.getCatalog().getCatalogFacilities()) {
            totalFacilityCost += facility.getPrice();
        }

        table.addCell(String.valueOf(String.format("%.2f", totalRoomCost)));
        table.addCell(String.valueOf(String.format("%.2f", totalFacilityCost)));
        table.addCell(String.valueOf(String.format("%.2f", totalRoomCost + totalFacilityCost)));

        document.add(totalCostDetailsHeading);
        document.add(table);
        document.add(spacer);
        log.info("payment detail section generated");
    }

    private void printFacilitiesDetails(Document document, Paragraph spacer, List<Facility> facilities, Font fontTitle) {
        Paragraph facilityDetailsHeading = new Paragraph("Facility Details", fontTitle);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 2, 2 });
        table.setSpacingBefore(5);

        // Create Table Header
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Facility Name", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Facility Price", fontTitle));
        table.addCell(cell);

        for (Facility facility: facilities) {
            table.addCell(facility.getName().toUpperCase());
            table.addCell(String.valueOf(facility.getPrice()));
        }

        document.add(facilityDetailsHeading);
        document.add(table);
        document.add(spacer);
        log.info("facility detail section generated");
    }

    private void printGuestsDetails(Document document, Paragraph spacer, List<Guest> bookedRoomsGuest, Font fontTitle) {
        Paragraph guestDetailsHeading = new Paragraph("Guest Details", fontTitle);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 2, 2 });
        table.setSpacingBefore(5);

        // Create Table Header
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Guest Name", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Guest Age", fontTitle));
        table.addCell(cell);

        for (Guest guest: bookedRoomsGuest) {
            table.addCell(guest.getFirstName().toUpperCase() + " " + guest.getLastName().toUpperCase());
            table.addCell(String.valueOf(guest.getAge()));
        }

        document.add(guestDetailsHeading);
        document.add(table);
        document.add(spacer);
        log.info("guest detail section generated");
    }

    private void printRoomDetails(Document document, Paragraph spacer, List<Room> bookedRooms, Font fontTitle) {
        Paragraph roomDetailsHeading = new Paragraph("Room Details", fontTitle);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 2, 2, 2, 2 });
        table.setSpacingBefore(5);

        // Create Table Header
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Room No.", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Room Type", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Room Occupancy", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Room Cost", fontTitle));
        table.addCell(cell);

        for (Room room: bookedRooms) {
            table.addCell(String.valueOf(room.getId()));
            table.addCell(room.getType().toUpperCase());
            table.addCell(String.valueOf(room.getAdults() + room.getChildren()));
            table.addCell(String.valueOf(room.getCost()));
        }

        document.add(roomDetailsHeading);
        document.add(table);
        document.add(spacer);
        log.info("room detail section generated");
    }

    private void printBookingDetails(Document document, Paragraph spacer, Booking booking, Font fontTitle) {
        String numOfDaysStay = String.valueOf(Period.between(booking.getCheckInDate(), booking.getCheckOutDate()).getDays());

        Paragraph bookingDetailsHeading = new Paragraph("Booking Details", fontTitle);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 2, 2, 2, 2, 2, 2 });
        table.setSpacingBefore(5);

        // Create Table Header
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Booking Id", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Check In Date", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Check Out Date", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("No. of Days", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("No. of Rooms", fontTitle));
        table.addCell(cell);
        cell.setPhrase(new Phrase("No. of Guests", fontTitle));
        table.addCell(cell);

        table.addCell(String.valueOf(booking.getId()));
        table.addCell(String.valueOf(booking.getCheckInDate()));
        table.addCell(String.valueOf(booking.getCheckOutDate()));
        table.addCell(numOfDaysStay);
        table.addCell(String.valueOf(booking.getBookedRooms().size()));
        table.addCell(String.valueOf(booking.getBookedRoomsGuest().size()));

        document.add(bookingDetailsHeading);
        document.add(table);
        document.add(spacer);
        log.info("booking detail section generated");
    }

    private void printInvoiceHeader(Document document, Paragraph spacer, Font fontTitle, Font fontParagraph) {
        String count = String.valueOf(invoiceCounter++);
        String date = String.valueOf(LocalDate.now());

        Paragraph hotelName = new Paragraph("Hotel GrandView", fontTitle);
        Paragraph invoice = new Paragraph("Invoice", fontTitle);
        Paragraph invoiceNo = new Paragraph("Invoice No: " + count, fontParagraph);
        Paragraph invoiceDate = new Paragraph("Date: " + date, fontParagraph);

        document.add(hotelName);
        document.add(invoice);
        document.add(invoiceNo);
        document.add(invoiceDate);
        document.add(spacer);
        log.info("Header section generated");
    }
}
