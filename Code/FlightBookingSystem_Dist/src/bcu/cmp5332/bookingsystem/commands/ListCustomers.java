package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

public class ListCustomers implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem)throws FlightBookingSystemException {
        List<Customer> customers = flightBookingSystem.getCustomers();
        for (Customer customer : customers) {
            System.out.println(customer.getDetailsLong());
        }
        System.out.println(customers.size() + " customer(s)");
    }
}
