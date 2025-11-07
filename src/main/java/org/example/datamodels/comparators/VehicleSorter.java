package org.example.datamodels.comparators;

import org.example.datamodels.Vehicle;

import java.util.Comparator;

public class VehicleSorter {

    public static Comparator<Vehicle> sortByRegistration(){
        return Comparator.comparing(Vehicle::getRegistration);
    }

    public static Comparator<Vehicle> sortByYearMade(){
        return Comparator.comparing(Vehicle::getYearModel).reversed();
    }


}
