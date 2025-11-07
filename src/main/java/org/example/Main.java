package org.example;



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