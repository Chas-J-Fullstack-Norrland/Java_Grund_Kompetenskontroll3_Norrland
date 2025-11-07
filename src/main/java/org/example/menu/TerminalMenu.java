package org.example.menu;

import org.example.VehicleBookingApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.function.Predicate;

public class TerminalMenu implements OptionSelectionInterface, UserInputInterface {

    private Set<String> menuOptions;
    private BufferedReader reader;
    private static final Logger log = LoggerFactory.getLogger(TerminalMenu.class);

    public TerminalMenu(Set<String> menuOptions, InputStream in){
        reader = new BufferedReader(new InputStreamReader(in));
        this.menuOptions = menuOptions;
    }

    public TerminalMenu(InputStream in){
        reader = new BufferedReader(new InputStreamReader(in));
        this.menuOptions = null;
    }

    public String selectMenuOption(String message) throws IllegalStateException {
        if(menuOptions == null|| menuOptions.isEmpty()){
            throw new IllegalStateException("Menu instance contains no viable options");
        }

        if(!message.isBlank()){
            System.out.println(message);
        }
        boolean validOption = false;
        String selectedOption = "";
        while(!validOption){
            try {
                String line = reader.readLine();
                if (menuOptions.contains(line)) {
                    selectedOption = line;
                    validOption = true;
                }else{
                    System.err.println("Not valid option");
                }
            }catch(IOException e){

                throw new RuntimeException();
            }
        }

        return selectedOption;
    }

    @Override
    public Set<String> getMenuOptions() {
        return menuOptions;
    }

    @Override
    public void viewMenuOptions() {
        menuOptions.forEach(System.out::println);
    }


    public String readTextInput(String message){
        if(!message.isBlank()){
            System.out.println(message);
        }

        String input = "";
        while(input.isBlank()){
            try{input = reader.readLine();}
            catch(IOException e){
                //log implement log to file
                throw new RuntimeException();
            }

        }

        return input;


    }


    public int readNumberInput(String message) {
        if (!message.isBlank()) {
            System.out.println(message);
        }

        int inputNumber = 0;
        String line;
        boolean validInput = false;
        while (!validInput) {
            try {
                line = reader.readLine();
                if (line.matches("[0-9]+")) {
                    inputNumber = Integer.parseInt(line);
                    validInput = true;
                }
            } catch (Exception e) {
                //log to file
                throw new RuntimeException();
            }

        }

        return inputNumber;

    }


    @Override
    public String readAny(String message){

        if (!message.isBlank()) {
            System.out.println(message);
        }

        String s = "";
        try{
            s = reader.readLine();
        } catch (IOException e) {
            //Implement log to file !
        }

        return s;
    }

    public void setMenuOptions(Set<String> options) {
        this.menuOptions = options;
    }

    public void printSelectionFields(){

        System.out.println("--------------------------");
        System.out.println("Available fields:");
        menuOptions.forEach(System.out::println);
        System.out.println("--------------------------");

    }

    public LocalDateTime parseDateTimeEntry(){


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while(true){
            String input = this.readTextInput("What date and time; Format 'yyyy-MM-dd HH:mm'");
            try {
                return LocalDateTime.parse(input,dateTimeFormatter);
            } catch (DateTimeParseException e) {
                log.error("Could not parse input into dateTime");
            }
        }


    }

    public LocalDate parseDateEntry(){


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        while(true){
            String input = this.readTextInput("What date and time; Format 'yyyy-mm-dd'");
            try {
                return LocalDate.parse(input,dateTimeFormatter);
            } catch (DateTimeParseException e) {
                log.error("Could not parse input into date");
            }
        }

    }




}

