package org.example.Services;
import org.example.repository.Repository;
import org.example.datamodels.Booking;

public class BookingDeletionService {
    private final Repository<Integer, Booking> repository;
    public BookingDeletionService(Repository<Integer, Booking> repository) {
        this.repository = repository;
    }
    public Booking deleteBooking(int id) {
        Booking existing = repository.get(id);
        if (existing == null) return null;

        if (existing.isFinished()) {
            throw new IllegalStateException("Kan inte ta bort en redan slutförd bokning (ID=" + id + ").");
        }
        return repository.remove(id);
    }
}
