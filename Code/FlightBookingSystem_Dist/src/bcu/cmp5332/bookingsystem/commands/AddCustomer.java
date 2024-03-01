package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddCustomer implements Command {
	private final int id;
    private final String name;
    private final String phone;
    private final String email;
    
    public AddCustomer(int id,String name, String phone, String email) {
        this.id = id;
		this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        // TODO: implementation here
//    	int maxId = 0;
//        if (flightBookingSystem.getCustomers().size() > 0) {
//            int lastIndex = flightBookingSystem.getCustomers().size() - 1;
//            maxId = flightBookingSystem.getCustomers().get(lastIndex).getId();
//        }
//        Customer customer = new Customer(++maxId, name, phone,email);
    	
    	Customer customer = new Customer(this.id, name, phone, email, 1);
        flightBookingSystem.addCustomer(customer);
        System.out.println("Customer #" + customer.getId() + " added.");
    }
}
