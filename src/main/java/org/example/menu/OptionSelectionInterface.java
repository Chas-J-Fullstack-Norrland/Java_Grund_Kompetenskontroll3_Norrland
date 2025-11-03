package org.example.menu;

import java.util.Set;

public interface OptionSelectionInterface {

    void setMenuOptions(Set<String> options);
    String selectMenuOption(String message, String error);
    Set<String> getMenuOptions();
    void viewMenuOptions();

}
