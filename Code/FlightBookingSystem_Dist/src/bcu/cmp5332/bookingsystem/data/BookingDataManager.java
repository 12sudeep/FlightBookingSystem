package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class BookingDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/bookings.txt";
    private final String SEPARATOR = "::";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                int status = Integer.parseInt(properties[3]);    
                
                try {
                    
                	int customerId = Integer.parseInt(properties[0]);
                    int flightId = Integer.parseInt(properties[1]);
                    LocalDate bookingDate = LocalDate.parse(properties[2]);

                    // Fetch customer and flight objects from FlightBookingSystem
                    Customer customer = fbs.getCustomerByID(customerId);
                    Flight flight = fbs.getFlightByID(flightId);

                    // Create booking object and add to FlightBookingSystem
                    Booking booking = new Booking(customer, flight, bookingDate, status);
                    fbs.addBooking(booking);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse booking data on line " + line_idx
                            + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Booking booking : fbs.getBookings()) {
                out.print(booking.getCustomer().getId() + SEPARATOR);
                out.print(booking.getFlight().getId() + SEPARATOR);
                out.print(booking.getBookingDate() + SEPARATOR);
                
                out.print(booking.getStatus() + SEPARATOR);
                out.println();
            }
        }
    }
}
