package com.luxury.reservation_service.shedular;

import com.luxury.reservation_service.model.Booking;
import com.luxury.reservation_service.repository.BookingRepository;
import com.luxury.reservation_service.repository.ReservedRoomsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservedRoomsCleanupScheduler {

    private final ReservedRoomsRepository reservedRoomsRepository;
    private final BookingRepository bookingRepository;

    public ReservedRoomsCleanupScheduler(ReservedRoomsRepository reservedRoomsRepository, BookingRepository bookingRepository) {
        this.reservedRoomsRepository = reservedRoomsRepository;
        this.bookingRepository = bookingRepository;
    }

    @Scheduled(cron = "0 0 2 * * ?") // Runs every day at 2 AM
    @Transactional
    public void cleanupReservedRooms() {
        LocalDate today = LocalDate.now();

        // Find bookings where checkout date has passed
        List<Booking> expiredBookings = bookingRepository.findByCheckoutDateBefore(today);

        for (Booking booking : expiredBookings) {
            // Delete reserved rooms linked to these bookings
            reservedRoomsRepository.deleteByBooking(booking);

            // Optionally, delete the booking itself if you want
            // bookingRepository.delete(booking);
        }
    }
}
