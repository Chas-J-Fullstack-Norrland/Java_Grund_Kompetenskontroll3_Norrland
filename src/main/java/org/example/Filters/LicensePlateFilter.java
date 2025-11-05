package org.example.Filters;


import org.example.factory.LogFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import java.util.regex.Pattern;





public class LicensePlateFilter {

    private static final Logger logger = LoggerFactory.getLogger(LicensePlateFilter.class);

    private static final Pattern LicensePlate = Pattern.compile(
            "^[A-Z]{3} (?:\\d{3}|\\d{2}[A-Z])$");


    public LicensePlateFilter() {
        LogFactory.loggingContext();
    }


    public boolean validateLicensePlate(String plate){


        if(plate == null || plate.isEmpty()){
           logger.error("License plate is null or empty");
            return false;
        }
        boolean isValid = LicensePlate.matcher(plate).matches();
        if(!isValid){
            logger.error("Invalid license plate format: {}", plate);
        }
        return isValid;
    }

}
