package bcu.cmp5332.bookingsystem.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import bcu.cmp5332.bookingsystem.model.Customer;

public class CustomerTest {

    @Test
    public void testCustomerId() {
        Customer customer = new Customer(1, "John Doe", "1234567890", "john@example.com", 1);
        assertEquals(1, customer.getId());
    }

    @Test
    public void testCustomerName() {
        Customer customer = new Customer(1, "John Doe", "1234567890", "john@example.com", 1);
        assertEquals("John Doe", customer.getName());
    }

    @Test
    public void testCustomerPhone() {
        Customer customer = new Customer(1, "John Doe", "1234567890", "john@example.com", 1);
        assertEquals("1234567890", customer.getPhone());
    }

    @Test
    public void testCustomerEmail() {
        Customer customer = new Customer(1, "John Doe", "1234567890", "john@example.com", 1);
        assertEquals("john@example.com", customer.getEmail());
    }
}
