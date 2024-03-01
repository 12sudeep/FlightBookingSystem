package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class DeleteCustomer implements Command {

	private final int customerId;

    public DeleteCustomer(int customerId) {
        this.customerId = customerId;
    }
    
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the booking to cancel
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
        
        // Check if the booking is already canceled
        if (customer.getStatus() == 0) {
            System.out.println("The customer is already deleted.");
            return;
        } else {
	        // Set the status of the booking to "cancel"
        	customer.setStatus(0);
	        
	        System.out.println("Customer #" + customerId + " removed successfully.");
        }
    }
}
