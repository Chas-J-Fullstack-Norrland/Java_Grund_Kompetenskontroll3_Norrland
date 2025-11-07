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
                new Car("ABC-123","Nizzan",1995),
                LocalDateTime.of(2025, 4, 10, 14, 30),
                "anna.svensson@example.com"
        );

        BookedInspection b2 = new BookedInspection(
                new Car("MEH-123","Toyottah Macro",1995),
                LocalDateTime.of(2025, 6, 12, 9, 0),
                "karl.nilsson@example.com"
        );

        BookedInspection b3 = new BookedInspection(
                new Car("ABC-132","Volvo Shoebox",1995),
                LocalDateTime.of(2025, 11, 20, 10, 0),
                "maria.eriksson@example.com"
        );

        BookedInspection b4 = new BookedInspection(
                new Car("USA-555","StationWagon v7",1995),
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
