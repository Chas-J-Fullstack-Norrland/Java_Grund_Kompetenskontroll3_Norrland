package org.example.datamodels.comparators;

import org.example.datamodels.Booking;
import org.example.datamodels.Vehicle;

import java.util.Comparator;

public class BookingSorter {

    public static Comparator<Booking> sortByDate(){
        return Comparator.comparing(Booking::getDate);
    }

    public static Comparator<Booking> sortByVehicle(Comparator<Vehicle> vehicleComparator){
        return (b1,b2)-> vehicleComparator.compare(b1.getVehicle(), b2.getVehicle());
    }

    public static Comparator<Booking> sortByFinished(){
       return Comparator.comparing(Booking::isFinished);
    }

    public static Comparator<Booking> sortByID(){
        return Comparator.comparing(Booking::getID);
    }


}
