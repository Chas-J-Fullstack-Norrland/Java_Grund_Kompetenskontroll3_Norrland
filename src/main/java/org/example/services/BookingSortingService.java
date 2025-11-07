package org.example.services;

import org.example.datamodels.Booking;
import org.example.datamodels.sorters.BookingSorter;
import org.example.datamodels.sorters.VehicleSorter;
import org.example.menu.TerminalMenu;
import org.example.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BookingSortingService {

    private final Repository<Integer, Booking> repository;
    private static final Logger log = LoggerFactory.getLogger(BookingSortingService.class);

    public BookingSortingService(Repository<Integer,Booking> repository){
        this.repository = repository;
    }


    public List<Booking> printSorted() throws NullPointerException{

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
        sortingSelection.printSelectionFields();


        Comparator<Booking> comparator = null;
        switch(sortingSelection.selectMenuOption("Select a Method")){

            case "year" -> comparator = BookingSorter.sortByVehicle(VehicleSorter.sortByYearMade());
            case "registration" -> comparator = BookingSorter.sortByVehicle(VehicleSorter.sortByRegistration());
            case "id" -> comparator = BookingSorter.sortByID();
            case "date" -> comparator = BookingSorter.sortByDate();
            case "status" -> comparator = BookingSorter.sortByFinished();

        }

        try{
            return repository.getSortedCollection(comparator);
        } catch ( NullPointerException e) {
            log.warn("Tried to sort with a nullObject", e);
            throw e;
        }

    }

}
