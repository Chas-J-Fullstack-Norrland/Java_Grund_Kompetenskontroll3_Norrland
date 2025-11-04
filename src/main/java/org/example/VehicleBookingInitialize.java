package org.example;

import org.example.datamodels.Booking;
import org.example.menu.TerminalMenu;
import org.example.repository.Repository;

import java.util.HashSet;
import java.util.Set;

public class VehicleBookingInitialize {

    Set<String> menuOptions = new HashSet<>(Set.of(
            "new","remove","printdetail","quit"
    ));

    TerminalMenu newMenu = new TerminalMenu(menuOptions,System.in);
    Repository<Integer, Booking> repository = new Repository<>();
    VehicleBookingApp app = new VehicleBookingApp(repository,newMenu,newMenu);

    public void setup(){
        app.run();
    }



}
