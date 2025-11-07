package org.example.datamodels;

import org.example.predefined.PriceList;

import java.time.LocalDateTime;

public class Maintenance extends Booking implements PaidService {

    public double price;

    public Maintenance(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        super(vehicle,date, contactEmail);

        price = PriceList.maintenancePrice(vehicle.getYearModel());

    }

    public Maintenance(Booking booking) {
        super();
        this.id = booking.id;
        this.vehicle = booking.vehicle;
        this.contactEmail = booking.contactEmail;
        this.date = booking.date;
        this.price = PriceList.maintenancePrice(vehicle.getYearModel());
    }

    public double getPrice(){
        return price;
    }

}
