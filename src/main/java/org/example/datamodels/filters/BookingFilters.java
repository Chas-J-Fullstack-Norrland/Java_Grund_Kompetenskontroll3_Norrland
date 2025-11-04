package org.example.datamodels.filters;

import org.example.datamodels.Booking;
import org.example.datamodels.Vehicle;

import java.util.function.Predicate;

public class BookingFilters {

    public static Predicate<Booking> forVehicle(Predicate<Vehicle> vehiclePredicate){
        return booking -> vehiclePredicate.test(booking.getVehicle());
    }

    public static Predicate<Booking> bookingType(String className){

       return booking -> booking.getClass().getSimpleName().equals(className);

    }

    public static Predicate<Booking> isFinished(){
        return Booking::isFinished;
    }

    public static Predicate<Booking> customerEmailEquals(String email){
        return booking -> booking.getContactEmail().equals(email);
    }


}
