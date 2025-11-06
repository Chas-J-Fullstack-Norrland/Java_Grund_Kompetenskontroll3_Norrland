package org.example;


import ch.qos.logback.core.model.Model;
import org.example.datamodels.Booking;
import org.example.datamodels.BookingEditingService;
import org.example.datamodels.BookingFactory;
import org.example.datamodels.filters.BookingFilters;
import org.example.datamodels.filters.VehicleFilters;
import org.example.datamodels.sorters.BookingSorter;
import org.example.datamodels.sorters.VehicleSorter;
import org.example.menu.OptionSelectionInterface;
import org.example.menu.TerminalMenu;
import org.example.menu.UserInputInterface;
import org.example.repository.Repository;
import org.example.services.BookingFilterService;
import org.example.services.BookingReporter;
import org.example.services.BookingSortingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.Services.BookingDeletionService;

import java.awt.print.Book;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;


public class VehicleBookingApp {

    private Repository<Integer, Booking> bookingRepository;
    private OptionSelectionInterface menu;
    private UserInputInterface userInput;
    private BookingFilterService filterService;
    private BookingSortingService sortingService;
    private BookingDeletionService deletionService;
    private BookingFactory factory = new BookingFactory();
    private static final Logger log = LoggerFactory.getLogger(VehicleBookingApp.class);


    VehicleBookingApp(Repository<Integer,Booking> repository, OptionSelectionInterface menuOptions, UserInputInterface IOReader){

        this.bookingRepository = repository;
        this.menu = menuOptions;
        this.userInput = IOReader;
        this.filterService = new BookingFilterService(repository);
        this.sortingService = new BookingSortingService(repository);
        this.deletionService = new BookingDeletionService(repository);


    }

    public void run(){

    boolean running = true;

        while(running){

            //When adding options, remember to add them during the init step.
            menu.viewMenuOptions();
            switch(menu.selectMenuOption("How can I help you?")){

                case "new" -> {


                    Booking newBooking = factory.createBookingProcess();

                    bookingRepository.add(newBooking.getID(), newBooking);
                }
                case "edit" -> {
                    BookingEditingService edit = new BookingEditingService();
                    edit.editBooking(bookingRepository.get(userInput.readNumberInput("What is the ID of the booking we're looking for?")));
                }
                case "remove" -> {
                    int idToRemove = userInput.readNumberInput("What is the ID of the booking you wish to remove?");
                    Booking toRemove = bookingRepository.get(idToRemove);
                    if (toRemove == null) {
                        System.out.println("Ingen bokning med ID " + idToRemove);
                        break;
                    }
                    System.out.println("Du är på väg att ta bort följande bokning: ");
                    System.out.println(toRemove);
                    String confirm = userInput.readTextInput("Skriv 'JA' för att bekräfta borttagningen, annars lämna tomt:");
                    if (!"JA".equalsIgnoreCase(confirm.trim())) {
                        System.out.println("Borttagning avbröts.");
                        break;
                    }
                    try {
                        Booking removed = deletionService.deleteBooking(idToRemove);
                        if (removed != null) {
                            System.out.println("Bokning " + idToRemove + " borttagen.");
                            log.info("Bokning borttagen: id={}", idToRemove);
                        } else {
                            System.out.println("Bokning fanns inte vid borttagning.");
                            log.warn("Försökte ta bort icke-existerande bokning: id={}", idToRemove);
                        }
                    } catch (IllegalStateException ex) {
                        System.out.println("Borttagning nekad: " + ex.getMessage());
                        log.warn("Borttagning nekad för id={}: {}", idToRemove, ex.getMessage());
                    } catch (Exception ex) {
                        System.out.println("Fel vid borttagning: " + ex.getMessage());
                        log.error("Okänt fel vid borttagning id={}", idToRemove, ex);
                    }
                    //Booking removedBooking = bookingRepository.remove(userInput.readNumberInput("What is the ID of the booking you wish to remove?"));
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
                case "SetAsComplete" -> bookingRepository.get(userInput.readNumberInput("Which BookingID was finished?")).setFinished(true); //Example!! Replace with proper implementation

                case "quit" -> running = false;
            }

            userInput.readAny("Press enter to continue");

        }

    }

}



