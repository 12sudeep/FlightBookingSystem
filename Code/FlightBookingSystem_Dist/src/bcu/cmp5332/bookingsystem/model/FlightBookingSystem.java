package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2020-11-11");
    
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final List<Booking> bookings = new ArrayList<>();

    public LocalDate getSystemDate() {
        return systemDate;
    }

    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }
    
    public List<Customer> getCustomers() {
        List<Customer> out = new ArrayList<>(customers.values());
        return Collections.unmodifiableList(out);
    }
    
    public List<Booking> getBookings() {
    	return Collections.unmodifiableList(bookings);
    }

    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }

    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        // TODO: implementation here
        if (!customers.containsKey(id)) {
        	throw new FlightBookingSystemException("There is no customer with that Id.");
        }
        return customers.get(id);
    }
    
    public Booking getBookingByCustomerId(int customerId) throws FlightBookingSystemException {
        for (Booking booking : bookings) {
            if (booking.getCustomer().getId() == customerId) {
                return booking;
            }
        }
        throw new FlightBookingSystemException("Booking not found for customer ID " + customerId);
    }
    
    public Booking getBookingById(int customerId, int flightId) throws FlightBookingSystemException {
        for (Booking booking : bookings) {
            if (booking.getCustomer().getId() == customerId && booking.getFlight().getId() == flightId) {
                return booking;
            }
        }
        throw new FlightBookingSystemException("Booking not found for customer ID " + customerId + " and flight ID " + flightId);
    }
    

    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.containsKey(customer.getId())) {
            throw new FlightBookingSystemException("Duplicate customer ID. Please enter a different ID.");
        }
        customers.put(customer.getId(), customer);
    }
    
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        // Check if the booking conflicts with existing bookings for the same flight
        Flight flight = booking.getFlight();
        Customer customer = booking.getCustomer();
        LocalDate bookingDate = booking.getBookingDate();
        for (Booking existingBooking : bookings) {
            if (existingBooking.getFlight().equals(flight) && 
                existingBooking.getBookingDate().equals(bookingDate) &&
                existingBooking.getCustomer().equals(customer)) {
                throw new FlightBookingSystemException("This flight for " + customer.getName() + " on " + bookingDate + " is already booked.");
            }
        }

        bookings.add(booking);
    }
    
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }


	}



    


