package src;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;

public class AdminFrame extends JFrame {

    private static final Font MAIN_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Color BUTTON_COLOR = new Color(100, 149, 237); // Cornflower Blue
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Light Gray

    public AdminFrame() {
        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel welcomeLabel = new JLabel("Welcome to our Flight Reservation System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;

        JButton browseFlightButton = createStyledButton("Browse Flights");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(browseFlightButton, gbc);

        browseFlightButton.addActionListener(e -> browseFlightPanel());

        JButton crewListButton = createStyledButton("Browse Crew List for a Flight");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(crewListButton, gbc);

        crewListButton.addActionListener(e -> crewListPanel());

        JButton aircraftListButton = createStyledButton("List All Aircrafts");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(aircraftListButton, gbc);

        aircraftListButton.addActionListener(e -> {
            try {

                DBMS dbms = DBMS.getDBMS(); // This will not create a new instance but will return the existing
                                            // one.
                ArrayList<Aircraft> aircrafts = dbms.getAircrafts();
                String title = "List of All Aircrafts";
                AircraftInfoFrame AircraftInfo = new AircraftInfoFrame(title, aircrafts);
                AircraftInfo.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching flight data: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton updateCrewButton = createStyledButton("Update crew for a flight");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(updateCrewButton, gbc);

        updateCrewButton.addActionListener(e -> updateCrewPanel());

        JButton addAircraftButton = createStyledButton("Add an Aircraft");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(addAircraftButton, gbc);

        addAircraftButton.addActionListener(e -> addAircraftPanel());

        JButton removeAircraftButton = createStyledButton("Remove Aircraft");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(removeAircraftButton, gbc);

        removeAircraftButton.addActionListener(e -> removeAircraftPanel());

        JButton editFlightButton = createStyledButton("Edit flight");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(editFlightButton, gbc);

        editFlightButton.addActionListener(e -> {
            FlightEditFrame flightEditFrame = new FlightEditFrame(); // Create the welcome window
            flightEditFrame.setVisible(true); // Show the welcome window
        });

        JButton displayUsersButton = createStyledButton("List Registered Users");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(displayUsersButton, gbc);

        displayUsersButton.addActionListener(e -> {
            try {

                DBMS dbms = DBMS.getDBMS();
                ArrayList<RegisteredUser> users = dbms.getRegisteredUsers();

                DisplayUsers displayUsers = new DisplayUsers(users);
                displayUsers.setVisible(true);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching flight data: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(buttonPanel, BorderLayout.CENTER);

        JButton displayPromoButton = createStyledButton("Send Monthly Promotion");
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(displayUsersButton, gbc);

        displayPromoButton.addActionListener(e -> {
            try {
                DBMS db = DBMS.getDBMS();
                Email_Controller email = new Email_Controller();

                email.notifyAboutPromotions(db.getCurrentPromotion());
                JOptionPane.showMessageDialog(null, "Promotion sent to users.", "Error",
                        JOptionPane.INFORMATION_MESSAGE);

            }catch(Exception error){
                JOptionPane.showMessageDialog(null, "Failed to send promotion.", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }


        });

        buttonPanel.add(displayPromoButton,gbc);

        JButton logoutButton = createStyledButton("Logout");
        gbc.insets = new Insets(0, 0, 10, 0);
        buttonPanel.add(logoutButton, gbc);

        logoutButton.addActionListener(e -> {
            // Hide the welcome frame
            AdminFrame.this.dispose(); // Optionally, you can dispose the WelcomeFrame

            // Create and show the login frame
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });

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

    private void removeAircraftPanel() {
        JFrame browseFrame = new JFrame("Remove Aircraft");
        browseFrame.setSize(400, 300);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel fromLabel = new JLabel("Enter a aircraftID: ");
        JTextField aircraftTextField = new JTextField();

        browseFrame.add(fromLabel);
        browseFrame.add(aircraftTextField);

        JButton confirmButton = new JButton("Remove Aircraft");
        confirmButton.addActionListener(e -> {
            try {

                int aircraftID = Integer.parseInt(aircraftTextField.getText());

                DBMS dbms = DBMS.getDBMS(); // This will not create a new instance but will return the existing
                                            // one.
                dbms.removeAircraft(aircraftID);
                ArrayList<Aircraft> aircrafts = dbms.getAircrafts();
                String title = "Updated Aircraft List";
                AircraftInfoFrame aircraftInfo = new AircraftInfoFrame(title, aircrafts);
                aircraftInfo.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(browseFrame, "Error fetching flight data: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        browseFrame.add(new JLabel());
        browseFrame.add(confirmButton);

        browseFrame.setVisible(true);
    }

    private void addAircraftPanel() {
        JFrame browseFrame = new JFrame("Add Aircraft");
        browseFrame.setSize(800, 600);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Use GridBagLayout for center alignment
        browseFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        JTextField modelTextField = new JTextField(20);
        JTextField ecoTextField = new JTextField(20);
        JTextField comfortTextField = new JTextField(20);
        JTextField businessTextField = new JTextField(20);
        JTextField ecoPriceTextField = new JTextField(20);
        JTextField businessPriceTextField = new JTextField(20);
        browseFrame.add(createLabelAndTextField("Enter Aircraft model: ", modelTextField), gbc);
        gbc.gridy++;
        browseFrame.add(createLabelAndTextField("Enter Number of Economy Seats: ", ecoTextField), gbc);
        gbc.gridy++;
        browseFrame.add(createLabelAndTextField("Enter Number of Comfort Seats: ", comfortTextField), gbc);
        gbc.gridy++;
        browseFrame.add(createLabelAndTextField("Enter Number of Business Seats: ", businessTextField), gbc);
        gbc.gridy++;
        browseFrame.add(createLabelAndTextField("Enter Economy Price: ", ecoPriceTextField), gbc);
        gbc.gridy++;
        browseFrame.add(createLabelAndTextField("Enter Business Price: ", businessPriceTextField), gbc);
        gbc.gridy++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton addButton = new JButton("Add Aircraft");
        addButton.addActionListener(e -> {
            try {
                String aircraftModel = modelTextField.getText();
                int numEconomySeats = Integer.parseInt(ecoTextField.getText());
                int numComfortSeats = Integer.parseInt(comfortTextField.getText());
                int numBusinessSeats = Integer.parseInt(businessTextField.getText());
                double economyPrice = Double.parseDouble(ecoPriceTextField.getText());
                double businessPrice = Double.parseDouble(businessPriceTextField.getText());

                DBMS dbms = DBMS.getDBMS();
                dbms.addAircraft(aircraftModel, numEconomySeats, numComfortSeats, numBusinessSeats,
                        economyPrice, businessPrice);

                ArrayList<Aircraft> aircrafts = dbms.getAircrafts();
                String title = "Updated Aircraft List";
                AircraftInfoFrame aircraftInfo = new AircraftInfoFrame(title, aircrafts);

                aircraftInfo.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(browseFrame, "Please enter valid numeric values.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(browseFrame, "Error fetching flight data: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(addButton);

        browseFrame.add(new JLabel());
        browseFrame.add(buttonPanel);

        browseFrame.setVisible(true);
    }

    private JPanel createLabelAndTextField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel(labelText);

        panel.add(label);
        panel.add(textField);

        return panel;
    }

    private void updateCrewPanel() {
        JFrame browseFrame = new JFrame("Update Crew");
        browseFrame.setSize(600, 300);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel crewIDLabel = new JLabel("Enter a crewID you would like to update: ");
        JTextField crewIDTextField = new JTextField();

        JLabel flightLabel = new JLabel("Enter the flightID you would like to change to: ");
        JTextField flightTextField = new JTextField();

        browseFrame.add(crewIDLabel);
        browseFrame.add(crewIDTextField);
        browseFrame.add(flightLabel);
        browseFrame.add(flightTextField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {

                int crewID = Integer.parseInt(crewIDTextField.getText());
                int flightID = Integer.parseInt(flightTextField.getText());

                DBMS dbms = DBMS.getDBMS(); // This will not create a new instance but will return the existing
                                            // one.
                dbms.updateCrew(crewID, flightID);

                ArrayList<CrewMember> crew = dbms.getCrewMembers(flightID);
                CrewInfoFrame crewInfo = new CrewInfoFrame(flightID, crew);

                crewInfo.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(browseFrame, "Error fetching flight data: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        browseFrame.add(new JLabel());
        browseFrame.add(confirmButton);

        browseFrame.setVisible(true);
    }

    private void browseFlightPanel() {
        JFrame browseFrame = new JFrame("Browse Flights");
        browseFrame.setSize(400, 300);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel fromLabel = new JLabel("Enter a date (YYYY-MM-DD): ");
        JTextField daTextField = new JTextField();

        browseFrame.add(fromLabel);
        browseFrame.add(daTextField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {

                LocalDate date = LocalDate.parse(daTextField.getText());

                DBMS dbms = DBMS.getDBMS(); // This will not create a new instance but will return the existing
                                            // one.
                ArrayList<Flight> flights = dbms.getFlights(date);

                String title = "Flights on " + date.toString();

                AdminFlightInfoFrame flightInfo = new AdminFlightInfoFrame(title, flights);
                flightInfo.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(browseFrame, "Error fetching flight data: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        browseFrame.add(new JLabel());
        browseFrame.add(confirmButton);

        browseFrame.setVisible(true);
    }

    private void crewListPanel() {
        JFrame browseFrame = new JFrame("Browse Crew List for a Flight");
        browseFrame.setSize(400, 300);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel fromLabel = new JLabel("Enter a flightID: ");
        JTextField flightTextField = new JTextField();

        browseFrame.add(fromLabel);
        browseFrame.add(flightTextField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {

                int flightID = Integer.parseInt(flightTextField.getText());

                DBMS dbms = DBMS.getDBMS(); // This will not create a new instance but will return the existing
                                            // one.
                ArrayList<CrewMember> crew = dbms.getCrewMembers(flightID);

                CrewInfoFrame crewInfo = new CrewInfoFrame(flightID, crew);
                crewInfo.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(browseFrame, "Error fetching flight data: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        browseFrame.add(new JLabel());
        browseFrame.add(confirmButton);

        browseFrame.setVisible(true);
    }

    public class FlightEditFrame extends JFrame {
        public FlightEditFrame() {
            setTitle("Flight Editor");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setExtendedState(JFrame.MAXIMIZED_BOTH);

            JLabel welcomeLabel = new JLabel("Welcome to our Flight Reservation System", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            add(welcomeLabel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setBackground(BACKGROUND_COLOR);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.NONE;

            JButton addFlightButton = createStyledButton("Add Flight");
            gbc.insets = new Insets(10, 0, 10, 0);
            buttonPanel.add(addFlightButton, gbc);

            addFlightButton.addActionListener(e -> addFlightPanel());

            JButton removeFlightButton = createStyledButton("Remove Flight");
            gbc.insets = new Insets(10, 0, 10, 0);
            buttonPanel.add(removeFlightButton, gbc);

            removeFlightButton.addActionListener(e -> removeFlightPanel());

            JButton editOriButton = createStyledButton("Edit Origin");
            gbc.insets = new Insets(10, 0, 10, 0);
            buttonPanel.add(editOriButton, gbc);

            editOriButton.addActionListener(e -> editFlightPanel("Origin"));

            JButton editDestButton = createStyledButton("Edit Destination");
            gbc.insets = new Insets(10, 0, 10, 0);
            buttonPanel.add(editDestButton, gbc);

            editDestButton.addActionListener(e -> editFlightPanel("Destination"));

            JButton editDepartDateTimeButton = createStyledButton("Edit Destination Date and Time");
            gbc.insets = new Insets(10, 0, 10, 0);
            buttonPanel.add(editDepartDateTimeButton, gbc);

            editDepartDateTimeButton.addActionListener(e -> editFlightPanel("DepartureDateTime"));

            JButton editArrivalDateTimeButton = createStyledButton("Edit Arrival Date and Time");
            gbc.insets = new Insets(10, 0, 10, 0);
            buttonPanel.add(editArrivalDateTimeButton, gbc);

            editArrivalDateTimeButton.addActionListener(e -> editFlightPanel("ArrivalDateTime"));

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

        private void removeFlightPanel() {
            JFrame browseFrame = new JFrame("Remove Flight");
            browseFrame.setSize(400, 300);
            browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            browseFrame.setLayout(new GridLayout(5, 2, 10, 10));

            JLabel fromLabel = new JLabel("Enter flightID: ");
            JTextField aircraftTextField = new JTextField();

            browseFrame.add(fromLabel);
            browseFrame.add(aircraftTextField);

            JButton confirmButton = new JButton("Remove Flight");
            confirmButton.addActionListener(e -> {
                try {

                    int flightID = Integer.parseInt(aircraftTextField.getText());
                    DBMS dbms = DBMS.getDBMS(); // This will not create a new instance but will return the existing
                    dbms.removeFlight(flightID);
                    ArrayList<Flight> flights = dbms.getFlights();
                    String title = "Updated Flight List";
                    AdminFlightInfoFrame flightInfo = new AdminFlightInfoFrame(title, flights);
                    flightInfo.setVisible(true);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(browseFrame, "Error fetching flight data: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            browseFrame.add(new JLabel());
            browseFrame.add(confirmButton);

            browseFrame.setVisible(true);
        }

        private void addFlightPanel() {
            JFrame browseFrame = new JFrame("Add Flight");
            browseFrame.setSize(600, 400);
            browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Use GridBagLayout for center alignment
            browseFrame.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

            JTextField originTextField = new JTextField(20);
            JTextField destTextField = new JTextField(20);
            JTextField departDateTimeTextField = new JTextField(20);
            JTextField arrivalDateTimeTextField = new JTextField(20);
            JTextField aircraftIDField = new JTextField(20);
            browseFrame.add(createLabelAndTextField("Enter Origin: ", originTextField), gbc);
            gbc.gridy++;
            browseFrame.add(createLabelAndTextField("Enter Destination: ", destTextField), gbc);
            gbc.gridy++;
            browseFrame.add(createLabelAndTextField("Enter Departure Date and Time: ", departDateTimeTextField), gbc);
            gbc.gridy++;
            browseFrame.add(createLabelAndTextField("Enter Arrival Date and Time: ", arrivalDateTimeTextField), gbc);
            gbc.gridy++;
            browseFrame.add(createLabelAndTextField("Enter Aircraft ID: ", aircraftIDField), gbc);
            gbc.gridy++;

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton addButton = new JButton("Add Flight");
            addButton.addActionListener(e -> {
                try {
                    String origin = originTextField.getText();
                    String destination = destTextField.getText();
                    LocalDate departureDate = LocalDate.parse(departDateTimeTextField.getText().split(" ")[0]);
                    LocalTime departureTime = LocalTime.parse(departDateTimeTextField.getText().split(" ")[1]);
                    LocalDate arrivalDate = LocalDate.parse(arrivalDateTimeTextField.getText().split(" ")[0]);
                    LocalTime arrivalTime = LocalTime.parse(arrivalDateTimeTextField.getText().split(" ")[1]);
                    int aircraftID = Integer.parseInt(aircraftIDField.getText());

                    DBMS dbms = DBMS.getDBMS();
                    Aircraft aircraft = dbms.getAircraftbyID(aircraftID);
                    dbms.addFlight(aircraft, origin, destination, departureDate, departureTime, arrivalDate,
                            arrivalTime);

                    ArrayList<Flight> flights = dbms.getFlights();
                    String title = "Updated Flight List";
                    AdminFlightInfoFrame flightInfo = new AdminFlightInfoFrame(title, flights);

                    flightInfo.setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(browseFrame, "Please enter valid numeric values.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(browseFrame, "Error fetching flight data: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            buttonPanel.add(addButton);

            browseFrame.add(new JLabel());
            browseFrame.add(buttonPanel);

            browseFrame.setVisible(true);
        }

        private void editFlightPanel(String indicator) {
            JFrame editFrame = new JFrame("Edit Flight " + indicator);
            editFrame.setSize(400, 200);
            editFrame.setLayout(new GridLayout(4, 2));

            JLabel flightIdLabel = new JLabel("Flight ID:");
            JTextField flightIdText = new JTextField();

            JLabel dataLabel = new JLabel("New Data:");
            JTextField dataText = new JTextField();

            JButton editButton = new JButton("Edit Flight");

            editButton.addActionListener(e -> {
                try {
                    int flightID = Integer.parseInt(flightIdText.getText());
                    String data = dataText.getText();
                    DBMS dbms = DBMS.getDBMS();
                    dbms.editFlightLocation(flightID, indicator, data);
                    Flight flight = dbms.getFlights(flightID);
                    DisplayFlight displayFlight = new DisplayFlight(flight);
                    displayFlight.setVisible(true);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error fetching flight data: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            editFrame.add(flightIdLabel);
            editFrame.add(flightIdText);
            editFrame.add(dataLabel);
            editFrame.add(dataText);
            editFrame.add(editButton);

            editFrame.setVisible(true);
        }

    }

    public class DisplayUsers extends JFrame {
        public DisplayUsers(ArrayList<RegisteredUser> users) {
            setTitle("Registered User Information");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setSize(600, 400);

            String[] columnNames = { "User ID", "Username", "Email", "Address", "Has Insurance", "Companion Tickets"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (RegisteredUser u : users) {

                Object[] row = new Object[6]; // Adjusted to match the number of columns
                row[0] = u.getUserID();
                row[1] = u.getUsername();
                row[2] = u.getEmail();
                row[3] = u.getAddress();
                row[4] = u.getHasInsurance();
                row[5] = u.getCompanionTickets();

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

    public class DisplayFlight extends JFrame {
        public DisplayFlight(Flight flight) {
            setTitle("Updated Flight Information");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setSize(800, 400);

            String[] columnNames = { "Flight ID", "Origin", "Destination", "Departure Date", "Time",
                    "Arrival Date", "Time" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            Object[] row = new Object[7]; // Adjusted to match the number of columns
            row[0] = flight.getFlightID();
            row[1] = flight.getDepartureLocation();
            row[2] = flight.getArrivalLocation();
            row[3] = flight.getDepartureDate();
            row[4] = flight.getDepartureTime();
            row[5] = flight.getArrivalDate();
            row[6] = flight.getArrivalTime();
            model.addRow(row);

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            // Add the JScrollPane to the frame
            add(scrollPane, BorderLayout.CENTER);

            // Make the frame visible
            setVisible(true);
        }
    }

    public class AircraftInfoFrame extends JFrame {
        public AircraftInfoFrame(String title, ArrayList<Aircraft> aircrafts) {
            setTitle(title);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setSize(800, 400);

            String[] columnNames = { "Aircraft ID", "Model", "Economy Seats", "Comfort Seats", "Business Seats",
                    "Economy Price", "Business Price" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Aircraft aircraft : aircrafts) {
                Object[] row = new Object[7];
                row[0] = aircraft.getAircraftID();
                row[1] = aircraft.getAircraftModel();
                row[2] = aircraft.getNumEconomySeats();
                row[3] = aircraft.getNumComfortSeats();
                row[4] = aircraft.getNumBusinessSeats();
                row[5] = aircraft.getEconomyPrice();
                row[6] = aircraft.getBusinessPrice();
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

    public class CrewInfoFrame extends JFrame {
        public CrewInfoFrame(int flightID, ArrayList<CrewMember> crewList) {
            setTitle("Crew Information for Flight " + flightID);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setSize(600, 400);

            String[] columnNames = { "Crew ID", "Name", "Position", "FlightID" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (CrewMember crewMember : crewList) {
                Object[] row = new Object[4];
                row[0] = crewMember.getCrewID();
                row[1] = crewMember.getCrewName();
                row[2] = crewMember.getCrewPos();
                row[3] = crewMember.getFlightID();
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

    public class AdminFlightInfoFrame extends JFrame {
        public AdminFlightInfoFrame(String title, ArrayList<Flight> flights) {
            setTitle(title);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setSize(800, 600);

            String[] columnNames = { "Flight ID", "Origin", "Destination", "Departure Date", "Time", "Arrival Date",
                    "Time" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (Flight flight : flights) {
                Object[] row = new Object[7]; // Adjusted to match the number of columns
                row[0] = flight.getFlightID();
                row[1] = flight.getDepartureLocation();
                row[2] = flight.getArrivalLocation();
                row[3] = flight.getDepartureDate();
                row[4] = flight.getDepartureTime();
                row[5] = flight.getArrivalDate();
                row[6] = flight.getArrivalTime();
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
}