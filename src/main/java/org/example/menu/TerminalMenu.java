package org.example.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

public class TerminalMenu implements OptionSelectionInterface, UserInputInterface {

    private Set<String> menuOptions;
    private BufferedReader reader;

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
            //Log to file and menu,  No options included
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

                //log to file
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

    //Lets the user input text not defined in menuoptions.
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

    //Same as previous,  But for numbers specifically.
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



}

