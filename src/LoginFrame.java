package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.*;
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
//                // User is logged in so add logout button
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
     * Uses User's email in db to get all orders, then returns a list of all orders, with a button to cancel each one
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
                String[] columnNames = {"Order ID", "Flight ID", "Departure Location", "Arrival Location",
                        "Departure Time", "Arrival Time", "Seat Class", "Seat Number", "Insurance",
                        "Total Price"};
                // this table has 11 rows instead of 10 and OrderID is duplicated on both ends, how to fix?
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
     * Asks Guest for email, then goes through orders table and returns a list of all orders, with a button to cancel
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
        JButton userRegisterButton = createStyledButton("User Register");

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

    public class BookingFrame extends JFrame {

        private FlightInfoFrame flightInfoFrame;
        private JRadioButton economyClassButton;
        private JRadioButton comfortClassButton;
        private JRadioButton businessClassButton;
        private JCheckBox insuranceCheckbox;
        private JLabel totalPriceLabel;
        private String username;

        private int economySeats;
        private int comfortSeats;
        private int businessSeats;

        private double economyPrice;
        private double comfortPrice;
        private double businessPrice;
        private double insurancePrice;

        private double totalPrice; // Class variable to store the total price

        private void updatePrice() {
            totalPrice = economyClassButton.isSelected() ? economyPrice :
                    businessClassButton.isSelected() ? businessPrice: comfortPrice;
            if (insuranceCheckbox.isSelected()) {
                totalPrice += insurancePrice;
            }
            totalPriceLabel.setText("Price Per Seat: $" + String.format("%.2f", totalPrice));
        }

        public boolean isEconomyClassSelected() {
            return economyClassButton.isSelected();
        }

        public boolean isComfortClassSelected() {
            return comfortClassButton.isSelected();
        }

        public boolean isBusinessClassSelected() {
            return businessClassButton.isSelected();
        }

        public boolean isInsuranceSelected() {
            return insuranceCheckbox.isSelected();
        }

        public BookingFrame(FlightInfoFrame flightInfoFrame, Aircraft aircraft, double economyPrice,
                double businessPrice, double insurancePrice, String username) {
            this.flightInfoFrame = flightInfoFrame; // Store the FlightInfoFrame reference
            this.economySeats = aircraft.getNumEconomySeats();
            this.comfortSeats = aircraft.getNumComfortSeats();
            this.businessSeats = aircraft.getNumBusinessSeats();
            this.economyPrice = economyPrice;
            this.comfortPrice = this.economyPrice * 1.5;
            this.businessPrice = businessPrice;
            this.insurancePrice = insurancePrice;
            this.username = username;

            setTitle("Booking Options");
            setSize(600, 200); // Set size
            setLayout(new FlowLayout()); // Set layout

            economyClassButton = new JRadioButton("Economy Class", true);
            comfortClassButton = new JRadioButton("Comfort Class");
            businessClassButton = new JRadioButton("Business Class");

            // Group the radio buttons.
            ButtonGroup group = new ButtonGroup();
            group.add(economyClassButton);
            group.add(comfortClassButton);
            group.add(businessClassButton);

            insuranceCheckbox = new JCheckBox("Cancellation Insurance");

            totalPriceLabel = new JLabel("Total Price: $" + economyPrice);

            JButton confirmButton = new JButton("Confirm");
            // 在BookingFrame类中
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Hide the booking frame
                    BookingFrame.this.dispose();
                    SeatSelectionFrame seatSelectionFrame = new SeatSelectionFrame(
                            economyClassButton.isSelected() ? economySeats : businessClassButton.isSelected() ?
                                    businessSeats: comfortSeats,
                            economyClassButton.isSelected() ? "Economy Class" : businessClassButton.isSelected() ?
                                    "Business Class": "Comfort Class",
                            totalPrice,
                            flightInfoFrame, // 这里假设你是在FlightInfoFrame内部创建BookingFrame
                            BookingFrame.this, username);
                    seatSelectionFrame.setVisible(true);
                }
            });

            add(economyClassButton);
            add(comfortClassButton);
            add(businessClassButton);
            add(insuranceCheckbox);
            add(totalPriceLabel);
            add(confirmButton);

            ActionListener priceChangeListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePrice();
                }
            };

            // Add the ActionListener to the radio buttons and checkbox
            economyClassButton.addActionListener(priceChangeListener);
            comfortClassButton.addActionListener(priceChangeListener);
            businessClassButton.addActionListener(priceChangeListener);
            insuranceCheckbox.addActionListener(priceChangeListener);

            // Initialize the price based on default selected values
            updatePrice();
        }
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

    public class FlightInfoFrame extends JFrame {

        private JTable table;
        private ArrayList<Flight> flights;
        private String username;

        public FlightInfoFrame(ArrayList<Flight> flights, String username) {
            this.username = username;
            setTitle("Flight Information");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setSize(600, 400);
            this.flights = flights; // Save the flights to the class variable

            String[] columnNames = { "Flight ID", "Origin", "Destination", "Departure Time", "Arrival Time" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Flight flight : this.flights) {
                Object[] row = new Object[5];
                row[0] = flight.getFlightID();
                row[1] = flight.getDepartureLocation();
                row[2] = flight.getArrivalLocation();
                row[3] = flight.getDepartureDate().toString() + " " + flight.getDepartureTime().toString();
                row[4] = flight.getArrivalDate().toString() + " " + flight.getArrivalTime().toString();
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

            selectFlightButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = FlightInfoFrame.this.table.getSelectedRow();
                    if (selectedRow >= 0) {
                        try {
                            int flightID = (Integer) FlightInfoFrame.this.table.getValueAt(selectedRow, 0);
                            Flight selectedFlight = Flight.getFlightByID(FlightInfoFrame.this.flights, flightID);
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

    public class SeatSelectionFrame extends JFrame {
        private FlightInfoFrame flightInfoFrame;
        private BookingFrame bookingFrame;
        private double totalPrice;
        private JButton confirmButton;
        private JButton selectedSeatButton;
        private String username;

        public String getSelectedSeatNumber() {
            if (selectedSeatButton != null) {
                return selectedSeatButton.getText();
            } else {
                return "";
            }
        }

        public SeatSelectionFrame(int totalSeats, String seatType, double totalPrice, FlightInfoFrame flightInfoFrame,
                BookingFrame bookingFrame, String username) {
            this.totalPrice = totalPrice;
            this.flightInfoFrame = flightInfoFrame;
            this.bookingFrame = bookingFrame;
            this.totalPrice = totalPrice;
            setTitle("Select Seats");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            int numRows = (int) Math.ceil((totalSeats - 5) / 5.0) + 1;
            JPanel seatPanel = new JPanel(new GridLayout(numRows, 5, 10, 10));
            seatPanel.setBorder(BorderFactory.createTitledBorder(seatType));
            for (int row = 0; row < numRows; row++) {
                char seatChar = seatType.charAt(0);
                for (int col = 0; col < 5; col++) {
                    if (col == 2) {
                        seatPanel.add(Box.createRigidArea(new Dimension(50, 50))); // Gap after 2 seats
                    } else {
                        int seatNumber = (col < 3) ? col + 1 : col; // Calculate seat number
                        seatNumber += (row * 4); // Add the row offset
                        String seatLabel = String.valueOf(seatChar) + (seatNumber); // Convert to string
                        JButton seatButton = createSeatButton(seatLabel);
                        seatPanel.add(seatButton);
                    }
                }
            }

            // Check if seat is already booked and disable the button
            try {
                DBMS dbms = DBMS.getDBMS();
                ArrayList<Order> orders = dbms.getOrders(flightInfoFrame.getSelectedFlight().getFlightID());
                for (Order order : orders) {
                    String seatNumber = order.getSeatNumber();
                    for (Component component : seatPanel.getComponents()) {
                        if (component instanceof JButton) {
                            JButton button = (JButton) component;
                            if (button.getText().equals(seatNumber)) {
                                button.setEnabled(false);
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SeatSelectionFrame.this,
                        "Error accessing database: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }


            confirmButton = new JButton("Confirm Selection");
            confirmButton.setEnabled(false);
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Close the seat selection frame and open the payment frame
                    SeatSelectionFrame.this.dispose();
                    PaymentFrame paymentFrame = new PaymentFrame(totalPrice, SeatSelectionFrame.this, username);
                    paymentFrame.setVisible(true);
                }
            });

            add(confirmButton, BorderLayout.SOUTH);

            JScrollPane scrollPane = new JScrollPane(seatPanel);
            add(scrollPane, BorderLayout.CENTER);

            pack();
            setVisible(true);

        }

        private JButton createSeatButton(String seatText) {
            JButton button = new JButton(seatText);
            button.setPreferredSize(new Dimension(50, 50));
            button.setBackground(Color.LIGHT_GRAY);
            button.setBorder(BorderFactory.createRaisedBevelBorder());

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedSeatButton != null) {
                        selectedSeatButton.setBackground(Color.LIGHT_GRAY);
                    }

                    selectedSeatButton = (JButton) e.getSource();
                    selectedSeatButton.setBackground(Color.RED);
                    confirmButton.setEnabled(true);
                }
            });

            return button;
        }
    }

    class PaymentFrame extends JFrame {
        private double totalPrice;
        private String username;

        private FlightInfoFrame flightInfoFrame;
        private BookingFrame bookingFrame;
        private SeatSelectionFrame seatSelectionFrame;

        public void setFlightInfoFrame(FlightInfoFrame flightInfoFrame) {
            this.flightInfoFrame = flightInfoFrame;
        }

        public void setBookingFrame(BookingFrame bookingFrame) {
            this.bookingFrame = bookingFrame;
        }

        public void setSeatSelectionFrame(SeatSelectionFrame seatSelectionFrame) {
            this.seatSelectionFrame = seatSelectionFrame;
        }

        public PaymentFrame(double totalPrice, SeatSelectionFrame seatSelectionFrame, String username) {
            // Initialize variables
            this.totalPrice = totalPrice;
            setSeatSelectionFrame(seatSelectionFrame);
            this.flightInfoFrame = flightInfoFrame;
            this.bookingFrame = bookingFrame;
            this.seatSelectionFrame = seatSelectionFrame;

            setTitle("Make Payment");
            setLayout(new BorderLayout());
            setSize(400, 250);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Panel for the total price
            JPanel pricePanel = new JPanel();
            pricePanel.add(new JLabel("Total Price: "));
            JTextField priceField = new JTextField(10);
            priceField.setText(String.format("$%.2f", totalPrice));
            priceField.setEditable(false);
            pricePanel.add(priceField);

            // Panel for card information
            JPanel cardPanel = null;
            JTextField emailField = null;
            // if user is guest also ask for name and address
            if (username.equals("guest")) {
                cardPanel = new JPanel(new GridLayout(6, 2, 5, 5));
                cardPanel.add(new JLabel("Name:"));
                JTextField nameField = new JTextField();
                cardPanel.add(nameField);
                cardPanel.add(new JLabel("Email:"));
                emailField = new JTextField();
                cardPanel.add(emailField);
                cardPanel.add(new JLabel("Address:"));
                JTextField addressField = new JTextField();
                cardPanel.add(addressField);
            }
            else {
                cardPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            }
            cardPanel.add(new JLabel("Card Number:"));
            JTextField cardNumberField = new JTextField();
            cardPanel.add(cardNumberField);
            cardPanel.add(new JLabel("Expiry Date (MM/YY):"));
            JTextField expiryField = new JTextField();
            cardPanel.add(expiryField);
            cardPanel.add(new JLabel("CVV:"));
            JTextField cvvField = new JTextField();
            cardPanel.add(cvvField);


            // Confirm button to submit payment
            JButton confirmButton = new JButton("Confirm Payment");
            // Adding components to the frame
            add(pricePanel, BorderLayout.NORTH);
            add(cardPanel, BorderLayout.CENTER);
            add(confirmButton, BorderLayout.SOUTH);

//            pack();
            setVisible(true);
            JPanel finalCardPanel = cardPanel;
            JTextField finalEmailField = emailField;
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Validate the card information here (If user logged in then just check with db)
                    // TODO: Send Email confirmation
                    // Let's assume the payment is processed successfully here
                    boolean paymentSuccess = true; // This should be the result of your payment processing logic


                    if (paymentSuccess) {
                        JOptionPane.showMessageDialog(PaymentFrame.this, "Payment successful!", "Payment",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Retrieve the selected flight and other booking details
                        Flight selectedFlight = seatSelectionFrame.flightInfoFrame.getSelectedFlight();// flightInfoFrame.getSelectedFlight();
                        LocalDate departureDate = seatSelectionFrame.flightInfoFrame.getDepartureDate();
                        LocalTime departureTime = seatSelectionFrame.flightInfoFrame.getDepartureTime();
                        LocalDate arrivalDate = seatSelectionFrame.flightInfoFrame.getArrivalDate();
                        LocalTime arrivaltime = seatSelectionFrame.flightInfoFrame.getArrivalTime();
                        boolean isEconomy = seatSelectionFrame.bookingFrame.isEconomyClassSelected();

                        boolean isBusiness = seatSelectionFrame.bookingFrame.isBusinessClassSelected();
                        boolean hasInsurance = seatSelectionFrame.bookingFrame.isInsuranceSelected();
                        String seatNumber = seatSelectionFrame.getSelectedSeatNumber();

                        // Close the payment frame and open the confirmation frame
                        PaymentFrame.this.dispose();

                        String email = "";
                        if (username.equals("guest")) {
                            email = finalEmailField.getText();
                        }
                        else {
                            try {
                                DBMS dbms = DBMS.getDBMS();
                                email = dbms.getEmail(username);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(PaymentFrame.this,
                                        "Error accessing database: " + ex.getMessage(),
                                        "Database Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        System.out.println("Email: " + email);
                        ConfirmationFrame confirmationFrame = new ConfirmationFrame(email, username, selectedFlight, isEconomy,
                                isBusiness, hasInsurance, seatNumber, totalPrice, departureDate, departureTime,
                                arrivalDate, arrivaltime);
                        confirmationFrame.setVisible(true);
                        // hide the payment frame
                        PaymentFrame.this.dispose();
                    } else {
                        // Handle failed payment case
                        JOptionPane.showMessageDialog(PaymentFrame.this, "Payment failed. Please" +
                                        " try again.",
                                "Payment Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        }

    }

    // Q: Why is this class being called twice?
    // A: Because you are calling it twice in the PaymentFrame class
    // Q: Where in the class am i calling it?
    // A: In the constructor
    // Q: How do i fix it?
    // A: Remove the call from the constructor
    public class ConfirmationFrame extends JFrame {
        public ConfirmationFrame(String email, String username, Flight selectedFlight, boolean isEconomy, boolean isBusiness,
                boolean hasInsurance,
                String seatNumber, double totalPrice, LocalDate departureDate, LocalTime departureTime,
                LocalDate arrivalDate, LocalTime arrivalTime){
            setTitle("Booking Confirmation");
            setSize(500, 300); // Adjust the size as needed
            setLayout(new BorderLayout());
            int orderID = -1;
            try {
                orderID = updateDatabase(email, username, selectedFlight.getFlightID(),
                        selectedFlight.getAircraft().getAircraftModel(), selectedFlight.getDepartureLocation(),
                        selectedFlight.getArrivalLocation(), departureTime, arrivalTime, (isEconomy ? "Economy" : isBusiness
                                ? "Business" : "Comfort"), seatNumber, hasInsurance, totalPrice);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Order ID: " + orderID);

            JPanel infoPanel = new JPanel(new GridLayout(0, 1)); // Use GridLayout for listing the information
            infoPanel.add(new JLabel("This is your receipt:"));
            infoPanel.add(new JLabel("")); // Empty label for spacing
            infoPanel.add(new JLabel("Order ID: " + orderID)); // This should be the order ID from the database
            infoPanel.add(new JLabel("Passenger name: " + username));
            infoPanel.add(new JLabel("Flight ID: " + selectedFlight.getFlightID()));
            infoPanel.add(new JLabel("Aircraft: " + selectedFlight.getAircraft().getAircraftModel()));
            infoPanel.add(new JLabel("From: " + selectedFlight.getDepartureLocation()));
            infoPanel.add(new JLabel("To: " + selectedFlight.getArrivalLocation()));
            infoPanel.add(new JLabel("Departure time: " + departureTime));
            infoPanel.add(new JLabel("Arrival time: " + arrivalTime));
            infoPanel.add(new JLabel("Class: " + (isEconomy ? "Economy" : isBusiness ? "Business" : "Comfort")));
            infoPanel.add(new JLabel("Seat: " + seatNumber));
            infoPanel.add(new JLabel("Insurance: " + (hasInsurance ? "Yes" : "No")));
            infoPanel.add(new JLabel("total price: " + totalPrice));
            add(infoPanel, BorderLayout.CENTER);

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> dispose());
            add(closeButton, BorderLayout.SOUTH);

            try {
                DBMS db = DBMS.getDBMS();
                Email_Controller.sendReceipt(username, email, (hasInsurance ? "Yes" : "No"),
                        selectedFlight.getArrivalLocation(), totalPrice);
                Email_Controller.sendTicket(username, email, selectedFlight.getAircraft().getAircraftModel(),
                        selectedFlight.getDepartureLocation(), selectedFlight.getArrivalLocation(),
                        departureTime, arrivalTime, (isEconomy ? "Economy" : isBusiness ? "Business" : "Comfort"),
                        seatNumber);

            } catch (Exception e) {
                System.out.println(e);
            }
            pack();
            setLocationRelativeTo(null);
            setVisible(true);

            try {
                DBMS dbms = DBMS.getDBMS();

                dbms.addOrder(email, username, selectedFlight.getFlightID(), selectedFlight.getAircraft().getAircraftModel(),
                        selectedFlight.getDepartureLocation(), selectedFlight.getArrivalLocation(),
                        Timestamp.valueOf(LocalDateTime.of(departureDate, departureTime)),
                        Timestamp.valueOf(LocalDateTime.of(arrivalDate, arrivalTime)),
                        (isEconomy ? "Economy" : isBusiness ? "Business" : "Comfort"), seatNumber, hasInsurance,
                        totalPrice);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating database: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Q: This method is being called twice, why?
        // A: Because you are calling it twice in the PaymentFrame class
        // Q: Where in the class am i calling it?
        // A: In the constructor
        public int updateDatabase(String email, String username, int flightID, String aircraftModel, String departureLocation,
                                  String arrivalLocation, LocalTime departureTime, LocalTime arrivalTime, String seatClass,
                                  String seatNumber, boolean hasInsurance, double totalprice) throws SQLException {

            // Define the JDBC URL.
            String jdbcURL = "jdbc:mysql://localhost:3306/ensf480";
            String dbUser = "root"; // Replace with your database username.
            String dbPassword = "password"; // Replace with your database password.

            // SQL query to insert a new order.
            String sql = "INSERT INTO orders (Email, Username, FlightID, AircraftModel, DepartureLocation, ArrivalLocation, " +
                    "DepartureDateTime, ArrivalDateTime, Class, SeatNumber, Insurance, TotalPrice) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


            try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                // Set the parameters for the prepared statement.
                statement.setString(1, email);
                statement.setString(2, username);
                statement.setInt(3, flightID);
                statement.setString(4, aircraftModel);
                statement.setString(5, departureLocation);
                statement.setString(6, arrivalLocation);
                statement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), departureTime)));
                statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), arrivalTime)));
                statement.setString(9, seatClass);
                statement.setString(10, seatNumber);
                statement.setBoolean(11, hasInsurance);
                statement.setDouble(12, totalprice);

                // Execute the insert SQL statement.
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new order was inserted successfully!");
                }

                // Get the order ID of the inserted order
                String sql2 = "SELECT OrderID FROM orders WHERE Email = ? AND Username = ? AND FlightID = ? AND " +
                        "AircraftModel = ? AND DepartureLocation = ? AND ArrivalLocation = ? AND DepartureDateTime = ? " +
                        "AND ArrivalDateTime = ? AND Class = ? AND SeatNumber = ? AND Insurance = ? AND TotalPrice = ?";
                PreparedStatement statement2 = connection.prepareStatement(sql2);
                statement2.setString(1, email);
                statement2.setString(2, username);
                statement2.setInt(3, flightID);
                statement2.setString(4, aircraftModel);
                statement2.setString(5, departureLocation);
                statement2.setString(6, arrivalLocation);
                statement2.setTimestamp(7, Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), departureTime)));
                statement2.setTimestamp(8, Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), arrivalTime)));
                statement2.setString(9, seatClass);
                statement2.setString(10, seatNumber);
                statement2.setBoolean(11, hasInsurance);
                statement2.setDouble(12, totalprice);
                ResultSet result = statement2.executeQuery();
                if (result.next()) {
                    return result.getInt("OrderID");
                }
                else {
                    System.out.println("Error getting order ID");
                    return -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database update error: " + e.getMessage());
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
