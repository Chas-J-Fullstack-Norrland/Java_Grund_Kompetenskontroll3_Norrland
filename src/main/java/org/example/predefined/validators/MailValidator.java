package org.example.predefined.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.regex.Pattern;

public class MailValidator {

    private static final Logger log = LoggerFactory.getLogger(MailValidator.class);

    private static final Pattern emailPattern = Pattern.compile(
            "^(?![_.-])[A-Za-z0-9._%+-]+(?<![_.-])@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$"
    );


    public static boolean validateEmail(String email) {
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
