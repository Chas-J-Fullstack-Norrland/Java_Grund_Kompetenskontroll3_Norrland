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
    private BookingFactory factory = new BookingFactory();
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
                case "SetAsComplete" -> bookingRepository.get(userInput.readNumberInput("Which BookingID was finished?")).setFinished(true); //Example!! Replace with proper implementation

                case "quit" -> running = false;
            }

            userInput.readAny("Press enter to continue");

        }

    }

}



