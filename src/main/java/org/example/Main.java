package org.example;

import org.example.datamodels.*;

import java.time.LocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Vehicle testCar = new Car("abc-123","volvo",2012);
        PaidService Testing = new BookedMaintenance(testCar, LocalDateTime.now(),"NorrSken@live.de");

        System.out.println(Testing.getPrice());


    }
}