package org.example.datamodels;

import org.example.menu.TerminalMenu;
import org.example.validators.LicensePlateFilter;
import org.example.validators.Mailfilter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BookingFactory {
    public Booking createInspection(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        return new BookedInspection(vehicle, date, contactEmail);
    }

    public Booking createRepair(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        return new BookedRepair(vehicle, date, contactEmail);
    }

    public Booking createMaintenance(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        return new BookedMaintenance(vehicle, date, contactEmail);
    }

    public Booking createBookingProcess() {

        LicensePlateFilter lpValidator = new LicensePlateFilter();
        Set<String> menuOptions = new HashSet<>(Set.of("Inspection", "Repair", "Maintenance"));

        TerminalMenu menu = new TerminalMenu(menuOptions, System.in);

        String registration;
        while (true) {
            registration = menu.readTextInput("Registration Plate?. Format ABC 123").toUpperCase();

           if(lpValidator.validateLicensePlate(registration)){

                break;
            }
            System.out.println("Registration# "+registration +". is not of viable format. Please try again");
        }

            String Model = menu.readTextInput("Model of the vehicle?");
            int yearModel = menu.readNumberInput("Year of model?");
            LocalDateTime date = menu.parseDateTimeEntry();


            String contactEmail;
            while (true){

                ;
                if (Mailfilter.validateEmail(contactEmail = menu.readTextInput("Enter the customers Email"))){
                    break;
                }
                System.out.println("Invalid format. Try again.");

            }


            Vehicle vehicle = new Car(registration, Model, yearModel);
            Booking booking = null;
            menu.printSelectionFields();
            switch (menu.selectMenuOption("What is the purpose of the booking?")) {
                case "Inspection" -> {
                    booking = createInspection(vehicle, date, contactEmail);
                }
                case "Repair" -> {
                    booking = createRepair(vehicle, date, contactEmail);
                }
                case "Maintenance" -> {
                    booking = createMaintenance(vehicle, date, contactEmail);
                }

            }
            return booking;

        }
    }

