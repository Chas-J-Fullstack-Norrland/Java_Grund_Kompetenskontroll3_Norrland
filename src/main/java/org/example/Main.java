package org.example;

import org.example.datamodels.BookedInspection;
import org.example.datamodels.Booking;
import org.example.datamodels.Car;
import org.example.datamodels.Vehicle;
import org.example.menu.TerminalMenu;
import org.example.repository.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


    VehicleBookingInitialize startupProgram = new VehicleBookingInitialize();
    startupProgram.setup();


    }
}