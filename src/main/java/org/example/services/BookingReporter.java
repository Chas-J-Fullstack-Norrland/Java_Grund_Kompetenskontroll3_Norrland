package org.example.services;

import org.example.datamodels.Inspection;
import org.example.datamodels.Booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingReporter {

    private static final Logger log = LoggerFactory.getLogger(BookingReporter.class);

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd 'kl.' HH:mm");

    private BookingReporter() {}

    public static void outputBookingSummary(List<Booking> bookings) {

        log.info("Created Summary of {} number of bookings", bookings.size());

        System.out.println("--- BookingReport ---");
        System.out.println("ID\tDate\t\t\tStatus");
        System.out.println("------------------------------------------");

        if (bookings.isEmpty()){
            System.out.println("No bookings in system");
        } else {
            bookings.stream()
                    .map(BookingReporter::formatBookingsLine)
                    .forEach(System.out::println);
        }

        System.out.println("------------------------------------------");
    }

    private static String formatBookingsLine(Booking booking) {
        int id = booking.getID();
        String formatDate = booking.getDate().format(formatter);
        String status = booking.isFinished() ? "Done" : "Waiting";
        return id + "\t" + formatDate + "\t" + status;
    }

    public static void outputSpecificBookingDetails(Booking booking) {

        if (booking == null) {
            log.warn("outputSpecificBookingDetails was called with null object");
            System.out.println("--- ERROR ---");
            System.out.println("Internal Error Occurred");
            System.out.println("-----------");
            return;
        }

        log.info("Printing Detailed summary for Booking ID: {}", booking.getID());

        System.out.println("--- Detailed Summary ---");
        System.out.println("ID: \t\t" + booking.getID());
        String status = booking.isFinished() ? "Done" : "Waiting";
        System.out.println("Status: \t" + status);
        System.out.println("Date: \t\t" + booking.getDate().format(formatter));
        System.out.println("Contact: \t" + booking.getContactEmail());

        if (booking instanceof Inspection inspection) {
            System.out.println("Type: \t\tInspection");
            System.out.println("Price: \t\t" + inspection.getPrice() + " SEK");
        }
        System.out.println("---------------------------------");
    }
}