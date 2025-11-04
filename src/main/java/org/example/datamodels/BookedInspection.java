package org.example.datamodels;

import java.time.LocalDateTime;

public class BookedInspection extends Booking implements PaidService {

    private double price;

    public BookedInspection(LocalDateTime date, String contactEmail) {
        super(date, contactEmail);

        price = PriceList.inspectionPrice();
    }

    public double getPrice(){
        return price;
    }



}
