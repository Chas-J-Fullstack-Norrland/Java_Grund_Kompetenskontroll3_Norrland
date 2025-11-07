package org.example.predefined;

public class PriceList {

    public static int maintenancePrice(int yearModel){


        if(yearModel>2020){return 1500;}
        if(yearModel>2015){return 1800;}
        if(yearModel>2010){return 2000;}
        if(yearModel>2005){return 2300;}
        if(yearModel<2005){return 2800;}

        throw new NumberFormatException("Int yearModel did not match any listed valueRange");

    }

    public static double inspectionPrice(){
        return 550;
    }


}
