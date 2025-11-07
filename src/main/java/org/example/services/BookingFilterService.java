package org.example.services;


import org.example.datamodels.*;
import org.example.datamodels.predicates.BookingFilters;
import org.example.datamodels.predicates.VehicleFilters;
import org.example.menu.*;
import org.example.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class BookingFilterService {
    private static final Logger log = LoggerFactory.getLogger(BookingFilterService.class);
    private final Repository<Integer, Booking> repository;

    public BookingFilterService(Repository<Integer, Booking> repository){
        this.repository = repository;
    }

    public List<Booking> printFiltered() throws NullExpressionException {

        Set<String> filterOptions = new TreeSet<>(
                Set.of(
                        "vehicle older then",
                        "vehicle newer then",
                        "model year",
                        "model name",
                        "registration",
                        "customer email",
                        "booking date",
                        "booking before date",
                        "booking after",
                        "booking status"
                )
        );

        TerminalMenu filterSelection = new TerminalMenu(filterOptions,System.in);
        filterSelection.printSelectionFields();

        Predicate<Booking> predicate = null;
        switch (filterSelection.selectMenuOption("Select a filtering method")){

            case "vehicle older then" -> predicate = BookingFilters.forVehicle(VehicleFilters.yearModelOlderThan(filterSelection.readNumberInput("What year")));
            case "vehicle newer then" -> predicate = BookingFilters.forVehicle(VehicleFilters.yearModelNewerThan(filterSelection.readNumberInput("What year")));

            case "model year" -> predicate = BookingFilters.forVehicle(VehicleFilters.yearModelEquals(filterSelection.readNumberInput("What year")));
            case "model name" -> predicate = BookingFilters.forVehicle(VehicleFilters.modelNameContains("Which string do we match?"));

            case "registration" -> predicate = BookingFilters.forVehicle(VehicleFilters.registrationEquals("Which reg?"));
            case "customer email" -> predicate = BookingFilters.customerEmailEquals("Enter the customers Email");

            case "booking type"-> predicate = BookingFilters.bookingType(filterSelection.readTextInput("What kind of appointment? ex Inspection,Repair,Maintenance"));
            case "booking id" -> predicate = BookingFilters.atDate(LocalDate.parse(filterSelection.readTextInput("What date 'yyyy-mm-dd'")));
            case "booking before date" ->  predicate = BookingFilters.dateOlderThan(filterSelection.parseDateTimeEntry());
            case "booking after" -> predicate = BookingFilters.dateNewerThan(filterSelection.parseDateTimeEntry());
            case "booking date" -> {try{
                predicate = BookingFilters.atDate(filterSelection.parseDateEntry());
            } catch (NullPointerException e) {
                log.error("Parsed",e);
                throw new NullExpressionException("Cannot filter with a null predicate");
            }
            }
        }
        return repository.getFilteredSet(predicate).stream().toList();
    }


}
