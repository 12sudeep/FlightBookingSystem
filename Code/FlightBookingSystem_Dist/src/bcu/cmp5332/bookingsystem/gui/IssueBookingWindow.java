package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class IssueBookingWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField customerIdText = new JTextField();
    private JTextField flightIdText = new JTextField();

    private JButton proceedBtn = new JButton("Proceed");

    public IssueBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        setTitle("Issue Booking");

        setSize(400, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));
        topPanel.add(new JLabel("Customer ID: "));
        topPanel.add(customerIdText);
        topPanel.add(new JLabel("Flight ID: "));
        topPanel.add(flightIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
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
            showPriceAndConfirmBooking();
        }
    }

    private void showPriceAndConfirmBooking() {
        try {
            int customerId = Integer.parseInt(customerIdText.getText());
            int flightId = Integer.parseInt(flightIdText.getText());

            FlightBookingSystem fbs = mw.getFlightBookingSystem();
            double price = fbs.getFlightByID(flightId).calculatePrice(fbs.getCurrentDate());

            // Format the price to display with 2 decimal places
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedPrice = df.format(price);

            // Show price and confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(this, "Price for this booking: $" + formattedPrice + "\n\nAre you sure you want to issue this booking?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Create and execute the AddBooking Command
                Command addBooking = new AddBooking(customerId, flightId, fbs.getCurrentDate(), 1); // Assuming 1 is the default status for issued booking
                addBooking.execute(fbs);

                // Refresh the view with the list of bookings
                // mw.displayBookings(); // Uncomment this if you want to refresh the view after issuing the booking

                // Hide (close) the IssueBookingWindow
                this.setVisible(false);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Customer ID and Flight ID must be valid integers", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
