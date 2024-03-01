package bcu.cmp5332.bookingsystem.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private String email;
    private int status;
    private final List<Booking> bookings = new ArrayList<>();
    
    // TODO: implement constructor here
    public Customer(int id, String name,String phone, String email, int status) {
    	this.id=id;
    	this.name=name;
    	this.phone=phone;
    	this.email = email;
    	this.setStatus(status);
    }
    
    public void populate(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
	    try {
	        BufferedReader bookingsReader = new BufferedReader(new FileReader("./resources/data/bookings.txt"));
	        String bookingLine;
	        
	        while ((bookingLine = bookingsReader.readLine()) != null) {
	            String[] bookingData = bookingLine.split("::");
	            int customerId = Integer.parseInt(bookingData[0]);
	            int flightId = Integer.parseInt(bookingData[1]);
	
	            if (customerId == this.id) {
	                Flight flight = flightBookingSystem.getFlightByID(flightId);
	                LocalDate bookingDate = LocalDate.parse(bookingData[2]);
	                int status = Integer.parseInt(bookingData[3]);
	
	                boolean bookingExists = false;
	                for (Booking existingBooking : bookings) {
	                    if (existingBooking.getFlight().getId() == flightId && existingBooking.getBookingDate().equals(bookingDate)) {
	                        bookingExists = true;
	                        break;
	                    }
	                }
	
	                if (!bookingExists) {
	                    Booking booking = new Booking(this, flight, bookingDate, status);
	                    bookings.add(booking);
	                }
	            }
	        }
	        bookingsReader.close();
	    } catch (IOException | NumberFormatException ex) {
	        ex.printStackTrace(); // Handle error appropriately
	    }
	}

    
    // TODO: implementation of Getter and Setter methods
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	 public String getEmail() {
			return email;
		}

	public void setEmail(String email) {
		this.email = email;
	}


	public List<Booking> getBookings() {
		return bookings;
	}

	public void addBooking(Booking booking) {
        // TODO: implementation here
		bookings.add(booking);
    }
	
	public void cancelBooking(Booking booking) {
        bookings.remove(booking);
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getDetailsLong() {
	    StringBuilder details = new StringBuilder();
	    details.append("Customer ID: ").append(id).append("\n");
	    details.append("Name: ").append(name).append("\n");
	    details.append("Email: ").append(email).append("\n");
	    details.append("Phone: ").append(phone).append("\n");
	    details.append("Number of Bookings: ").append(bookings.size()).append("\n");
	    details.append("Bookings:\n");
	    for (Booking booking : bookings) {
	        details.append(" - Flight: ").append(booking.getFlight().getFlightNumber())
	               .append(", Departure Date: ").append(booking.getFlight().getDepartureDate())
	               .append("\n");
	    }
	    return details.toString();
	}

}
