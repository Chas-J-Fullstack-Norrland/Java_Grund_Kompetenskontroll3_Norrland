package org.example.services;

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

    public BookingService(InitializationService initService) {
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

    public BookedRepair createNewRepair(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        BookedRepair newRepair = new BookedRepair(vehicle, date, contactEmail);
        this.bookingDatabase.add(newRepair);
        log.info("Ny reparation skapad (ID: {}) för fordon: {}", newRepair.getID(), vehicle.getRegistration());
        return newRepair;
    }

    public void completeRepair(int bookingId, double price) {
        Optional<Booking> bookingOpt = findBookingById(bookingId);

        if (bookingOpt.isPresent() && bookingOpt.get() instanceof BookedRepair repair) {
            repair.setPrice(price);
            repair.setFinished(true);
            log.info("Reparation {} slutförd. Pris satt till: {}", bookingId, price);
        } else {
            log.warn("Kunde inte slutföra reparation: Hittade inte ID {} eller så var det inte en 'BookedRepair'.", bookingId);
        }
    }

    public void completeBooking(int bookingId) {
        Optional<Booking> bookingOpt = findBookingById(bookingId);

        if (bookingOpt.isEmpty()) {
            log.warn("Kunde inte slutföra bokning: ID {} hittades ej.", bookingId);
            return;
        }

        Booking booking = bookingOpt.get();

        if(booking instanceof BookedRepair) {
            log.warn("FEL: 'completeBooking' (utan pris) kan inte användas på en reparation (ID: {}). " +
                    "Använd 'completeRepair(id, price)' istället.", bookingId);
            return;
        }

        booking.setFinished(true);
        log.info("Bokning {} (Typ: {}) har markerats som slutförd.",
                bookingId, booking.getClass().getSimpleName());
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
