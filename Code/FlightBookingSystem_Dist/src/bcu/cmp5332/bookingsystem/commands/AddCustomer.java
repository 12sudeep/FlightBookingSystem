package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddCustomer implements Command {
	private final int id;
    private final String name;
    private final String phone;
    
    public AddCustomer(int id,String name, String phone) {
        this.id = id;
		this.name = name;
        this.phone = phone;		
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // TODO: implementation here
    	int maxId = 0;
        if (flightBookingSystem.getCustomers().size() > 0) {
            int lastIndex = flightBookingSystem.getCustomers().size() - 1;
            maxId = flightBookingSystem.getCustomers().get(lastIndex).getId();
        }

        Customer customer = new Customer(++maxId, name, phone);
        flightBookingSystem.addCustomer(customer);
        System.out.println("Customer #" + customer.getId() + " added.");
    }
}
