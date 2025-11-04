package org.example.Filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class LicensePlateFilter {

    private static final Pattern LicensePlate = Pattern.compile(
            "^[A-Z]{3} (?:\\d{3}|\\d{2}[A-Z])$"
    );
    private static final Logger log = LoggerFactory.getLogger(LicensePlateFilter.class);

    public boolean validateLicensePlate(String plate){
        if(plate == null || plate.isEmpty()){
            log.warn("License plate is null or empty");
            return false;
        }
        boolean isValid = LicensePlate.matcher(plate).matches();
        if(!isValid){
            log.warn("Invalid license plate format: {}", plate);
        }
        return isValid;
    }

}
