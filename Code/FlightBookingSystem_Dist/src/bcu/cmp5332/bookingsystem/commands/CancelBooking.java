package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class CancelBooking implements Command {

    private final int customerId;
    private final int flightId;

    public CancelBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the booking to cancel
        Booking booking = flightBookingSystem.getBookingById(customerId, flightId);
        
        // Check if the booking is already canceled
        if (booking.getStatus() == 0) {
            System.out.println("The booking is already canceled.");
            return;
        } else {
	        // Set the status of the booking to "cancel"
	        booking.setStatus(0);
	        
	        // Remove the booking from the system
	        // flightBookingSystem.removeBooking(booking);
	        
	        System.out.println("Booking canceled successfully.");
        }
    }
}
