package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.time.LocalDate;

public class EditBooking implements Command {
    private final int oldCustomerId;
    private final int newFlightId;
    
    public EditBooking(int oldCustomerId, int newFlightId) {
        this.oldCustomerId = oldCustomerId;
        this.newFlightId = newFlightId;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer oldCustomer = flightBookingSystem.getCustomerByID(oldCustomerId);
        Flight newFlight = flightBookingSystem.getFlightByID(newFlightId);

        // Get the booking associated with the old customer
        Booking booking = flightBookingSystem.getBookingByCustomerId(oldCustomerId);
        
        // Remove the old booking
        flightBookingSystem.removeBooking(booking);

        // Create a new booking with the new flight
        Booking newBooking = new Booking(oldCustomer, newFlight, booking.getBookingDate(), booking.getStatus());
        flightBookingSystem.addBooking(newBooking);
        
        System.out.println("Booking updated for " + oldCustomer.getName() + " to flight " + newFlight.getFlightNumber() + ".");
    }
}
