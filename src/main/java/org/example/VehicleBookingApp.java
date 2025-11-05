package org.example;


import ch.qos.logback.core.model.Model;
import org.example.datamodels.Booking;
import org.example.datamodels.filters.BookingFilters;
import org.example.datamodels.filters.VehicleFilters;
import org.example.datamodels.sorters.BookingSorter;
import org.example.datamodels.sorters.VehicleSorter;
import org.example.menu.OptionSelectionInterface;
import org.example.menu.TerminalMenu;
import org.example.menu.UserInputInterface;
import org.example.repository.Repository;
import org.example.services.BookingReporter;
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
    private static final Logger log = LoggerFactory.getLogger(VehicleBookingApp.class);


    VehicleBookingApp(Repository<Integer,Booking> repository, OptionSelectionInterface menuOptions, UserInputInterface IOReader){

        this.bookingRepository = repository;
        this.menu = menuOptions;
        this.userInput = IOReader;


    }

    public void run(){

    boolean running = true;

        while(running){

            //When adding options, remember to add them during the init step.
            menu.viewMenuOptions();
            switch(menu.selectMenuOption("How can I help you?")){

                case "new" -> {


                    //Booking newBooking = bookingFactory.createBooking();

                    //bookingRepository.add(newBooking.getID(), newBooking);
                }
                //case "edit" -> BookingEditService/Menu.edit(repository.get(userinput.readNumberInput()))
                case "remove" -> {
                    Booking removedBooking = bookingRepository.remove(userInput.readNumberInput("What is the ID of the booking you wish to remove?"));
                    //log.info(removedBooking)
                }

                case "printall" -> BookingReporter.outputBookingSummary(bookingRepository.toList());
                case "printdetail" -> System.out.println(bookingRepository.get(userInput.readNumberInput("Which BookingID do you wish to view")).toString());
                case "printsorted" -> printSorted();
                case "printfiltered" -> {
                    try{
                        printFiltered();
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

    private void printFiltered() throws NullPointerException{

        Set<String> filterOptions = new LinkedHashSet<>(
                Set.of(
                        "vehicle olderthen",
                        "vehicle newerthen",
                        "model year",
                        "model name",
                        "registration",
                        "customer email",
                        "booking date",
                        "booking beforedate",
                        "booking afterdate",
                        "booking status"
                )
        );

        TerminalMenu filterSelection = new TerminalMenu(filterOptions,System.in);
        printSelectionFields(filterSelection.getMenuOptions());

        Predicate<Booking> predicate = null;
        switch (filterSelection.selectMenuOption("Select a filtering method")){

            case "vehicle olderthen" -> predicate = BookingFilters.forVehicle(VehicleFilters.yearModelOlderThan(filterSelection.readNumberInput("What year")));
            case "vehicle newerthen" -> predicate = BookingFilters.forVehicle(VehicleFilters.yearModelNewerThan(filterSelection.readNumberInput("What year")));

            case "model year" -> predicate = BookingFilters.forVehicle(VehicleFilters.yearModelEquals(filterSelection.readNumberInput("What year")));
            case "model name" -> predicate = BookingFilters.forVehicle(VehicleFilters.modelNameContains("Which string do we match?"));

            case "registration" -> predicate = BookingFilters.forVehicle(VehicleFilters.registrationEquals("Which reg?"));
            case "customer email" -> predicate = BookingFilters.customerEmailEquals("Enter the customers Email");

            case "booking type"-> predicate = BookingFilters.bookingType(filterSelection.readTextInput("What kind of appointment? ex Inspection,Repair,Maintenance"));
            case "booking id" -> predicate = BookingFilters.atDate(LocalDate.parse(filterSelection.readTextInput("What date 'yyyy-mm-dd'")));
            case "booking beforedate" ->  predicate = BookingFilters.dateOlderThan(parseDateEntry(filterSelection));
            case "booking afterdate" -> predicate = BookingFilters.dateNewerThan(parseDateEntry(filterSelection));
            case "booking date" -> {try{
                    predicate = BookingFilters.atDate(parseDateEntry(filterSelection).toLocalDate());
                } catch (NullPointerException e) {
                    log.error("Parsed",e);
                    throw e;
                }
            }
        }
        BookingReporter.outputBookingSummary(bookingRepository.getFilteredSet(predicate).stream().toList());
    }

    private LocalDateTime parseDateEntry(UserInputInterface menu){


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String input = menu.readTextInput("What date and time; Format 'yyyy-mm-dd hh:mm");

        try {
            return LocalDateTime.parse(input,dateTimeFormatter);
        } catch (DateTimeParseException e) {

            log.error("Could not parse input into date",e);
            throw e;
        }
    }

    private void printSorted(){

        Set<String> sortingOptions = new HashSet<>(
                Set.of(
                        "year",
                        "registration",
                        "id",
                        "date",
                        "status"
                )
        );
        TerminalMenu sortingSelection = new TerminalMenu(sortingOptions,System.in);
        printSelectionFields(sortingSelection.getMenuOptions());


        Comparator<Booking> comparator = null;
        switch(sortingSelection.selectMenuOption("Select a Method")){

            case "year" -> comparator = BookingSorter.sortByVehicle(VehicleSorter.sortByYearMade());
            case "registration" -> comparator = BookingSorter.sortByVehicle(VehicleSorter.sortByRegistration());
            case "id" -> comparator = BookingSorter.sortByID();
            case "date" -> comparator = BookingSorter.sortByDate();
            case "status" -> comparator = BookingSorter.sortByFinished();

        }

        try{
            BookingReporter.outputBookingSummary(
                    bookingRepository.getSortedCollection(comparator)
            );
        } catch ( NullPointerException e) {
            log.warn("Tried to sort with a nullObject", e);
        }

    }


    private void printSelectionFields(Set<String> optionsSet){

        System.out.println("--------------------------");
        System.out.println("Available fields:");
        optionsSet.forEach(System.out::println);
        System.out.println("--------------------------");

    }

}



