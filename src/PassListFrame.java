package src;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PassListFrame extends JFrame {

    public PassListFrame(String username) {
        JFrame browseFrame = new JFrame("Enter Flights");
        browseFrame.setSize(400, 300);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel flightIdLabel = new JLabel("Flight ID: ");
        JTextField flightIdText = new JTextField();

        JButton getButton = new JButton("Get Passenger List");

        browseFrame.add(flightIdLabel);
        browseFrame.add(flightIdText);

        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int flightID = Integer.parseInt(flightIdText.getText());
                    DBMS dbms = DBMS.getDBMS();
                    ArrayList<Order> orders = dbms.getOrders(flightID);
                    passListFrame(orders);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error fetching flight data: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        browseFrame.add(new JLabel());
        browseFrame.add(getButton);
        browseFrame.setVisible(true);
    }

    private void passListFrame(ArrayList<Order> orders) {
        setTitle("Flight Passenger Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 400);

        String[] columnNames = { "Username", "Email", "SeatNumber" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Order o : orders) {

            Object[] row = new Object[3]; // Adjusted to match the number of columns
            row[0] = o.getUsername();
            row[1] = o.getEmail();
            row[2] = o.getSeatNumber();

            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to the frame
        add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }
}
