package org.example.datamodels;

import org.example.menu.TerminalMenu;

import java.util.Set;

public class BookingEditingService  {

 public void editBooking(Booking b) {
     Set<String> menuOptions = Set.of("Date", "Email", "Registration", "YearModel", "Model", "done");
     TerminalMenu menu = new TerminalMenu(menuOptions, System.in);
     
     
     while(true) {
         menu.printSelectionFields();
         switch(menu.selectMenuOption("Vilken information vill du byta ut?")) {
             
             case "Registration" -> b.getVehicle().setRegistration(menu.readTextInput("Vilket fordon var det?"));
             case "Email" -> b.setContactEmail(menu.readTextInput("Uppdatera emailadressen vi når kunden med"));
             case "Date" -> b.setDate(menu.parseDateTimeEntry());
             case "YearModel" -> b.getVehicle().setYearModel(menu.readNumberInput("Årsmodell?"));
             case "Model" -> b.getVehicle().setModel(menu.readTextInput("Modellen?"));
             case "done" ->{ return; }
         }

     }
 }

}
