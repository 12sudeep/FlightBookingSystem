package bcu.cmp5332.bookingsystem.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Flight {
    
    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;

    private final Map<Integer, List<Customer>> passengersByFlight;

    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        
        passengersByFlight  = new HashMap<>();
        populatePassengers();
    }
    
    private void populatePassengers() {
        try {
            // Read bookings.txt
            BufferedReader bookingsReader = new BufferedReader(new FileReader("./resources/data/bookings.txt"));
            String bookingLine;
            while ((bookingLine = bookingsReader.readLine()) != null) {
                String[] bookingData = bookingLine.split("::");
                int customerId = Integer.parseInt(bookingData[0]);
                int flightId = Integer.parseInt(bookingData[1]);

                // Read customers.txt to get customer details
                BufferedReader customersReader = new BufferedReader(new FileReader("./resources/data/customers.txt"));
                String customerLine;
                while ((customerLine = customersReader.readLine()) != null) {
                    String[] customerData = customerLine.split("::");
                    if (Integer.parseInt(customerData[0]) == customerId) {
                        String customerName = customerData[1];
                        String phoneNumber = customerData[2];
                        Customer customer = new Customer(customerId, customerName, phoneNumber);

                        // Add customer to the passengers of the flight
                        passengersByFlight.computeIfAbsent(flightId, k -> new ArrayList<>()).add(customer);
                        break;
                    }
                }
                customersReader.close();
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

    public List<Customer> getPassengers() {
        List<Customer> passengers = passengersByFlight.getOrDefault(this.id, Collections.emptyList());
        return new ArrayList<>(passengers);
    }
	
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf);
    }

    public String getDetailsLong() {
        // TODO: implementation here
        return null;
    }
    
    public void addPassenger(Customer passenger) {
        
    }
}
