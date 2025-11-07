package org.example.services;

import org.example.datamodels.Inspection;
import org.example.datamodels.Maintenance;
import org.example.datamodels.Repair;
import org.example.datamodels.Booking;
import org.example.menu.TerminalMenu;

import java.util.Set;
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

             case "changeToRepair" -> editedBooking = new Repair(booking,menu.readTextInput("what actions are to be done?"));
             case "changeToInspection" -> editedBooking = new Inspection(booking);
             case "changeToMaintenance" -> editedBooking = new Maintenance(booking);

             case "done" ->{ return editedBooking; }
         }

     }
 }

}
