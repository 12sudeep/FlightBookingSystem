package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateFlightWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
	private FlightBookingSystem fbs;
    private JTextField customerIdText = new JTextField();
    private JTextField flightIdText = new JTextField();

    private JButton proceedBtn = new JButton("Proceed");

    public UpdateFlightWindow(MainWindow mw, FlightBookingSystem fbs) {
        this.mw = mw;
        initialize();
        this.fbs = fbs;
    }

    private void initialize() {
        setTitle("Update Flight");

        setSize(350, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));
        topPanel.add(new JLabel("Customer ID: "));
        topPanel.add(customerIdText);
        topPanel.add(new JLabel("Flight ID: "));
        topPanel.add(flightIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(proceedBtn);

        proceedBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == proceedBtn) {
            updateBooking();
        }
    }

    private void updateBooking() {
        try {
            int customerId = Integer.parseInt(customerIdText.getText());
            int flightId = Integer.parseInt(flightIdText.getText());

            Booking booking = fbs.getBookingById(customerId, flightId);

            if (booking == null) {
                JOptionPane.showMessageDialog(this, "No booking found for the provided customer ID and flight ID. Please issue a new booking instead.", "Booking Not Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Show dialog to change customer ID and flight ID
                int newCustomerId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new Customer ID:"));
                int newFlightId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter new Flight ID:"));

                Customer newCustomer = fbs.getCustomerByID(newCustomerId);
                Flight newFlight = fbs.getFlightByID(newFlightId);
                
                // Get rebooking fee from the new flight
                double rebookingFee = newFlight.getCancellationRebookFee();
                
                // Show confirmation dialog with rebooking fee
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to rebook this flight?\nRebooking Fee: $" + rebookingFee, "Confirmation", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    // Update booking with new customer ID and flight ID
                    booking.setCustomer(newCustomer);
                    booking.setFlight(newFlight);
    
                    JOptionPane.showMessageDialog(this, "Booking updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Customer ID and Flight ID must be valid integers", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}