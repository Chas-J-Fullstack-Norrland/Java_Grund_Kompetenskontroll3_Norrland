package org.example.datamodels;

import java.time.LocalDateTime;

public class BookedRepair extends Booking implements PaidService{
    private double price;

    public BookedRepair(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        super(vehicle, date, contactEmail);
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
}
