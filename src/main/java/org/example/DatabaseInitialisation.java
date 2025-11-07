package org.example;

import org.example.datamodels.Booking;
import org.example.datamodels.Inspection;
import org.example.datamodels.Car;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitialisation {
    public List<Booking> loadBookingsFromDatabase() {
        List<Booking> databaseBookings = new ArrayList<>();

        Inspection b1 = new Inspection(
                new Car("ABC-123","Nizzan",1995),
                LocalDateTime.of(2025, 4, 10, 14, 30),
                "anna.svensson@example.com"
        );

        Inspection b2 = new Inspection(
                new Car("MEH-123","Toyottah Macro",1995),
                LocalDateTime.of(2025, 6, 12, 9, 0),
                "karl.nilsson@example.com"
        );

        Inspection b3 = new Inspection(
                new Car("ABC-132","Volvo Shoebox",1995),
                LocalDateTime.of(2025, 11, 20, 10, 0),
                "maria.eriksson@example.com"
        );

        Inspection b4 = new Inspection(
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
