package org.example.Services;

import org.example.datamodels.Booking;
import org.example.datamodels.bookedInspection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;

import static java.lang.String.format;


public class MailBooking  {
    private static final Logger logger = LoggerFactory.getLogger(MailBooking.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void sendBookingConfirmation(Booking booking) {
        if (booking == null) {
            logger.warn("Attempted to send booking confirmation for null booking.");
            return;
        }



        String bookingMessage = format("Booking Confirmation:\nBooking ID: %s\nDate: %s\nContact Email: %s\n",
                booking.getID(), booking.getDate(),booking.getContactEmail());

        logger.info("Sending booking confirmation email:\n{}", bookingMessage);
    }

    public void sendBookingCompletion(bookedInspection inspection) {
        String completion = format("Booking Confirmation:\nBooking ID: %s\nDate: %s\nContact Email: %s\nPrice%s/n",
                inspection.getID(),
                inspection.getDate().format(FMT),
                inspection.getContactEmail(),
                inspection.getPrice()

        );

        logger.info("Sending booking completion email:\n{}", completion);
    }


}
