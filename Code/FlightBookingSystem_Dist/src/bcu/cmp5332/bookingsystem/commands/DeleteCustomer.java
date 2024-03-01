/**
 * The DeleteCustomer class implements the Command interface and represents a command to delete a customer from the flight booking system.
 * It sets the status of the customer to "removed" in the system.
 */

package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class DeleteCustomer implements Command {

	/**
     * The ID of the customer to be deleted.
     */
	private final int customerId;

	/**
     * Constructs a DeleteCustomer object with the specified customer ID.
     *
     * @param customerId the ID of the customer to be deleted
     */
    public DeleteCustomer(int customerId) {
        this.customerId = customerId;
    }
    
    /**
     * Executes the command to delete the customer from the flight booking system.
     * It sets the status of the customer to "removed" if it is not already canceled.
     *
     * @param flightBookingSystem the flight booking system from which the customer is to be deleted
     * @throws FlightBookingSystemException if an error occurs while deleting the customer
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // Get the booking to cancel
        Customer customer = flightBookingSystem.getCustomerByID(customerId);
        
        // Check if the booking is already canceled
        if (customer.getStatus() == 0) {
            System.out.println("The customer is already removed.");
            return;
        } else {
	        // Set the status of the booking to "cancel"
        	customer.setStatus(0);
	        
	        System.out.println("Customer #" + customerId + " removed successfully.");
        }
    }
}
