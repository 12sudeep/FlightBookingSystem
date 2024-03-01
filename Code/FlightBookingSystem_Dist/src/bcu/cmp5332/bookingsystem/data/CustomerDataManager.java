package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CustomerDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/customers.txt";
    
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        // TODO: implementation here
    	try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int lineIdx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int customerId = Integer.parseInt(properties[0]);
                    String customerName = properties[1];
                    String customerPhoneNumber = properties[2];
                    String email = properties[3];
                    int status = Integer.parseInt(properties[4]);
                    
                    Customer customer = new Customer(customerId, customerName, customerPhoneNumber, email, status);
                    fbs.addCustomer(customer);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    throw new FlightBookingSystemException("Unable to parse customer data on line " + lineIdx
                            + "\nError: " + ex.getMessage());
                }
                lineIdx++;
            }
        }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        // TODO: implementation here
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.print(customer.getStatus() + SEPARATOR);
                out.println();
            }
        }
    }
}
