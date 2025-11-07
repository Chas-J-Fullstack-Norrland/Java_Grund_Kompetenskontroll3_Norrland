package org.example.datamodels.predicates;

import org.example.datamodels.Booking;
import org.example.datamodels.Vehicle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

public class BookingFilters {

    public static Predicate<Booking> forVehicle(Predicate<Vehicle> vehiclePredicate){
        return booking -> vehiclePredicate.test(booking.getVehicle());
    }

    public static Predicate<Booking> bookingType(String className){

       return booking -> booking.getClass().getSimpleName().toLowerCase().contains(className.toLowerCase());

    }

    public static Predicate<Booking> dateOlderThan(LocalDateTime dateTime){

        return booking -> booking.getDate().isBefore(dateTime);

    }

    public static Predicate<Booking> dateNewerThan(LocalDateTime dateTime){

        return booking -> booking.getDate().isAfter(dateTime);

    }

    public static Predicate<Booking> atDate(LocalDate date){

        return booking -> booking.getDate().toLocalDate().equals(date);

    }


    public static Predicate<Booking> isFinished(){
        return Booking::isFinished;
    }

    public static Predicate<Booking> customerEmailEquals(String email){
        return booking -> booking.getContactEmail().equals(email);
    }


}
