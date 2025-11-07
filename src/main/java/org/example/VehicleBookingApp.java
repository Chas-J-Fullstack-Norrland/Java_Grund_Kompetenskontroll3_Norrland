package org.example;

import org.example.datamodels.BookedRepair;
import org.example.datamodels.Booking;
import org.example.datamodels.BookingEditingService;
import org.example.datamodels.BookingFactory;
import org.example.factory.LogFactory;
import org.example.menu.OptionSelectionInterface;
import org.example.menu.UserInputInterface;
import org.example.repository.Repository;
import org.example.services.BookingFilterService;
import org.example.services.BookingReporter;
import org.example.services.BookingSortingService;
import org.example.Services.MailBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class VehicleBookingApp {

    private final Repository<Integer, Booking> bookingRepository;
    private final OptionSelectionInterface menu;
    private final UserInputInterface userInput;
    private final BookingFilterService filterService;
    private final BookingSortingService sortingService;
    private final BookingFactory factory = new BookingFactory();
    private final MailBooking mailBooking = new MailBooking();

    private static final Logger log = LoggerFactory.getLogger(VehicleBookingApp.class);


    VehicleBookingApp(Repository<Integer,Booking> repository, OptionSelectionInterface menuOptions, UserInputInterface IOReader){
        LogFactory.loggingContext();
        this.bookingRepository = repository;
        this.menu = menuOptions;
        this.userInput = IOReader;
        this.filterService = new BookingFilterService(repository);
        this.sortingService = new BookingSortingService(repository);


    }

    public void run(){
    log.info("Program started, Test log");
    boolean running = true;

        while(running){
            //When adding options, remember to add them during the init step.
            menu.viewMenuOptions();
            switch(menu.selectMenuOption("How can I help you?")){

                case "new" -> {


                    Booking newBooking = factory.createBookingProcess();
                    mailBooking.sendBookingConfirmation(newBooking);
                    bookingRepository.add(newBooking.getID(), newBooking);
                }
                case "edit" -> {
                    BookingEditingService edit = new BookingEditingService();
                    Booking editedBooking = edit.editBooking(bookingRepository.get(userInput.readNumberInput("What is the ID of the booking we're looking for?")));
                    bookingRepository.replace(editedBooking.getID(),editedBooking);
                }
                case "remove" -> {
                    Booking removedBooking = bookingRepository.remove(userInput.readNumberInput("What is the ID of the booking you wish to remove?"));
                    //log.info(removedBooking)
                }

                case "printall" -> BookingReporter.outputBookingSummary(bookingRepository.toList());
                case "printdetail" -> BookingReporter.outputSpecificBookingDetails(bookingRepository.get(userInput.readNumberInput("Which BookingID do you wish to view")));
                case "printsorted" ->{
                    try{
                        BookingReporter.outputBookingSummary(sortingService.printSorted());
                    }catch (NullPointerException e){
                        log.error("Ended up with a null comparator",e);
                    }
                }
                case "printfiltered" -> {
                    try{
                        BookingReporter.outputBookingSummary(filterService.printFiltered());
                    }catch (NullPointerException e){
                        log.error("Ended up with a null predicate",e);
                    }
                }
                case "SetAsComplete" -> this.completeBooking();

                case "quit" -> running = false;
            }

            userInput.readAny("Press enter to continue");

        }

    }

    private void completeBooking() {
        int id = userInput.readNumberInput("Vilket boknings-ID vill du slutföra?");

        Booking booking = bookingRepository.get(id);

        if (booking == null) {
            log.warn("Kunde inte hitta någon bokning med ID: {}", id);
            System.out.println("Fel: Hittade ingen bokning med det ID:t.");
            return;
        }

        if (booking.isFinished()) {
            log.info("Bokning {} är redan markerad som slutförd.", id);
            System.out.println("Denna bokning är redan markerad som klar.");
            return;
        }

        if (booking instanceof BookedRepair repair) {
            log.debug("Bokning {} är en reparation, frågar efter pris.", id);
            System.out.println("Detta är en reparation. Ett pris måste anges.");

            double price = 0;
            boolean priceIsValid = false;

            while (!priceIsValid) {
                String priceString = userInput.readTextInput("Ange mekanikerns slutpris (t.ex. 4500.50): ");
                try {
                    price = Double.parseDouble(priceString);
                    if (price < 0) {
                        System.out.println("Priset kan inte vara negativt. Försök igen.");
                        log.warn("Användaren angav ett negativt pris: {}", price);
                    } else {
                        priceIsValid = true;
                    }
                } catch (NumberFormatException e) {
                    log.warn("Ogiltig pris-input: {}", priceString, e);
                    System.out.println("Ogiltigt format. Använd siffror (t.ex. 4500.50).");
                }
            }

            repair.setPrice(price);
            log.info("Pris satt till {} för reparation {}", price, id);
            System.out.println("Pris uppdaterat till: " + price + " SEK");
        }

        booking.setFinished(true);
        mailBooking.sendBookingCompletion(booking);
        System.out.println("Bokning " + id + " har markerats som slutförd.");
        log.info("Bokning {} markerad som slutförd.", id);
    }

}



