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

    public PassListFrame() {
        setTitle("Enter Flights");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel flightIdLabel = new JLabel("Flight ID: ");
        JTextField flightIdText = new JTextField();

        JButton getButton = new JButton("Get Passenger List");

        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int flightID = Integer.parseInt(flightIdText.getText());
                    DBMS dbms = DBMS.getDBMS();
                    ArrayList<Order> orders = dbms.getOrders(flightID);
                    DisplayFrame displayFrame = new DisplayFrame(orders);
                    displayFrame.setVisible(true);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error fetching flight data: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(flightIdLabel);
        add(flightIdText);
        add(getButton);

        // Ensure visibility is set at the end of the constructor
        setVisible(true);
       
    }

    public class DisplayFrame extends JFrame {
        public DisplayFrame(ArrayList<Order> orders) {
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
        // setVisible(true);
            }}

    
}
