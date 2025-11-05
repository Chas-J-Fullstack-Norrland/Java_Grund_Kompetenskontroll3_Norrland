package org.example.datamodels;

import java.time.LocalDateTime;

public class BookedMaintenance extends Booking implements PaidService {

    public double price;

    public BookedMaintenance(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        super(vehicle, date, contactEmail);

        price = PriceList.maintenencePrice(vehicle.getYearModel());

    }

    public double getPrice(){
        return price;
    }

}
