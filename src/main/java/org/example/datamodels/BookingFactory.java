package org.example.datamodels;

import org.example.menu.TerminalMenu;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BookingFactory {
    public Booking createInspection(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        return new BookedInspection(vehicle,date, contactEmail);
    }
    public Booking createRepair(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        return new BookedRepair(vehicle,date, contactEmail);
    }
    public Booking createMaintenance(Vehicle vehicle, LocalDateTime date, String contactEmail) {
        return new BookedMaintenance(vehicle,date, contactEmail);
    }
    public Booking createBookingProcess() {

        Set<String> menuOptions = new HashSet<>(Set.of("Inspection", "Repair","Maintenance"));

        TerminalMenu menu = new TerminalMenu(menuOptions, System.in);

        String registration = menu.readTextInput("Skriv in din registreringsskylt");
        String Model = menu.readTextInput("Skriv in din bilmodell");
        int yearModel = menu.readNumberInput("Vilken årsmodell har du?");
        LocalDateTime date = LocalDateTime.parse(menu.readTextInput("Vilken tid vill du boka?"));
        String contactEmail = menu.readTextInput("Skriv in din e-mailadress tack.");

        Vehicle vehicle = new Car(registration, Model, yearModel);
        Booking booking = null;
        switch (menu.selectMenuOption("Vad vill du boka?")) {
            case "Inspection" -> { booking = createInspection(vehicle, date, contactEmail); }
            case "Repair" -> { booking = createRepair(vehicle, date, contactEmail); }
            case "Maintenance" -> { booking = createMaintenance(vehicle, date, contactEmail); }

        }
        return booking;

    }
}
