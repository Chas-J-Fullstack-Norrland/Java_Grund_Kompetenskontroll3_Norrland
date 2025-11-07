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
            registration = menu.readTextInput("Skriv in din registreringsskylt. Format ABC-123").toUpperCase();

           if(lpValidator.validateLicensePlate(registration)){

                break;
            }
            System.out.println("Ogiltigt registreringsskyltformat. Försök igen.");
        }

            String Model = menu.readTextInput("Skriv in din bilmodell");
            int yearModel = menu.readNumberInput("Vilken årsmodell har du?");
            LocalDateTime date = menu.parseDateTimeEntry();


            String contactEmail;
            while (true){

                contactEmail = menu.readTextInput("Skriv in din e-mailadress tack.");
                if (Mailfilter.validateEmail(contactEmail)){
                    break;
                }
                System.out.println("Ogiltigt registreringsskyltformat. Försök igen.");

            }


            Vehicle vehicle = new Car(registration, Model, yearModel);
            Booking booking = null;
            menu.printSelectionFields();
            switch (menu.selectMenuOption("Vad vill du boka?")) {
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

