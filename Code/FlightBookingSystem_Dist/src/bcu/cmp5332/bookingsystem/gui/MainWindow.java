package bcu.cmp5332.bookingsystem.gui;

import java.time.LocalDate;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

    private JMenuItem adminExit;

    private JMenuItem flightsViewAll;
    private JMenuItem flightsViewFuture;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;

    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;
    private JMenuItem custList;

    private FlightBookingSystem fbs;

    public MainWindow(FlightBookingSystem fbs) {

        initialize();
        this.fbs = fbs;
    }
    
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Flight Booking Management System");
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        
//        adding image to homepage
        ImageIcon imageIcon = new ImageIcon("./resources/images/Homepage.png"); 
        if (imageIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.err.println("Error loading image");
        } else {
            JLabel imageLabel = new JLabel(imageIcon);
            this.getContentPane().add(imageLabel);
        }
        
        
        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding Flights menu and menu items
        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);

        flightsViewAll = new JMenuItem("View all flights");
        flightsViewFuture = new JMenuItem("View future flights");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsMenu.add(flightsViewAll);
        flightsMenu.add(flightsViewFuture);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);
        // adding action listener for Flights menu items
        for (int i = 0; i < flightsMenu.getItemCount(); i++) {
            flightsMenu.getItem(i).addActionListener(this);
        }
        
        // adding Bookings menu and menu items
        bookingsMenu = new JMenu("Bookings");
        menuBar.add(bookingsMenu);
        
        bookingsIssue = new JMenuItem("Issue");
        bookingsUpdate = new JMenuItem("Update");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsMenu.add(bookingsIssue);
        bookingsMenu.add(bookingsUpdate);
        bookingsMenu.add(bookingsCancel);
        // adding action listener for Bookings menu items
        for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
            bookingsMenu.getItem(i).addActionListener(this);
        }

        // adding Customers menu and menu items
        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);

        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        custDel = new JMenuItem("Delete");
        custList = new JMenuItem("List");
        
        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);
        customersMenu.add(custList); 
        
        // adding action listener for Customers menu items
        custView.addActionListener(this);
        custAdd.addActionListener(this);
        custDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = FlightBookingSystemData.load();
        new MainWindow(fbs);			
    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            try {
                FlightBookingSystemData.store(fbs);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (ae.getSource() == flightsViewAll) {
            displayAllFlights();
            
        } else if (ae.getSource() == flightsViewFuture) {
            displayFutureFlights();
            
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
            
        } else if (ae.getSource() == flightsDel) {
        	new DeleteFlightWindow(this);
            
        } else if (ae.getSource() == bookingsIssue) {
        	new IssueBookingWindow(this);
            
        } else if (ae.getSource() == bookingsUpdate) {
        	new UpdateFlightWindow(this, fbs);
            
        } else if (ae.getSource() == bookingsCancel) {
        	new CancelBookingWindow(this, fbs);
            
        } else if (ae.getSource() == custView) {
        	displayCustomers();
            
        } else if (ae.getSource() == custAdd) {
        	new AddCustomerWindow(this);
            
        } else if (ae.getSource() == custDel) {
        	new DeleteCustomerWindow(this);
            
        }
    }
    
  
    public void displayAllFlights() {
        List<Flight> flightsList = fbs.getFlights();
        
        // Filter flights whose status is 1
        flightsList = flightsList.stream()
                                 .filter(flight -> flight.getStatus() == 1)
                                 .collect(Collectors.toList());

        // headers for the table
        String[] columns = new String[]{"ID", "Flight No", "Origin", "Destination", "Departure Date", "Maximum Capacity", "Price", "Cancellation/Rebook fee"};

        Object[][] data = new Object[flightsList.size()][8];
        
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            data[i][0] = flight.getId();
            data[i][1] = flight.getFlightNumber();
            data[i][2] = flight.getOrigin();
            data[i][3] = flight.getDestination();
            data[i][4] = flight.getDepartureDate();
            data[i][5] = flight.getCapacity();
            data[i][6] = flight.getPrice();
            data[i][7] = flight.getCancellationRebookFee();
        }

        JTable table = new JTable(data, columns);
        table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int flightId = (int) table.getValueAt(selectedRow, 0);
                        try {
                            displayPassengersForFlight(flightId);
                        } catch (FlightBookingSystemException ex) {
                            JOptionPane.showMessageDialog(MainWindow.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            
        });

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
    
    public void displayFutureFlights() {
        List<Flight> flightsList = fbs.getFlights();

        // Get the current system date
        LocalDate systemDate = LocalDate.now();

        // Filter flights whose status is 1 and departure date is in the future
        flightsList = flightsList.stream()
                                 .filter(flight -> flight.getStatus() == 1 && flight.getDepartureDate().isAfter(systemDate))
                                 .collect(Collectors.toList());

        // headers for the table
        String[] columns = new String[]{"ID", "Flight No", "Origin", "Destination", "Departure Date", "Maximum Capacity", "Price", "Cancellation/Rebook fee"};

        Object[][] data = new Object[flightsList.size()][8];
        
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            data[i][0] = flight.getId();
            data[i][1] = flight.getFlightNumber();
            data[i][2] = flight.getOrigin();
            data[i][3] = flight.getDestination();
            data[i][4] = flight.getDepartureDate();
            data[i][5] = flight.getCapacity();
            data[i][6] = flight.getPrice();
            data[i][7] = flight.getCancellationRebookFee();
        }

        JTable table = new JTable(data, columns);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int flightId = (int) table.getValueAt(selectedRow, 0);
                    try {
                        displayPassengersForFlight(flightId);
                    } catch (FlightBookingSystemException ex) {
                        JOptionPane.showMessageDialog(MainWindow.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
	
    
    public void displayPassengersForFlight(int flightId) throws FlightBookingSystemException {
        Flight flight = fbs.getFlightByID(flightId);
        List<Customer> passengers = flight.getPassengers();
        StringBuilder passengerDetails = new StringBuilder();
        passengerDetails.append("Passengers for Flight ID: ").append(flightId).append("\n\n");
        for (Customer passenger : passengers) {
        	passengerDetails.append("\n");
        	passengerDetails.append("ID: ").append(passenger.getId()).append(", ");
            passengerDetails.append("Name: ").append(passenger.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(this, passengerDetails.toString(), "Passengers for Flight", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void displayCustomers() {
        List<Customer> customersList = fbs.getCustomers();
        // Filter customers whose status is 1
        customersList = customersList.stream()
                                    .filter(customer -> customer.getStatus() == 1)
                                    .collect(Collectors.toList());

        // headers for the table
        String[] columns = new String[]{"ID", "Name", "Email", "Phone", "Bookings"};

        Object[][] data = new Object[customersList.size()][5];
        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getEmail();
            data[i][3] = customer.getPhone();
            data[i][4] = customer.getBookings().size(); 
        }

        JTable table = new JTable(data, columns);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int customerId = (int) table.getValueAt(selectedRow, 0);
                    try {
                        displayBookingDetails(customerId);
                    } catch (FlightBookingSystemException e1) {
                        // Handle FlightBookingSystemException
                        e1.printStackTrace();
                    }
                }
            }
        });
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    
    public void displayBookingDetails(int customerId) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        StringBuilder bookingDetails = new StringBuilder();
        bookingDetails.append("Customer ID: ").append(customer.getId()).append("\n\n");
        bookingDetails.append("Bookings: \n");
        if (customer.getBookings().isEmpty()) {
        	bookingDetails.append("\n");
        	bookingDetails.append("No bookings made.");
        } else {
        int bookingNumber = 1;
        for (Booking booking : customer.getBookings()) {
        	bookingDetails.append("\n");
        	bookingDetails.append(bookingNumber).append(": ").append("\n");
            bookingDetails.append("Flight: ").append(booking.getFlight().getFlightNumber()).append("\n");
            bookingDetails.append("Departure Date: ").append(booking.getFlight().getDepartureDate()).append("\n");
            // Include other booking details as needed
            bookingDetails.append("\n");
            bookingNumber++;
        }}
        JOptionPane.showMessageDialog(this, bookingDetails.toString(), "Booking Details", JOptionPane.INFORMATION_MESSAGE);
    }
}
