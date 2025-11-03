package org.example.datamodels;

import java.time.LocalDateTime;

public class bookedInspection extends Booking implements PaidService {

    private double price;

    public bookedInspection(LocalDateTime date, String contactEmail) {
        super(date, contactEmail);

        price = PriceList.inspectionPrice();
    }

    public double getPrice(){
        return price;
    }



}
