package org.example.datamodels;

import java.time.LocalDateTime;

public class BookedInspection extends Booking implements PaidService {

    private final double price;


    public BookedInspection(Vehicle vehicle,LocalDateTime date, String contactEmail) {
        super(vehicle ,date, contactEmail);

        price = PriceList.inspectionPrice();
    }

    public BookedInspection(Booking booking){
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
