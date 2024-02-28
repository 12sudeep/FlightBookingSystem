package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.time.LocalDate;

public class AddBooking implements Command {
	private final int customerId;
    private final int flightId;
    private final LocalDate bookingDate;
    private final int status;
    
    public AddBooking(int customerId, int flightId, LocalDate bookingDate, int status) {
        this.customerId = customerId;
		this.flightId = flightId;
        this.bookingDate = bookingDate;
        this.status=status;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {    	
    	Booking booking = new Booking(
    			flightBookingSystem.getCustomerByID(customerId), 
    			flightBookingSystem.getFlightByID(flightId), 
    			bookingDate, status
    		);
        flightBookingSystem.addBooking(booking);
        System.out.println("Booking for " + flightBookingSystem.getCustomerByID(customerId).getName() + " added.");
    }
}
