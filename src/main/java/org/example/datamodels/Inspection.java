package org.example.datamodels;

import org.example.predefined.PriceList;

import java.time.LocalDateTime;

public class Inspection extends Booking implements PaidService {

    private final double price;


    public Inspection(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        super(vehicle ,date, contactEmail);

        price = PriceList.inspectionPrice();
    }

    public Inspection(Booking booking){
        super();
        this.id = booking.id;
        this.vehicle = booking.vehicle;
        this.contactEmail = booking.contactEmail;
        this.date = booking.date;
        this.price = PriceList.inspectionPrice();
    }

    public double getPrice(){
        return price;
    }



}
