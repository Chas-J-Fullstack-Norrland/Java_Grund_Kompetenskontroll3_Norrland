package org.example;


import org.example.datamodels.Booking;
import org.example.menu.OptionSelectionInterface;
import org.example.menu.UserInputInterface;
import org.example.repository.Repository;


public class VehicleBookingApp {

    Repository<Integer, Booking> bookingRepository;
    OptionSelectionInterface menu;
    UserInputInterface userInput;

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
                case "SetAsComplete" -> bookingRepository.get(userInput.readNumberInput("Which BookingID was finished?")).setFinished(true); //Example!! Replace with proper implementation

                case "quit" -> running = false;
            }
        }

    }
}
