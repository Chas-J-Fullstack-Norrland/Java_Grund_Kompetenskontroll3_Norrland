package org.example.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.regex.Pattern;

public class Mailfilter {

    private static final Logger log = LoggerFactory.getLogger(Mailfilter.class);

    private static final Pattern emailPattern = Pattern.compile(
            "^(?![_.-])[A-Za-z0-9._%+-]+(?<![_.-])@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$"
    );


    public  boolean validateEmail(String email) {
       if(email == null){
           log.warn("Email is null");
           return false;
       }
        boolean isValid = emailPattern.matcher(email).matches();
        if (!isValid) {
            log.warn("Invalid email format: {}", email);
        }
        return isValid;
    }
}
