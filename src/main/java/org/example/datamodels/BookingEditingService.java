package org.example.datamodels;

import org.example.menu.TerminalMenu;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class BookingEditingService  {

 public Booking editBooking(Booking booking) {
     Set<String> menuOptions =  new TreeSet<>(
             Set.of(
                     "Date",
                     "Email",
                     "Registration",
                     "YearModel",
                     "Model",
                     "changeToRepair",
                     "changeToInspection",
                     "changeToMaintenance",
                     "done")
     );
     TerminalMenu menu = new TerminalMenu(menuOptions, System.in);
     

     Booking editedBooking = booking;
     while(true) {
         menu.printSelectionFields();
         System.out.println(booking.getClass().getSimpleName());
         switch(menu.selectMenuOption("Select a method to replace")) {
             
             case "Registration" -> editedBooking.getVehicle().setRegistration(menu.readTextInput("Vehicle Registration?"));
             case "Email" -> editedBooking.setContactEmail(menu.readTextInput("Customer email?"));
             case "Date" -> editedBooking.setDate(menu.parseDateTimeEntry());
             case "YearModel" -> editedBooking.getVehicle().setYearModel(menu.readNumberInput("ModelYear?"));
             case "Model" -> editedBooking.getVehicle().setModel(menu.readTextInput("Model?"));

             case "changeToRepair" -> editedBooking = new BookedRepair(booking,menu.readTextInput("what actions are to be done?"));
             case "changeToInspection" -> editedBooking = new BookedInspection(booking);
             case "changeToMaintenance" -> editedBooking = new BookedMaintenance(booking);

             case "done" ->{ return editedBooking; }
         }

     }
 }

}
