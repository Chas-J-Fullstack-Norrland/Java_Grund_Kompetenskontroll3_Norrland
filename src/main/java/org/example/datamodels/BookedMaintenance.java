package org.example.datamodels;

import java.time.LocalDateTime;

public class BookedMaintenance extends Booking implements PaidService {

    public double price;

    public BookedMaintenance(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        super(vehicle,date, contactEmail);

        price = PriceList.maintenancePrice(vehicle.getYearModel());

    }

    public BookedMaintenance(Booking booking) {
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
