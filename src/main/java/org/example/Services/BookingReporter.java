package org.example.services;

import org.example.datamodels.BookedInspection;
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

        log.info("Skapar bokningssummering för {} bokningar...", bookings.size());

        System.out.println("--- Bokningsrapport ---");
        System.out.println("ID\tDatum\t\t\tStatus");
        System.out.println("------------------------------------------");

        if (bookings.isEmpty()){
            System.out.println("Inga bokningar att visa.");
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
        String status = booking.isFinished() ? "Klar" : "Väntande";
        return id + "\t" + formatDate + "\t" + status;
    }

    public static void outputSpecificBookingDetails(Booking booking) {

        if (booking == null) {
            log.warn("outputSpecificBookingDetails anropades med ett null-objekt.");
            System.out.println("--- Fel ---");
            System.out.println("Ett internt fel inträffade.");
            System.out.println("-----------");
            return;
        }

        log.info("Skriver ut detaljer för bokning ID: {}", booking.getID());

        System.out.println("--- Detaljerad information ---");
        System.out.println("ID: \t\t" + booking.getID());
        String status = booking.isFinished() ? "Klar" : "Väntande";
        System.out.println("Status: \t" + status);
        System.out.println("Datum: \t\t" + booking.getDate().format(formatter));
        System.out.println("Kontakt: \t" + booking.getContactEmail());

        if (booking instanceof BookedInspection inspection) {
            System.out.println("Typ: \t\tBesiktning");
            System.out.println("Pris: \t\t" + inspection.getPrice() + " SEK");
        }
        System.out.println("---------------------------------");
    }
}