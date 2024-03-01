package bcu.cmp5332.bookingsystem.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

public class Flight {

    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private int capacity;
    private double price;
    private int status;
    private final List<Customer> passengers = new ArrayList<>();

    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int capacity,
            double price, int status) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
        this.setStatus(status);
    }

    public void populate(FlightBookingSystem fbs) throws FlightBookingSystemException {
        try {
            BufferedReader bookingsReader = new BufferedReader(new FileReader("./resources/data/bookings.txt"));
            String bookingLine;

            while ((bookingLine = bookingsReader.readLine()) != null) {
                String[] bookingData = bookingLine.split("::");
                int customerId = Integer.parseInt(bookingData[0]);
                int flightId = Integer.parseInt(bookingData[1]);

                if (flightId == this.id) {
                    Customer customer = fbs.getCustomerByID(customerId);
                    passengers.add(customer);
                }
            }
            bookingsReader.close();
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace(); // Handle error appropriately
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Customer> getPassengers() {
        return passengers;
    }

    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to "
                + destination + " on " + departureDate.format(dtf);
    }

    public String getDetailsLong() {
        // TODO: implementation here
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        StringBuilder details = new StringBuilder();
        details.append("Flight Number: ").append(flightNumber).append("\n");
        details.append("Origin: ").append(origin).append("\n");
        details.append("Destination: ").append(destination).append("\n");
        details.append("Departure Date: ").append(departureDate.format(dtf)).append("\n");
        details.append("Capacity: ").append(capacity).append("\n");
        details.append("Price: ").append(price).append("\n");
        details.append("Passengers:\n");
        for (Customer passenger : passengers) {
            details.append(passenger.getName()).append("\n");
        }
        return details.toString();
    }

    public void addPassenger(Customer passenger) throws FlightBookingSystemException {
        if (passengers.size() >= capacity) {
            throw new FlightBookingSystemException("Flight is at full capacity. No more bookings can be made.");
        }
        passengers.add(passenger);
    }

    public void removePassenger(Customer passenger) throws FlightBookingSystemException {
        if (!passengers.contains(passenger)) {
            throw new FlightBookingSystemException(
                    "Passenger " + passenger.getName() + " is not booked on this flight.");
        }
        passengers.remove(passenger);
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
