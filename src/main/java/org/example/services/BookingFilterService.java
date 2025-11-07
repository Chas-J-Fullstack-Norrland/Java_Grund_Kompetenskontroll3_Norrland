package org.example.services;

import org.example.VehicleBookingApp;
import org.example.datamodels.*;
import org.example.datamodels.filters.BookingFilters;
import org.example.datamodels.filters.VehicleFilters;
import org.example.menu.*;
import org.example.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class BookingFilterService {
    private static final Logger log = LoggerFactory.getLogger(BookingFilterService.class);
    private Repository<Integer, Booking> repository;

    public BookingFilterService(Repository<Integer, Booking> repository){
        this.repository = repository;
    }

    public List<Booking> printFiltered() throws NullPointerException{

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
        filterSelection.printSelectionFields();

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
            case "booking beforedate" ->  predicate = BookingFilters.dateOlderThan(filterSelection.parseDateTimeEntry());
            case "booking afterdate" -> predicate = BookingFilters.dateNewerThan(filterSelection.parseDateTimeEntry());
            case "booking date" -> {try{
                predicate = BookingFilters.atDate(filterSelection.parseDateEntry());
            } catch (NullPointerException e) {
                log.error("Parsed",e);
                throw e;
            }
            }
        }
        return repository.getFilteredSet(predicate).stream().toList();
    }


}
