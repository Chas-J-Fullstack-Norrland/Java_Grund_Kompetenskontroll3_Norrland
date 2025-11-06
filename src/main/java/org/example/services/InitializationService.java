package org.example.services;

import org.example.datamodels.Booking;
import org.example.datamodels.BookedInspection;
import org.example.datamodels.Car;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InitializationService {
    public List<Booking> loadBookingsFromDatabase() {
        List<Booking> databaseBookings = new ArrayList<>();

        BookedInspection b1 = new BookedInspection(
                new Car("abc-123","Nizzan",1995),
                LocalDateTime.of(2025, 4, 10, 14, 30),
                "anna.svensson@example.com"
        );
        b1.setFinished(false);

        BookedInspection b2 = new BookedInspection(
                new Car("meh-123","Toyotta macro",1995),
                LocalDateTime.of(2025, 6, 12, 9, 0),
                "karl.nilsson@example.com"
        );
        b2.setFinished(false);

        BookedInspection b3 = new BookedInspection(
                new Car("abc-132","Volvo shoebox",1995),
                LocalDateTime.of(2025, 11, 20, 10, 0),
                "maria.eriksson@example.com"
        );
        b3.setFinished(false);

        BookedInspection b4 = new BookedInspection(
                new Car("USA-555","StationVagon v7",1995),
                LocalDateTime.of(2025, 12, 10, 14, 30),
                "pelle.olsson@example.com"
        );

        databaseBookings.add(b1);
        databaseBookings.add(b2);
        databaseBookings.add(b3);
        databaseBookings.add(b4);

        return databaseBookings;
    }
}
