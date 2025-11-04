package org.example.Filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Mailfilter {

    private static final Logger log = LoggerFactory.getLogger(Mailfilter.class);

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Predicate<String> EMAIL_VALIDATOR = email -> {
        if (email == null || email.trim().isEmpty()) {
            log.warn("Email is null or empty");
            return false;
        }
        String trimmed = email.trim();
        boolean matches = EMAIL_PATTERN.matcher(trimmed).matches();
        if (!matches) {
            log.warn("Invalid email format: {}", trimmed);
        }
        return matches;
    };

    public static boolean validateEmail(String email) {
        return EMAIL_VALIDATOR.test(email);
    }
}
