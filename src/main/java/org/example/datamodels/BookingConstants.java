package org.example.datamodels;
import java.util.Set;

public final class BookingConstants {
    private BookingConstants() {}
    public static final String TYPE_INSPECTION = "INSPECTION";
    public static final String TYPE_MAINTENANCE = "MAINTENANCE";
    public static final String TYPE_REPAIR = "REPAIR";
    public static final String TYPE_OTHER = "OTHER";
    public static final String STATUS_SCHEDULED = "SCHEDULED";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_CANCELED = "CANCELED";

    public static final Set<String> ALL_TYPES = Set.of(
            TYPE_INSPECTION, TYPE_MAINTENANCE, TYPE_REPAIR, TYPE_OTHER
    );
    public static final Set<String> ALL_STATUS = Set.of(
            STATUS_SCHEDULED, STATUS_IN_PROGRESS, STATUS_COMPLETED, STATUS_CANCELED
    );
}
