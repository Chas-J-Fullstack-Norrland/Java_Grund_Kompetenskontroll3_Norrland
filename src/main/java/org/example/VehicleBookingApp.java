package org.example;


import org.example.datamodels.Booking;
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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

                //case "printall" -> bookingRepository.print()
                case "printdetail" -> System.out.println(bookingRepository.get(userInput.readNumberInput("Which BookingID do you wish to view")).toString());
                case "printsorted" -> printSorted();
                case "SetAsComplete" -> bookingRepository.get(userInput.readNumberInput("Which BookingID was finished?")).setFinished(true); //Example!! Replace with proper implementation

                case "quit" -> running = false;
            }
        }

    }

    private void printFiltered(){

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



