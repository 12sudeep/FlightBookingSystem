/**
 * The DeleteFlight class represents a command to remove a flight from the flight booking system.
 * It implements the Command interface.
 * 
 * <p>
 * This command takes an integer flight ID as input and deletes the flight with the corresponding ID
 * from the flight booking system. If the flight is already canceled, it displays a message indicating
 * that the flight is already removed. Otherwise, it sets the status of the flight to "cancel" and 
 * displays a success message.
 * </p>
 * 
 * <p>
 * Note: The status of a flight is represented by an integer value. A status of 1 indicates that the flight
 * is active, while a status of 0 indicates that the flight is removed.
 * </p>
 */

package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class DeleteFlight implements Command {

	/**
     * The ID of the flight to be removed.
     */
	private final int flightId;

	/**
     * Constructs a new DeleteFlight object with the specified flight ID.
     *
     * @param flightId the ID of the flight to be deleted
     */
    public DeleteFlight(int flightId) {
        this.flightId = flightId;
    }
    
    /**
     * Executes the command to delete the flight from the flight booking system.
     * 
     * <p>
     * This method retrieves the flight with the specified ID from the flight booking system
     * and checks if it is already canceled. If the flight is already canceled, it prints
     * a message indicating that the flight is already removed. Otherwise, it sets the status
     * of the flight to "cancel" and prints a success message indicating that the flight has
     * been removed.
     * </p>
     *
     * @param flightBookingSystem the flight booking system from which the flight will be deleted
     * @throws FlightBookingSystemException if an error occurs while deleting the flight
     */
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
