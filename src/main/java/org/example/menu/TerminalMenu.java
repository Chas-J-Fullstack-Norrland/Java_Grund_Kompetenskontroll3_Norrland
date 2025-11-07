package org.example.menu;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.Set;


public class TerminalMenu implements OptionSelectionInterface, UserInputInterface {

    private Set<String> menuOptions;
    private final BufferedReader reader;
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
            log.error("Tried to run options selection while no options were defined");
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

                log.error("Unexpected error occurred when reading input");
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
                log.error("Unexpected error occurred when reading input");
                throw new RuntimeException();
            }

        }

        return input;


    }


    public int readNumberInput(String message) {
        if (!message.isBlank()) {
            System.out.println(message);
        }

        int inputNumber;
        String line;
        while (true) {
            try {
                line = reader.readLine();
                if (line.matches("[0-9]+")) {
                    inputNumber = Integer.parseInt(line);
                    break;
                }
            } catch (Exception e) {
                log.error("Unexpected error occurred when reading input");
                throw new RuntimeException();
            }

            System.out.println("That is not a valid number, looking for integer");
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
            log.error("Unexpected error occurred when reading input");
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
                log.error("Could not parse {} into dateTime format",input,e);
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
                log.error("Could not parse {} into date format",input,e);
            }
        }

    }




}

