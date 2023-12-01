package src;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class FlightInfoFrame extends JFrame {

    private final JTable table;
    private final ArrayList<Flight> flights;

    public FlightInfoFrame(ArrayList<Flight> flights, String username) {
        setTitle("Flight Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 400);
        this.flights = flights; // Save the flights to the class variable

        String[] columnNames = { "Flight ID", "Origin", "Destination", "Departure Date/Time", "Arrival Date/Time" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Flight flight : this.flights) {
            Object[] row = new Object[5];
            row[0] = flight.getFlightID();
            row[1] = flight.getDepartureLocation();
            row[2] = flight.getArrivalLocation();
            row[3] = flight.getDepartureDate().toString() + "/" + flight.getDepartureTime().toString();
            row[4] = flight.getArrivalDate().toString() + "/" + flight.getArrivalTime().toString();
            model.addRow(row);
        }

        this.table = new JTable(model); // Use the class variable instead of a local variable
        JScrollPane scrollPane = new JScrollPane(this.table);
        add(scrollPane, BorderLayout.CENTER);

        JButton selectFlightButton = new JButton("Select Flight");
        selectFlightButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        selectFlightButton.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        selectFlightButton.setForeground(Color.WHITE);
        selectFlightButton.setFocusPainted(false);
        selectFlightButton.setBorderPainted(false);

        JPanel selectFlightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectFlightPanel.add(selectFlightButton);

        add(selectFlightPanel, BorderLayout.SOUTH);

        selectFlightButton.addActionListener(e -> {
            int selectedRow = FlightInfoFrame.this.table.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    int flightID = (Integer) FlightInfoFrame.this.table.getValueAt(selectedRow, 0);
                    Flight selectedFlight = Flight.getFlightByID(FlightInfoFrame.this.flights, flightID);
                    assert selectedFlight != null;
                    Aircraft aircraft = selectedFlight.getAircraft(); // Get the Aircraft object

                    DBMS dbms = DBMS.getDBMS(); // Singleton instance
                    double economyPrice = dbms.getEconomyPrice(aircraft.getAircraftID());
                    double businessPrice = dbms.getBusinessPrice(aircraft.getAircraftID());
                    double insurancePrice = 50; // This can be a fixed value or also retrieved from the
                                                // database

                    FlightInfoFrame.this.dispose(); // Close the current frame

                    BookingFrame bookingFrame = new BookingFrame(FlightInfoFrame.this, aircraft, economyPrice,
                            businessPrice,
                            insurancePrice, username);
                    bookingFrame.setVisible(true); // Display the booking frame

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(FlightInfoFrame.this,
                            "Error accessing database: " + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(FlightInfoFrame.this,
                        "Please select a flight to continue.",
                        "No Flight Selected",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        setVisible(true);
    }

    public Flight getSelectedFlight() {
        int selectedRow = this.table.getSelectedRow();
        if (selectedRow >= 0) {
            int flightID = (Integer) this.table.getValueAt(selectedRow, 0);
            for (Flight flight : this.flights) {
                if (flight.getFlightID() == flightID) {
                    return flight;
                }
            }
        }
        return null; // Or handle appropriately if no flight is selected
    }

    public LocalTime getDepartureTime() {
        return getSelectedFlight().getDepartureTime();
    }

    public LocalTime getArrivalTime() {
        return getSelectedFlight().getArrivalTime();
    }

    public LocalDate getDepartureDate() {
        return getSelectedFlight().getDepartureDate();
    }

    public LocalDate getArrivalDate() {
        return getSelectedFlight().getArrivalDate();
    }
}
