package org.example.datamodels;

public abstract class Vehicle {

    protected String registration;
    protected String model;
    protected int yearModel;

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYearModel(int yearModel) {
        this.yearModel = yearModel;
    }

    public Vehicle(String registration, String model, int yearModel) {
        this.registration = registration;
        this.model = model;
        this.yearModel = yearModel;
    }

    public String getRegistration() {
        return registration;
    }

    public String getModel() {
        return model;
    }

    public int getYearModel() {
        return yearModel;
    }

    @Override
    public String toString() {
        return "vehicle{" +
                "registration='" + registration + '\'' +
                ", model='" + model + '\'' +
                ", yearModel=" + yearModel +
                '}';
    }
}
