package org.example.menu;

public interface UserInputInterface {
    String readTextInput(String message,String err);
    int readNumberInput(String message,String err);
    String readAny(String message);
}
