package org.example.services;

import org.example.DatabaseInitialisation;
import org.example.datamodels.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.DoubleStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final List<Booking> bookingDatabase;

    public BookingService(DatabaseInitialisation initService) {
        this.bookingDatabase = new ArrayList<>(initService.loadBookingsFromDatabase());
        log.info("BookingService initierad med {} bokningar.", bookingDatabase.size());
    }

    public List<Booking> getAllBookings() {
        return bookingDatabase;
    }

    public Optional<Booking> findBookingById(int id) {
        log.debug("Söker efter bokning med ID: {}", id);
        return bookingDatabase.stream()
                .filter(b -> b.getID() == id)
                .findFirst();
    }

    public Repair createNewRepair(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        Repair newRepair = new Repair(vehicle, date, contactEmail);
        this.bookingDatabase.add(newRepair);
        log.info("Ny reparation skapad (ID: {}) för fordon: {}", newRepair.getID(), vehicle.getRegistration());
        return newRepair;
    }

    public double calculateTotalPriceOfFinishedServices() {
        double total = bookingDatabase.stream()
                .filter(Booking::isFinished)
                .flatMapToDouble(booking -> {
                    if(booking instanceof PaidService p) {
                        return DoubleStream.of(p.getPrice());
                    } else {
                        return DoubleStream.empty();
                    }
                })
                .sum();

        log.info("Beräknat totalpris för alla klara tjänster: {}", total);
        return total;
    }
}
