package org.example.datamodels;

public class PriceList {

    public static int maintenencePrice(int yearModel){

        int returnPrice = 0;

        if(yearModel>2020){returnPrice = 1500;}
        if(yearModel>2015){returnPrice = 1800;}
        if(yearModel>2010){returnPrice = 2000;}
        if(yearModel>2005){returnPrice = 2300;}
        if(yearModel<2005){returnPrice = 2800;}

        return returnPrice;

    }

    public static double inspectionPrice(){
        return 550;
    }


}
