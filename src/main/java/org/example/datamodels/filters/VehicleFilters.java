package org.example.datamodels.filters;

import org.example.datamodels.Vehicle;
import java.util.function.Predicate;

public class VehicleFilters {

    public static Predicate<Vehicle> yearModelOlderThan(int year){
        return v -> v.getYearModel() < year;
    }

    public static Predicate<Vehicle> yearModelNewerThan(int year){
        return v -> v.getYearModel() > year;
    }

    public static Predicate<Vehicle> yearModelEquals(int year){
        return v -> v.getYearModel() == year;
    }


    public static Predicate<Vehicle> modelNameContains(String s){
        return vehicle-> vehicle.getModel().contains(s);
    }

    public static Predicate<Vehicle> modelName(String s){
        return vehicle-> vehicle.getModel().equals(s);
    }

    public static Predicate<Vehicle> registrationEquals(String s){
        return vehicle -> vehicle.getRegistration().equals(s);
    }


}
