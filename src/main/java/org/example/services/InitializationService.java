package org.example.services;

import org.example.datamodels.Booking;
import org.example.datamodels.bookedInspection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InitializationService {
    public List<Booking> loadBookingsFromDatabase() {
        List<Booking> databaseBookings = new ArrayList<>();

        bookedInspection b1 = new bookedInspection(
                LocalDateTime.of(2025, 11, 10, 14, 30),
                "anna.svensson@example.com"
        );
        b1.setId(1);
        b1.setFinished(true);

        bookedInspection b2 = new bookedInspection(
                LocalDateTime.of(2025, 11, 12, 9, 0),
                "karl.nilsson@example.com"
        );
        b2.setId(2);
        b2.setFinished(false);

        bookedInspection b3 = new bookedInspection(
                LocalDateTime.of(2025, 11, 20, 10, 0),
                "maria.eriksson@example.com"
        );
        b3.setId(3);
        b3.setFinished(true);

        bookedInspection b4 = new bookedInspection(
                LocalDateTime.of(2025, 11, 10, 14, 30),
                "pelle.olsson@example.com"
        );
        b4.setId(4);
        b4.setFinished(false);

        databaseBookings.add(b1);
        databaseBookings.add(b2);
        databaseBookings.add(b3);
        databaseBookings.add(b4);

        Booking.initNextID(5);

        return databaseBookings;
    }
}
