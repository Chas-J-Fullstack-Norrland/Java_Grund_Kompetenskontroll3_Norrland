package org.example;

import org.example.datamodels.BookedInspection;
import org.example.datamodels.Booking;
import org.example.datamodels.Car;
import org.example.datamodels.Vehicle;
import org.example.datamodels.sorters.BookingSorter;
import org.example.datamodels.sorters.VehicleSorter;
import org.example.menu.TerminalMenu;
import org.example.repository.Repository;
import org.example.services.BookingReporter;
import org.example.services.InitializationService;
import org.example.factory.LogFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {

    static {
        LogFactory.loggingContext();
    }

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.error("--- TEST: Logg-systemet har startat och detta FEL ska sparas i JSON-filen. ---");
        log.info("Applikationen startar nu...");

        VehicleBookingInitialize startupProgram = new VehicleBookingInitialize();
        startupProgram.setup();


    }
}