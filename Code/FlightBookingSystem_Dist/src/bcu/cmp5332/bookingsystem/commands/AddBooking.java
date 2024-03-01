package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
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
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
        Flight flight = flightBookingSystem.getFlightByID(flightId);

        if (flight.getPassengers().size() >= flight.getCapacity()) {
            throw new FlightBookingSystemException("Flight " + flight.getFlightNumber() + " is at full capacity. No more bookings can be made.");
        }

        Booking booking = new Booking(customer, flight, bookingDate, status);
        flightBookingSystem.addBooking(booking);
        System.out.println("Booking for " + customer.getName() + " added to flight " + flight.getFlightNumber() + ".");
    }
}
