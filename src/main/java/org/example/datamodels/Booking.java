package org.example.datamodels;

import java.time.LocalDateTime;

public abstract class Booking implements PaidService {

    protected int id;
    protected Vehicle vehicle;
    protected static int nextID;
    protected LocalDateTime date;
    protected String contactEmail;
    protected boolean isFinished = false;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Booking(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        this.date = date;
        this.vehicle = vehicle;
        this.contactEmail = contactEmail;
        this.id = nextID;
        nextID++;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date=" + date +
                ", contactEmail='" + contactEmail + '\'' +
                ", isFinished=" + isFinished +
                '}';
    }
}
