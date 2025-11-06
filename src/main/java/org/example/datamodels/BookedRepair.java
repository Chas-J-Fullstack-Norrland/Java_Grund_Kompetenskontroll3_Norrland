package org.example.datamodels;

import java.time.LocalDateTime;

public class BookedRepair extends Booking implements PaidService{
    private double price;
    private String action;


    public BookedRepair(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        super(vehicle, date, contactEmail);
    }

    public BookedRepair(Booking booking, String action){
        super();
        this.id = booking.id;
        this.vehicle = booking.vehicle;
        this.contactEmail = booking.contactEmail;
        this.date = booking.date;
        this.action = action;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String getAction() {return action;}
    public void setAction(String rectify) {this.action = rectify;}
}
