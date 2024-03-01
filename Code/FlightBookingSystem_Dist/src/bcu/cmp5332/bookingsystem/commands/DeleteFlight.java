package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class DeleteFlight implements Command {

	private final int flightId;

    public DeleteFlight(int flightId) {
        this.flightId = flightId;
    }
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the booking to cancel
        Flight flight = flightBookingSystem.getFlightByID(flightId);
        
        // Check if the booking is already canceled
        if (flight.getStatus() == 0) {
            System.out.println("The flight is already removed.");
            return;
        } else {
	        // Set the status of the booking to "cancel"
        	flight.setStatus(0);
	        
	        System.out.println("Flight #" + flightId + " removed successfully.");
        }
    }
}
