package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class ShowFlight implements Command {

    private final int flightId;

    public ShowFlight(int flightId) {
        this.flightId = flightId;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Flight flight = flightBookingSystem.getFlightByID(flightId);
        System.out.println("Flight ID: " + flight.getId());
        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.println("Origin: " + flight.getOrigin());
        System.out.println("Destination: " + flight.getDestination());
        System.out.println("Departure Date: " + flight.getDepartureDate());
        System.out.println("Number of Seats: " + flight.getCapacity());
        System.out.println("Price: " + flight.getPrice());

        System.out.println("Passengers:");
        if (flight.getPassengers().isEmpty()) {
            System.out.println("No passengers on this flight.");
        } else {
        for (Customer passenger : flight.getPassengers()) {
            System.out.println("Name: " + passenger.getName());
            System.out.println("Phone Number: " + passenger.getPhone());
        }
    }
    }}
