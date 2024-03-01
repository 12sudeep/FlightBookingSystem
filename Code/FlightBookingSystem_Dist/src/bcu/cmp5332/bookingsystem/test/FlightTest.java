package bcu.cmp5332.bookingsystem.test;

import static org.junit.Assert.*;
import org.junit.Test;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;

import java.time.LocalDate;

public class FlightTest {

    @Test
    public void testAddPassenger() throws FlightBookingSystemException {
        Customer customer1 = new Customer(1, "John Doe", "john@example.com", "123456789", 1);
        Customer customer2 = new Customer(2, "Jane Doe", "jane@example.com", "987654321", 1);
        LocalDate departureDate = LocalDate.of(2024, 2, 15);
        Flight flight = new Flight(1, "FL123", "New York", "London", departureDate, 150, 500.00, 10.0, 1);

        // Adding passengers
        flight.addPassenger(customer1);
        flight.addPassenger(customer2);

        // Checking if passengers are added or not
        assertTrue(flight.getPassengers().contains(customer1));
        assertTrue(flight.getPassengers().contains(customer2));
    }

    @Test
    public void testRemovePassenger() throws FlightBookingSystemException {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "123456789", 1);
        LocalDate departureDate = LocalDate.of(2024, 2, 15);
        Flight flight = new Flight(1, "FL123", "New York", "London", departureDate, 150, 500.00, 10.0, 1);

        // Adding passenger
        flight.addPassenger(customer);

        // Removing passenger
        flight.removePassenger(customer);

        // Check if passenger is removed
        assertFalse(flight.getPassengers().contains(customer));
    }

    @Test(expected = FlightBookingSystemException.class)
    public void testRemoveNonExistentPassenger() throws FlightBookingSystemException {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "123456789", 1);
        LocalDate departureDate = LocalDate.of(2024, 2, 15);
        Flight flight = new Flight(1, "FL123", "New York", "London", departureDate, 150, 500.00, 10.0, 1);

        flight.removePassenger(customer);
    }

    @Test
    public void testGetDetailsLong() throws FlightBookingSystemException {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "123456789", 1);
        LocalDate departureDate = LocalDate.of(2024, 2, 15);
        Flight flight = new Flight(1, "FL123", "New York", "London", departureDate, 150, 500.00, 10.0, 1);

        flight.addPassenger(customer);

        String expectedDetails = "Flight Number: FL123\n" +
                "Origin: New York\n" +
                "Destination: London\n" +
                "Departure Date: 15/02/2024\n" +
                "Capacity: 150\n" +
                "Price: 500.0\n" +
                "Passengers:\n" +
                "John Doe\n";
        assertEquals(expectedDetails, flight.getDetailsLong());
    }
}
