package org.example;

import org.example.datamodels.Booking;
import org.example.menu.TerminalMenu;
import org.example.repository.Repository;
import org.example.services.InitializationService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class VehicleBookingInitialize {

    Set<String> menuOptions = new LinkedHashSet<>(Set.of(
            "new", "edit", "remove","printdetail","printfiltered","printsorted","quit"
    ));

    TerminalMenu newMenu = new TerminalMenu(menuOptions,System.in);
    InitializationService dataBaseMock = new InitializationService();
    Repository<Integer, Booking> repository = new Repository<>(
            dataBaseMock.loadBookingsFromDatabase().stream().collect(Collectors.toMap(Booking::getID,b->b)));
    VehicleBookingApp app = new VehicleBookingApp(repository,newMenu,newMenu);

    public void setup(){
        app.run();
    }



}
