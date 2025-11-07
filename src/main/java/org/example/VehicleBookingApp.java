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

        this.bookingRepository = repository;
        this.menu = menuOptions;
        this.userInput = IOReader;
        this.filterService = new BookingFilterService(repository);
        this.sortingService = new BookingSortingService(repository);


    }

    public void run(){

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

                    Integer idToRemove = userInput.readNumberInput("What is the ID of the booking you wish to remove?");

                    BookingReporter.outputSpecificBookingDetails(bookingRepository.get(idToRemove));

                    if(userInput.readTextInput("You are about to remove this booking, Type 'y' to proceed ").equals("y")){
                        Booking removedBooking = bookingRepository.remove(idToRemove);

                        if(removedBooking == null){
                            System.out.println("No such booking with that ID");
                        } else{
                            log.info("Removed booking {}", removedBooking);
                        }
                    }
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
                case "complete" -> this.completeBooking();

                case "quit" -> running = false;
            }

            userInput.readAny("Press enter to continue");

        }

    }

    private void completeBooking() {
        int id = userInput.readNumberInput("Select ID of completed booking");

        Booking booking = bookingRepository.get(id);

        if (booking == null) {
            log.warn("No booking with ID: {}", id);
            System.out.println("Error: Booking with said ID does not exist");
            return;
        }

        if (booking.isFinished()) {
            log.info("Booking {} is already marked completed", id);
            System.out.println("This booking is already completed");
            return;
        }

        if (booking instanceof BookedRepair repair) {
            log.debug("Booking {} is a repair, waiting for price", id);
            System.out.println("This booking is a repair, please set the price");

            Integer price = 0;
            boolean priceIsValid = false;

            while (!priceIsValid) {
                    price = userInput.readNumberInput("Enter the final price (ex. 4500): ");
                    if (price < 0) {
                        System.out.println("Price cannot be negative, try again");
                        log.warn("User entered a negative price: {}", price);
                    } else {
                        priceIsValid = true;
                    }
            }

            repair.setPrice(price);
            log.info("Price was set to {} for the repair with ID# {}", price, id);
            System.out.println("Price set to: " + price + " SEK");
        }

        booking.setFinished(true);
        mailBooking.sendBookingCompletion(booking);
        System.out.println("Booking " + id + " was marked as completed");
        log.info("Booking {} marked as complete", id);
    }

}



