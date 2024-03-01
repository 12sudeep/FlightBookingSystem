package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelBookingWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private MainWindow mw;
    private FlightBookingSystem fbs;
    private JTextField customerIdText = new JTextField();
    private JTextField flightIdText = new JTextField();
    private JButton proceedBtn = new JButton("Proceed");

    public CancelBookingWindow(MainWindow mw, FlightBookingSystem fbs) {
        this.mw = mw;
        this.fbs = fbs;
        initialize();
    }

    private void initialize() {
        setTitle("Cancel Booking");
        setSize(350, 200);
        
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

        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == proceedBtn) {
            confirmCancellation();
        }
    }

    private void confirmCancellation() {
        try {
            int customerId = Integer.parseInt(customerIdText.getText());
            int flightId = Integer.parseInt(flightIdText.getText());

            // Retrieve the flight object to get cancellation fee
            Flight flight = fbs.getFlightByID(flightId);
            double cancellationFee = flight.getCancellationRebookFee();

            // Show confirmation dialog with cancellation fee
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?\nCancellation Fee: $" + cancellationFee, "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Create and execute the CancelBooking Command
                Command cancelBooking = new CancelBooking(customerId, flightId);
                cancelBooking.execute(fbs);

                // Hide (close) the CancelBookingWindow
                this.setVisible(false);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Customer ID and Flight ID must be valid integers", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
