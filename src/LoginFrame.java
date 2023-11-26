import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        // Login Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
                        WelcomeFrame welcomeFrame = new WelcomeFrame(); // Create the welcome window
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
        // Here we should actually hash the password and compare against hashed password
        // in the database
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ensf480", "root", "ensf480");
            String sql = "SELECT * FROM users WHERE Name = ? AND PasswordHash = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password); // In real app, you should hash the password before comparing

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true; // User found with matching username and password
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + sqlException.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return false; // User not found, or db error occurred
    }

    public class WelcomeFrame extends JFrame {
        public WelcomeFrame() {
            setTitle("Welcome");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout()); 
            setExtendedState(JFrame.MAXIMIZED_BOTH); 

            // 欢迎信息
            JLabel welcomeLabel = new JLabel("Welcome to our Flight Reservation System", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            add(welcomeLabel, BorderLayout.NORTH);


            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setBackground(BACKGROUND_COLOR); 
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.NONE;

            // "Buy Tickets" 按钮
            JButton buyTicketsButton = createStyledButton("Buy Tickets");
            gbc.insets = new Insets(10, 0, 10, 0); // 顶部和底部的间距
            buttonPanel.add(buyTicketsButton, gbc);

            buyTicketsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createTicketPurchasePanel(); 
                }
            });

            // "Cancel Flight" 按钮
            JButton cancelFlightButton = createStyledButton("Cancel Flight");
            gbc.insets = new Insets(0, 0, 10, 0); 
            buttonPanel.add(cancelFlightButton, gbc);

            add(buttonPanel, BorderLayout.CENTER);


            pack();
            setVisible(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        // 创建风格化的按钮
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
                LoginFrame.this.setVisible(false);
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
        JLabel groupLabel = new JLabel("group25");
        groupLabel.setFont(GROUP_FONT);
        groupLabel.setForeground(Color.GRAY);
        groupPanel.add(groupLabel);

        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(groupPanel, BorderLayout.SOUTH);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void createTicketPurchasePanel() {
        // 创建新的窗口
        JFrame ticketFrame = new JFrame("Purchase Tickets");
        ticketFrame.setSize(400, 300);
        ticketFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ticketFrame.setLayout(new GridLayout(5, 2, 10, 10)); 

        // 创建标签和下拉框
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

        // 添加组件到窗口
        ticketFrame.add(fromLabel);
        ticketFrame.add(fromComboBox);
        ticketFrame.add(toLabel);
        ticketFrame.add(toComboBox);

        // 确认按钮
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 获取用户选择的始发地和目的地
                    String origin = (String) fromComboBox.getSelectedItem();
                    String destination = (String) toComboBox.getSelectedItem();

                    // 从数据库获取航班信息
                    DBMS dbms = DBMS.getDBMS(); // This will not create a new instance but will return the existing one.
                    ArrayList<Flight> flights = dbms.getFlights(origin, destination);

                    // 创建并显示航班信息界面
                    FlightInfoFrame flightInfoFrame = new FlightInfoFrame(flights);
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

        // 显示窗口
        ticketFrame.setVisible(true);
    }

    public class BookingFrame extends JFrame {
        private JRadioButton economyClassButton;
        private JRadioButton businessClassButton;
        private JCheckBox insuranceCheckbox;
        private JLabel totalPriceLabel;

        private int economySeats; // Number of economy seats
        private int businessSeats; // Number of business seats

        private double economyPrice;
        private double businessPrice;
        private double insurancePrice;

        public BookingFrame(Aircraft aircraft, double economyPrice, double businessPrice, double insurancePrice) {
            // Use the Aircraft object to set the number of seats
            this.economySeats = aircraft.getNumEconomySeats();
            this.businessSeats = aircraft.getNumBusinessSeats();
            this.economyPrice = economyPrice;
            this.businessPrice = businessPrice;
            this.insurancePrice = insurancePrice;

            setTitle("Booking Options");
            setSize(300, 200); // Set size
            setLayout(new FlowLayout()); // Set layout

            economyClassButton = new JRadioButton("Economy Class", true);
            businessClassButton = new JRadioButton("Business Class");

            // Group the radio buttons.
            ButtonGroup group = new ButtonGroup();
            group.add(economyClassButton);
            group.add(businessClassButton);

            insuranceCheckbox = new JCheckBox("Cancellation Insurance");

            totalPriceLabel = new JLabel("Total Price: $" + economyPrice);

            JButton confirmButton = new JButton("Confirm");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    SeatSelectionFrame seatSelectionFrame = new SeatSelectionFrame(
                            economyClassButton.isSelected() ? economySeats : businessSeats,
                            economyClassButton.isSelected());
                    seatSelectionFrame.setVisible(true);
                }
            });

            add(economyClassButton);
            add(businessClassButton);
            add(insuranceCheckbox);
            add(totalPriceLabel);
            add(confirmButton);
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
        public FlightInfoFrame(ArrayList<Flight> flights) {
            setTitle("Flight Information");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setSize(600, 400);

            // 创建表格模型以展示航班信息
            String[] columnNames = { "Flight ID", "Origin", "Destination", "Departure Time", "Arrival Time" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            // 为表格添加数据
            for (Flight flight : flights) {
                Object[] row = new Object[5];
                row[0] = flight.getFlightID();
                row[1] = flight.getDepartureLocation();
                row[2] = flight.getArrivalLocation();
                row[3] = flight.getDepartureDate().toString() + " " + flight.getDepartureTime().toString();
                row[4] = flight.getArrivalDate().toString() + " " + flight.getArrivalTime().toString();
                model.addRow(row);
            }

            // 创建表格并将其添加到滚动面板
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
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
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        try {
                            int flightID = (Integer) table.getValueAt(selectedRow, 0);
                            Flight selectedFlight = Flight.getFlightByID(flights, flightID);
                            Aircraft aircraft = selectedFlight.getAircraft(); // Get the Aircraft object

                            DBMS dbms = DBMS.getDBMS(); // Singleton instance
                            double economyPrice = dbms.getEconomyPrice(aircraft.getAircraftID());
                            double businessPrice = dbms.getBusinessPrice(aircraft.getAircraftID());
                            double insurancePrice = 50; // This can be a fixed value or also retrieved from the database

                            FlightInfoFrame.this.dispose(); // Close the current frame

                            BookingFrame bookingFrame = new BookingFrame(aircraft, economyPrice, businessPrice,
                                    insurancePrice);
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
    }

    public class SeatSelectionFrame extends JFrame {
        private static final int ECONOMY_SEATS = 70; // Assuming 50 economy seats
        private static final int BUSINESS_SEATS = 30; // Assuming 20 business seats

        // Updated constructor to accept a total seat count
        // Updated constructor to accept a total seat count and class type
        public SeatSelectionFrame(int totalSeats, boolean isEconomy) {
            setTitle("Select Seats");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel seatPanel = new JPanel(new GridLayout(0, isEconomy ? 6 : 4, 2, 2)); // Adjust columns based on class
            seatPanel.setBorder(BorderFactory.createTitledBorder(isEconomy ? "Economy Class" : "Business Class"));

            // Create seats
            for (int i = 1; i <= totalSeats; i++) {
                String seatLabel = (isEconomy ? "E" : "B") + i;
                JButton seatButton = createSeatButton(seatLabel);
                seatPanel.add(seatButton);
            }

            JScrollPane scrollPane = new JScrollPane(seatPanel);
            add(scrollPane, BorderLayout.CENTER);

            pack(); // Adjust window to fit content
            setVisible(true);
        }

        private JButton createSeatButton(String seatText) {
            JButton button = new JButton(seatText);
            button.setPreferredSize(new Dimension(40, 40)); // Smaller buttons for compact layout
            // Optionally set button styles and add action listeners here
            return button;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
