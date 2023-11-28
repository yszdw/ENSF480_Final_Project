package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class LoginFrame extends JFrame {

    private static final Font MAIN_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font GROUP_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Color BUTTON_COLOR = new Color(100, 149, 237); // Cornflower Blue
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light Gray
    private static final Color INPUT_COLOR = new Color(255, 255, 255); // White

    private void createUserLoginPanel() {
        // New frame for login to match the style of RegisterFrame
        JFrame loginFrame = new JFrame("User Login");
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setBackground(BACKGROUND_COLOR);

        // Set the login window to maximize
        loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(MAIN_FONT);
        titlePanel.add(titleLabel);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(INPUT_FONT);
        JTextField userTextField = new JTextField(20);
        userTextField.setFont(INPUT_FONT);
        userTextField.setBackground(INPUT_COLOR);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(INPUT_FONT);
        JPasswordField passField = new JPasswordField(20);
        passField.setFont(INPUT_FONT);
        passField.setBackground(INPUT_COLOR);

        // Adding components to inputPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(userTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(passField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Back button to close the login window and show the welcome window
        JButton backButton = new JButton("Back");
        backButton.setFont(INPUT_FONT);
        backButton.setBackground(BUTTON_COLOR); // Cornflower Blue
        backButton.setForeground(BUTTON_TEXT_COLOR);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        buttonPanel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(INPUT_FONT);
        loginButton.setBackground(BUTTON_COLOR); // Cornflower Blue
        loginButton.setForeground(BUTTON_TEXT_COLOR);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        buttonPanel.add(loginButton);

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your login logic here
                String username = userTextField.getText();
                String password = new String(passField.getPassword());
                if (authenticate(username, password)) {
                    // Login successful
                    SwingUtilities.invokeLater(() -> {
                        loginFrame.dispose(); // Close the login window
                        WelcomeFrame welcomeFrame = new WelcomeFrame(username); // Pass the username to WelcomeFrame
                        welcomeFrame.setVisible(true); // Show the welcome window
                    });
                } else {
                    // Login failed
                    JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add panels to frame
        loginFrame.add(titlePanel, BorderLayout.NORTH);
        loginFrame.add(inputPanel, BorderLayout.CENTER);
        loginFrame.add(buttonPanel, BorderLayout.SOUTH);
        loginFrame.setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        try {

            DBMS dbms = DBMS.getDBMS();
            return dbms.loginCheck(username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching flight data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    private void createAdminLoginPanel() {
        // New frame for login to match the style of RegisterFrame
        JFrame loginFrame = new JFrame("Admin Login");
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setBackground(BACKGROUND_COLOR);

        // Set the login window to maximize
        loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setFont(MAIN_FONT);
        titlePanel.add(titleLabel);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(INPUT_FONT);
        JTextField userTextField = new JTextField(20);
        userTextField.setFont(INPUT_FONT);
        userTextField.setBackground(INPUT_COLOR);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(INPUT_FONT);
        JPasswordField passField = new JPasswordField(20);
        passField.setFont(INPUT_FONT);
        passField.setBackground(INPUT_COLOR);

        // Adding components to inputPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(userTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(passField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Back button to close the login window and show the welcome window
        JButton backButton = new JButton("Back");
        backButton.setFont(INPUT_FONT);
        backButton.setBackground(BUTTON_COLOR); // Cornflower Blue
        backButton.setForeground(BUTTON_TEXT_COLOR);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        buttonPanel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(INPUT_FONT);
        loginButton.setBackground(BUTTON_COLOR); // Cornflower Blue
        loginButton.setForeground(BUTTON_TEXT_COLOR);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        buttonPanel.add(loginButton);

        // Action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your login logic here
                String username = userTextField.getText();
                String password = new String(passField.getPassword());

                if (authenticate(username, password) && username.equals("admin")) {
                    // Login successful
                    SwingUtilities.invokeLater(() -> {
                        loginFrame.dispose(); // Close the login window
                        AdminFrame adminFrame = new AdminFrame(); // Create the Admin window
                        adminFrame.setVisible(true); // Show the Admin window
                    });
                } else {
                    // Login failed
                    JOptionPane.showMessageDialog(null, "You do not have admin privileges.", "Access Denied",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add panels to frame
        loginFrame.add(titlePanel, BorderLayout.NORTH);
        loginFrame.add(inputPanel, BorderLayout.CENTER);
        loginFrame.add(buttonPanel, BorderLayout.SOUTH);
        loginFrame.setVisible(true);
    }

    public class AddCreditCardFrame extends JFrame {
        private String username;
        private JTextField cardNumberField;
        private JTextField expirationDateField;
        private JTextField cvvField;

        public AddCreditCardFrame(String username) {
            this.username = username;
            setTitle("Add Credit Card");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            // Title Panel
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel titleLabel = new JLabel("Add Credit Card");
            titleLabel.setFont(MAIN_FONT);
            titlePanel.add(titleLabel);

            // Input Panel
            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBackground(BACKGROUND_COLOR);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            // Card Number
            JLabel cardNumberLabel = new JLabel("Card Number:");
            cardNumberLabel.setFont(INPUT_FONT);
            cardNumberField = new JTextField(20);
            cardNumberField.setFont(INPUT_FONT);
            cardNumberField.setBackground(INPUT_COLOR);

            // Expiration Date
            JLabel expirationDateLabel = new JLabel("Expiration Date:");
            expirationDateLabel.setFont(INPUT_FONT);
            expirationDateField = new JTextField(20);
            expirationDateField.setFont(INPUT_FONT);
            expirationDateField.setBackground(INPUT_COLOR);

            // CVV
            JLabel cvvLabel = new JLabel("CVV:");
            cvvLabel.setFont(INPUT_FONT);
            cvvField = new JTextField(20);
            cvvField.setFont(INPUT_FONT);
            cvvField.setBackground(INPUT_COLOR);

        }
    }

    public class WelcomeFrame extends JFrame {
        private String username;

        public WelcomeFrame(String username) {
            this.username = username;
            setTitle("Welcome");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setExtendedState(JFrame.MAXIMIZED_BOTH);

            JLabel welcomeLabel = new JLabel("Welcome " + this.username + ", to our Flight Reservation System",
                    SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            add(welcomeLabel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setBackground(BACKGROUND_COLOR);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.NONE;

            JButton buyTicketsButton = createStyledButton("Buy Tickets");
            gbc.insets = new Insets(10, 0, 10, 0);
            buttonPanel.add(buyTicketsButton, gbc);

            buyTicketsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createTicketPurchasePanel(username); // Pass the username to createTicketPurchasePanel
                }
            });

            JButton cancelFlightButton = createStyledButton("Cancel Flight");
            gbc.insets = new Insets(0, 0, 10, 0);
            buttonPanel.add(cancelFlightButton, gbc);

            JButton registerButton = null;

            if (username.equals("guest")) {
                // have to add register button
                registerButton = createStyledButton("Register");
                gbc.insets = new Insets(0, 0, 10, 0);
                buttonPanel.add(registerButton, gbc);

                // Back button to close the login window and show the welcome window

                JButton backButton = createStyledButton("Login");
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        WelcomeFrame.this.dispose();
                        LoginFrame loginFrame = new LoginFrame();
                        loginFrame.setVisible(true);
                    }
                });
            } else {
                // // User is logged in so add logout button
                JButton logoutButton = createStyledButton("Logout");
                gbc.insets = new Insets(0, 0, 10, 0);
                buttonPanel.add(logoutButton, gbc);

                logoutButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Hide the welcome frame
                        WelcomeFrame.this.dispose(); // Optionally, you can dispose the WelcomeFrame

                        // Create and show the login frame
                        LoginFrame loginFrame = new LoginFrame();
                        loginFrame.setVisible(true);
                    }
                });

                // also add "Add Credit Card" button
                JButton addCreditCardButton = createStyledButton("Add Credit Card");
                gbc.insets = new Insets(0, 0, 10, 0);
                buttonPanel.add(addCreditCardButton, gbc);

                addCreditCardButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        // Create and show the login frame
                        AddCreditCardFrame addCreditCardFrame = new AddCreditCardFrame(username);
                        addCreditCardFrame.setVisible(true);
                    }
                });
            }
            cancelFlightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        DBMS dbms = DBMS.getDBMS();
                        if (username.equals("guest")) {
                            cancelFlightPanel();
                        } else {
                            cancelFlightPanel(dbms.getEmail(username)); // Pass the username to cancelFlightPanel
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            if (registerButton != null) {
                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Hide the welcome frame
                        WelcomeFrame.this.setVisible(false);
                        WelcomeFrame.this.dispose(); // Optionally, you can dispose the WelcomeFrame

                        // Create and show the register frame
                        RegisterFrame registerFrame = new RegisterFrame();
                        registerFrame.setVisible(true);
                    }
                });
            }

            add(buttonPanel, BorderLayout.CENTER);

            pack();
            setVisible(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        private JButton createStyledButton(String text) {
            JButton button = new JButton(text);
            button.setFont(MAIN_FONT);
            button.setBackground(BUTTON_COLOR);
            button.setForeground(BUTTON_TEXT_COLOR);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            return button;
        }
    }

    /*
     * Uses User's email in db to get all orders, then returns a list of all orders,
     * with a button to cancel each one
     */
    private void cancelFlightPanel(String email) {
        System.out.println("cancelFlightPanel(email)");
        JFrame browseFrame = new JFrame("Cancel Flight");
        browseFrame.setSize(1200, 300);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Cancel Flight");
        titleLabel.setFont(MAIN_FONT);
        titlePanel.add(titleLabel);

        try {
            DBMS dbms = DBMS.getDBMS();
            ArrayList<Order> orders = dbms.getOrders(email);
            if (orders.size() == 0) {
                JOptionPane.showMessageDialog(browseFrame, "No orders found for email: " + email,
                        "No Orders Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Display Orders in a table
                String[] columnNames = { "Order ID", "Flight ID", "Departure Location", "Arrival Location",
                        "Departure Time", "Arrival Time", "Seat Class", "Seat Number", "Insurance",
                        "Total Price" };
                // this table has 11 rows instead of 10 and OrderID is duplicated on both ends,
                // how to fix?
                // A: remove the first column from the table
                // What command do I use to remove the last column?
                // A: table.removeColumn(table.getColumnModel().getColumn(10));
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                for (Order order : orders) {
                    Object[] row = new Object[10];
                    row[0] = order.getOrderID();
                    row[1] = order.getFlightID();
                    row[2] = order.getDepartureLocation();
                    row[3] = order.getArrivalLocation();
                    row[4] = order.getDepartureTime();
                    row[5] = order.getArrivalTime();
                    row[6] = order.getSeatClass();
                    row[7] = order.getSeatNumber();
                    row[8] = order.getInsurance();
                    row[9] = order.getTotalPrice();
                    model.addRow(row);
                }
                JTable table = new JTable(model);
                table.addColumn(new TableColumn());
                JScrollPane scrollPane = new JScrollPane(table);
                browseFrame.add(scrollPane, BorderLayout.CENTER);
                table.removeColumn(table.getColumnModel().getColumn(10));
                browseFrame.setVisible(true);

                // Add a button to cancel selected order
                JButton cancelButton = new JButton("Cancel Order");

                // add button to Frame
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(cancelButton);
                browseFrame.add(buttonPanel, BorderLayout.SOUTH);

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int selectedRow = table.getSelectedRow();
                            if (selectedRow >= 0) {
                                int orderID = (Integer) table.getValueAt(selectedRow, 0);
                                // SEND EMAIL HERE

                                String email = dbms.getEmail(orderID);
                                Email_Controller.sendCancellationEmail(orderID, email);

                                dbms.cancelOrder(orderID);
                                JOptionPane.showMessageDialog(browseFrame, "Order cancelled successfully.",
                                        "Order Cancelled", JOptionPane.INFORMATION_MESSAGE);
                                model.removeRow(selectedRow); // Remove the canceled order from the table
                            } else {
                                JOptionPane.showMessageDialog(browseFrame, "Please select an order to cancel.",
                                        "No Order Selected", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(browseFrame, "Error cancelling order: " + ex.getMessage(),
                                    "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(browseFrame, "Error fetching orders: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Asks Guest for email, then goes through orders table and returns a list of
     * all orders, with a button to cancel
     * each one. Overloaded to add guest cancellation functionality
     */
    private void cancelFlightPanel() {
        System.out.println("cancelFlightPanel()");
        JFrame browseFrame = new JFrame("Cancel Flight");
        browseFrame.setSize(1200, 300);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Cancel Flight");
        titleLabel.setFont(MAIN_FONT);
        titlePanel.add(titleLabel);

        JTextField emailField = new JTextField(20);
        emailField.setFont(INPUT_FONT);
        emailField.setBackground(INPUT_COLOR);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(INPUT_FONT);
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        browseFrame.add(emailPanel, BorderLayout.NORTH);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(INPUT_FONT);
        confirmButton.setBackground(BUTTON_COLOR); // Cornflower Blue
        confirmButton.setForeground(BUTTON_TEXT_COLOR);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmPanel.add(confirmButton);
        browseFrame.add(confirmPanel, BorderLayout.SOUTH);

        // show email input panel
        browseFrame.setVisible(true);

        // add action listener to confirm button

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                if (email.equals("")) {
                    JOptionPane.showMessageDialog(browseFrame, "Please enter an email.",
                            "No Email Entered", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Hide the browse frame
                    browseFrame.dispose();
                    cancelFlightPanel(email);
                }
            }
        });
    }

    public LoginFrame() {
        setTitle("Flight Reservation System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Flight Reservation System");
        titleLabel.setFont(MAIN_FONT);
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton userLoginButton = createStyledButton("User Login");
        JButton adminLoginButton = createStyledButton("Admin Login");
        JButton guestLoginButton = createStyledButton("Guest Login");
        JButton userRegisterButton = createStyledButton("Register User");

        // Add action listeners to your buttons
        userRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the login frame
                LoginFrame.this.dispose(); // Optionally, you can dispose the LoginFrame

                // Create and show the register frame
                RegisterFrame registerFrame = new RegisterFrame();
                registerFrame.setVisible(true);
            }
        });

        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserLoginPanel(); // Call the method to create the login panel
                LoginFrame.this.dispose(); // Optionally, you can dispose the LoginFrame
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAdminLoginPanel(); // Call the method to create the login panel
                LoginFrame.this.dispose(); // Optionally, you can dispose the LoginFrame
            }
        });

        guestLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the login frame
                LoginFrame.this.dispose(); // Optionally, you can dispose the LoginFrame

                // Create and show the welcome frame
                WelcomeFrame welcomeFrame = new WelcomeFrame("guest");
                welcomeFrame.setVisible(true);
            }
        });

        gbc.gridy = 0;
        buttonPanel.add(userLoginButton, gbc);
        gbc.gridy = 1;
        buttonPanel.add(adminLoginButton, gbc);
        gbc.gridy = 2;
        buttonPanel.add(guestLoginButton, gbc);
        gbc.gridy = 3;
        buttonPanel.add(userRegisterButton, gbc);

        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel groupLabel = new JLabel("group24");
        groupLabel.setFont(GROUP_FONT);
        groupLabel.setForeground(Color.GRAY);
        groupPanel.add(groupLabel);

        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(groupPanel, BorderLayout.SOUTH);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void createTicketPurchasePanel(String username) {

        JFrame ticketFrame = new JFrame("Purchase Tickets");
        ticketFrame.setSize(400, 300);
        ticketFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ticketFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel fromLabel = new JLabel("Select Departure:");
        JComboBox<String> fromComboBox = new JComboBox<>();
        JLabel toLabel = new JLabel("Select Destination:");
        JComboBox<String> toComboBox = new JComboBox<>();

        try {
            DBMS dbms = DBMS.getDBMS();
            ArrayList<String> origins = dbms.getOrigins();
            for (String origin : origins) {
                fromComboBox.addItem(origin);
            }

            ArrayList<String> destinations = dbms.getDestinations();
            for (String destination : destinations) {
                toComboBox.addItem(destination);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ticketFrame, "Error fetching locations: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        ticketFrame.add(fromLabel);
        ticketFrame.add(fromComboBox);
        ticketFrame.add(toLabel);
        ticketFrame.add(toComboBox);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // hide the ticket frame
                    ticketFrame.dispose();

                    String origin = (String) fromComboBox.getSelectedItem();
                    String destination = (String) toComboBox.getSelectedItem();

                    DBMS dbms = DBMS.getDBMS(); // This will not create a new instance but will return the existing
                                                // one.
                    ArrayList<Flight> flights = dbms.getFlights(origin, destination);

                    FlightInfoFrame flightInfoFrame = new FlightInfoFrame(flights, username); // Pass the username here
                    flightInfoFrame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ticketFrame, "Error fetching flight data: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ticketFrame.add(new JLabel());
        ticketFrame.add(confirmButton);

        ticketFrame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    // DBMS dbms = DBMS.getDBMS(); // Singleton instance
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
