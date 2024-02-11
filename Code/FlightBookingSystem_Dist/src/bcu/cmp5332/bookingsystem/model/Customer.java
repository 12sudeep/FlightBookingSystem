package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private final List<Booking> bookings = new ArrayList<>();
    
    // TODO: implement constructor here
    
    // TODO: implementation of Getter and Setter methods
    
    public void addBooking(Booking booking) {
        // TODO: implementation here
    }
}
