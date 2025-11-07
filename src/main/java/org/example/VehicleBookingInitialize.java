package org.example;

import org.example.datamodels.Booking;
import org.example.menu.TerminalMenu;
import org.example.repository.Repository;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class VehicleBookingInitialize {

    Set<String> menuOptions = new TreeSet<>(Set.of(
            "new", "edit", "remove","complete","printall","printdetail","printfiltered","printsorted","quit"
    ));

    TerminalMenu newMenu = new TerminalMenu(menuOptions,System.in);
    DatabaseInitialisation dataBaseMock = new DatabaseInitialisation();
    Repository<Integer, Booking> repository = new Repository<>(
            dataBaseMock.loadBookingsFromDatabase().stream().collect(Collectors.toMap(Booking::getID,b->b)));
    VehicleBookingApp app = new VehicleBookingApp(repository,newMenu,newMenu);

    public void setup(){
        app.run();
    }



}
