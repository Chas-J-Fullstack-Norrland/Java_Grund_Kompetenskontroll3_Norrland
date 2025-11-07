package org.example.predefined.validators;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import java.util.regex.Pattern;





public class RegistrationValidator {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationValidator.class);

    private static final Pattern LicensePlate = Pattern.compile(
            "^[A-Z]{3} (?:\\d{3}|\\d{2}[A-Z])$");


    public RegistrationValidator() {

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
