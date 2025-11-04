package org.example.datamodels;

import java.time.LocalDateTime;

public class BookedInspection extends Booking implements PaidService {

    private double price;

<<<<<<< HEAD
    public BookedInspection(LocalDateTime date, String contactEmail) {
        super(date, contactEmail);
=======
    public BookedInspection(Vehicle vehicle,LocalDateTime date, String contactEmail) {
        super(vehicle, date, contactEmail);
>>>>>>> ea201ae4794f017dc3a609a532410a0efcbe3f93

        price = PriceList.inspectionPrice();
    }

    public double getPrice(){
        return price;
    }



}
