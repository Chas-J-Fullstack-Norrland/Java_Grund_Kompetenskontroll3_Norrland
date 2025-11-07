package org.example.services;

import org.example.datamodels.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;

import static java.lang.String.format;


public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void sendBookingConfirmation(Booking booking) {
        if (booking == null) {
            logger.warn("Attempted to send booking confirmation for null booking.");
            return;
        }


        String bookingMessage = format("Booking Confirmation:\nBooking ID: %s\nDate: %s\nContact Email: %s\n",
                booking.getID(),
                booking.getDate().format(FMT),
                booking.getContactEmail());

        logger.info("Sending booking confirmation email:\n{}", bookingMessage);
    }

    public void sendBookingCompletion(Booking booking) {

        if (booking == null) {
            logger.warn("Attempted to send completion confirmation for null booking.");
            return;
        }

        String completion = format("Booking Confirmation:\nBooking ID: %s\nDate: %s\nContact Email: %s\nPrice%s\n",
                booking.getID(),
                booking.getDate().format(FMT),
                booking.getContactEmail(),
                format("Price: %.2f SEK\n", booking.getPrice())

        );

        logger.info("Sending booking completion email:\n{}", completion);
    }


}
