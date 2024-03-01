package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.LoadGUI;
import bcu.cmp5332.bookingsystem.commands.ShowCustomer;
import bcu.cmp5332.bookingsystem.commands.ShowFlight;
import bcu.cmp5332.bookingsystem.commands.ListFlights;
import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.EditBooking;
import bcu.cmp5332.bookingsystem.commands.Help;
import bcu.cmp5332.bookingsystem.commands.ListCustomers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CommandParser {
    
    public static Command parse(String line) throws FlightBookingSystemException {
        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];

            if (cmd.equals("addflight")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Flight Number: ");
                String flightNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();

                LocalDate departureDate = parseDateWithAttempts(reader);
                System.out.print("Capacity: ");
                int capacity = Integer.parseInt(reader.readLine());
                System.out.print("Price: ");
                double price = Double.parseDouble(reader.readLine());
                System.out.print("Cancellation/Rebok Fee: ");
                double cancellationRebookFee = Double.parseDouble(reader.readLine());
                
                return new AddFlight(flightNumber, origin, destination, departureDate, capacity, price, cancellationRebookFee);
            
            } else if (cmd.equals("addcustomer")) {
                     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                     System.out.print("ID: ");
                     int id = Integer.parseInt(reader.readLine());
                     System.out.print("Name: ");
                     String name = reader.readLine();
                     System.out.print("Phone Number: ");
                     String phoneNumber = reader.readLine();
                     System.out.print("Email: ");
                     String email = reader.readLine();
                     return new AddCustomer(id, name, phoneNumber, email);
          
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                if (line.equals("listflights")) {
                    return new ListFlights();
                } else if (line.equals("listcustomers")) {
                    return new ListCustomers();
                } else if (line.equals("help")) {
                    return new Help();
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showflight")) {
                	return new ShowFlight(id);
                } else if (cmd.equals("showcustomer")) {
                	return new ShowCustomer(id);
                }
            } else if (parts.length == 3) {
                if (cmd.equals("addbooking")) {
                	return new AddBooking(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), LocalDate.now(), 1);
                } else if (cmd.equals("editbooking")) {
                    return new EditBooking(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                } else if (cmd.equals("cancelbooking")) {
                    return new CancelBooking(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
            }
        } catch (NumberFormatException | IOException ex) {
            throw new FlightBookingSystemException("Invalid input. Please provide correct input.");
        }

        throw new FlightBookingSystemException("Invalid command.");
    }
    
    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher than 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                LocalDate departureDate = LocalDate.parse(br.readLine());
                return departureDate;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            } catch (IOException ex) {
                throw new FlightBookingSystemException("Error reading input. Please try again.");
            }
        }
        
        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }
    
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}
