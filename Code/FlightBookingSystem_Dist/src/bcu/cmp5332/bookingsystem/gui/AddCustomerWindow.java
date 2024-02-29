package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddCustomerWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField customerNoText = new JTextField();
    private JTextField fullNameText = new JTextField();
    private JTextField phoneNumberText = new JTextField();
    private JTextField emailText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle exception if setting look and feel fails
        }

        setTitle("Add a New Customer");

        setSize(350, 250);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("Customer Number: "));
        topPanel.add(customerNoText);
        topPanel.add(new JLabel("Full Name: "));
        topPanel.add(fullNameText);
        topPanel.add(new JLabel("Phone Number: "));
        topPanel.add(phoneNumberText);
        topPanel.add(new JLabel("Email: "));
        topPanel.add(emailText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addCustomer();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void addCustomer() {
        try {
            int customerNumber = Integer.parseInt(customerNoText.getText());
            String fullName = fullNameText.getText();
            String phoneNumber = phoneNumberText.getText();
            String email = emailText.getText();

            // Validate email format
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create and execute the AddCustomer Command
            Command addCustomer = new AddCustomer(customerNumber, fullName, phoneNumber, email);
            addCustomer.execute(mw.getFlightBookingSystem());

            // Refresh the view with the list of customers
             mw.displayCustomers();

            // Hide (close) the AddCustomerWindow
            this.setVisible(false);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Customer Number must be a valid integer", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to validate email format using regular expression
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
