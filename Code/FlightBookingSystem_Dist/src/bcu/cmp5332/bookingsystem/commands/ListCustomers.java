package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

public class ListCustomers implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        List<Customer> customers = flightBookingSystem.getCustomers();
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getId() + ", Name: " + customer.getName() + ", Phone: " + customer.getPhone());
        }
        System.out.println(customers.size() + " customer(s)");
    }
}
