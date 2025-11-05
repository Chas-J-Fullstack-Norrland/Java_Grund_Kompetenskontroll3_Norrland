package org.example.datamodels;

public class BookingEditor {
    if (req.hasStatus()) {
        String s = req.getStatus();
        if (!BookingConstants.ALL_STATUS.contains(s)) {
            throw new IllegalArgumentException("Ogiltig status: " + s);
        }
        target.setStatus(s);
    }
}
