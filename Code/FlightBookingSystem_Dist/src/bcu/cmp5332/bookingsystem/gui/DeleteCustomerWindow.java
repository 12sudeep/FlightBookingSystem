/**
 * The DeleteCustomerWindow class provides a graphical user interface for deleting a customer.
 * It allows the user to enter the ID of the customer they want to delete, and prompts for confirmation before deletion.
 * Upon deletion, it executes the DeleteCustomer command and refreshes the list of customers displayed in the main window.
 * If any error occurs during the deletion process, appropriate error messages are displayed to the user.
 */

package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.DeleteCustomer;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteCustomerWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField customerIdText = new JTextField();

    private JButton deleteBtn = new JButton("Delete");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs a DeleteCustomerWindow object.
     * 
     * @param mw The reference to the main window of the application.
     */
    public DeleteCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the contents of the frame.
     * Sets up the layout, components, and event listeners for the window.
     */
    private void initialize() {
        setTitle("Delete Customer");

        setSize(300, 150);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(new JLabel("Customer ID: "));
        topPanel.add(customerIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(deleteBtn);
        bottomPanel.add(cancelBtn);

        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    /**
     * Handles the actionPerformed event for the buttons in the window.
     * If the delete button is clicked, it invokes the deleteCustomer method.
     * If the cancel button is clicked, it hides the DeleteCustomerWindow.
     * 
     * @param ae The ActionEvent object representing the user's action.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteBtn) {
            deleteCustomer();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    /**
     * Deletes the customer based on the ID entered by the user.
     * Prompts for confirmation before deletion.
     * If confirmed, executes the DeleteCustomer command and refreshes the list of customers.
     * Displays error messages if any error occurs during the deletion process.
     */
    private void deleteCustomer() {
        try {
            int customerId = Integer.parseInt(customerIdText.getText());

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Create and execute the DeleteCustomer Command
                Command deleteCustomer = new DeleteCustomer(customerId);
                deleteCustomer.execute(mw.getFlightBookingSystem());

                // Refresh the view with the list of customers
                mw.displayCustomers();

                // Hide (close) the DeleteCustomerWindow
                this.setVisible(false);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Customer ID must be a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
