package src;

// import src.LoginFrame.ConfirmationFrame;
// import src.LoginFrame.PaymentFrame;
// import src.LoginFrame.SeatSelectionFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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
        totalPrice = economyClassButton.isSelected() ? economyPrice
                : businessClassButton.isSelected() ? businessPrice : comfortPrice;
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
                        economyClassButton.isSelected() ? economySeats
                                : businessClassButton.isSelected() ? businessSeats : comfortSeats,
                        economyClassButton.isSelected() ? "Economy Class"
                                : businessClassButton.isSelected() ? "Business Class" : "Comfort Class",
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

        public void makeSeatUnavailable(String seatNumber) {
            for (Component component : getContentPane().getComponents()) {
                if (component instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane) component;
                    for (Component component2 : scrollPane.getViewport().getComponents()) {
                        if (component2 instanceof JPanel) {
                            JPanel seatPanel = (JPanel) component2;
                            for (Component component3 : seatPanel.getComponents()) {
                                if (component3 instanceof JButton) {
                                    JButton button = (JButton) component3;
                                    if (button.getText().equals(seatNumber)) {
                                        button.setEnabled(false);
                                    }
                                }
                            }
                        }
                    }
                }
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
                    // Check if user has companionTicket(s)
                    int companionTickets = 0;
                    User user = null;
                    try {
                        DBMS dbms = DBMS.getDBMS();
                        user = dbms.getUser(username);
                        if (user instanceof RegisteredUser) {
                            companionTickets = ((RegisteredUser) user).getCompanionTickets();
                        }
                        else {
                            // Close the seat selection frame and open the payment frame
                            SeatSelectionFrame.this.dispose();
                            PaymentFrame paymentFrame = new PaymentFrame(totalPrice, SeatSelectionFrame.this, username);
                            paymentFrame.setVisible(true);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(SeatSelectionFrame.this,
                                "Error accessing database: " + ex.getMessage(),
                                "Database Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    // if companion tickets are available, ask if user wants to use them to buy another seat
                    if (companionTickets > 0) {
                        int result = JOptionPane.showConfirmDialog(SeatSelectionFrame.this,
                                "You have " + companionTickets + " companion tickets available. " +
                                        "Would you like to use one to purchase another seat?",
                                "Companion Ticket", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            // create new seat selection frame with 1 less companion ticket
                            // seat selection frame will close after extra seat is selected
                            // no payment frame will be opened for the extra seat
                            // also close old seat selection frame
                            SeatSelectionFrame.this.dispose();
                            CompanionSelectionFrame companionSelectionFrame = new CompanionSelectionFrame(
                                    totalSeats, seatType, totalPrice, flightInfoFrame, bookingFrame, username, SeatSelectionFrame.this);
                            // Disable already selected seat the user initially selected
                            companionSelectionFrame.makeSeatUnavailable(getSelectedSeatNumber());
                            companionSelectionFrame.setVisible(true);
                            ((RegisteredUser) user).setCompanionTickets(companionTickets - 1);
                            try {
                                DBMS dbms = DBMS.getDBMS();
                                dbms.updateUser(user);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(SeatSelectionFrame.this,
                                        "Error accessing database: " + ex.getMessage(),
                                        "Database Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else {
                            // Close the seat selection frame and open the payment frame
                            SeatSelectionFrame.this.dispose();
                            PaymentFrame paymentFrame = new PaymentFrame(totalPrice, SeatSelectionFrame.this, username);
                            paymentFrame.setVisible(true);
                        }
                    }
                    else {
                        // Close the seat selection frame and open the payment frame
                        SeatSelectionFrame.this.dispose();
                        PaymentFrame paymentFrame = new PaymentFrame(totalPrice, SeatSelectionFrame.this, username);
                        paymentFrame.setVisible(true);
                    }
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

    class CompanionSelectionFrame extends JFrame {
        private FlightInfoFrame flightInfoFrame;
        private BookingFrame bookingFrame;
        private double totalPrice;
        private JButton confirmButton;
        private JButton selectedSeatButton;
        private String username;
        private String seatType;

        public String getSelectedSeatNumber() {
            if (selectedSeatButton != null) {
                return selectedSeatButton.getText();
            } else {
                return "";
            }
        }

        public void makeSeatUnavailable(String seatNumber) {
            for (Component component : getContentPane().getComponents()) {
                if (component instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane) component;
                    for (Component component2 : scrollPane.getViewport().getComponents()) {
                        if (component2 instanceof JPanel) {
                            JPanel seatPanel = (JPanel) component2;
                            for (Component component3 : seatPanel.getComponents()) {
                                if (component3 instanceof JButton) {
                                    JButton button = (JButton) component3;
                                    if (button.getText().equals(seatNumber)) {
                                        button.setEnabled(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public CompanionSelectionFrame(int totalSeats, String seatType, double totalPrice, FlightInfoFrame flightInfoFrame,
                                  BookingFrame bookingFrame, String username, SeatSelectionFrame seatSelectionFrame) {
            this.totalPrice = totalPrice;
            this.flightInfoFrame = flightInfoFrame;
            this.bookingFrame = bookingFrame;
            this.totalPrice = totalPrice;
            this.seatType = seatType;
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
                JOptionPane.showMessageDialog(CompanionSelectionFrame.this,
                        "Error accessing database: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            confirmButton = new JButton("Confirm Selection");
            confirmButton.setEnabled(false);
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // add order to database for companion ticket and then close frame
                    try {
                        String seatType = seatSelectionFrame.bookingFrame.isEconomyClassSelected() ? "Economy" :
                                seatSelectionFrame.bookingFrame.isBusinessClassSelected() ? "Business" : "Comfort";
                        DBMS dbms = DBMS.getDBMS();
                        int order = dbms.addOrder(dbms.getEmail(username), username,
                                flightInfoFrame.getSelectedFlight().getFlightID(),
                                flightInfoFrame.getSelectedFlight().getAircraft().getAircraftModel(),
                                flightInfoFrame.getSelectedFlight().getDepartureLocation(),
                                flightInfoFrame.getSelectedFlight().getArrivalLocation(),
                                Timestamp.valueOf(LocalDateTime.of(flightInfoFrame.getDepartureDate(),
                                        flightInfoFrame.getDepartureTime())),
                                Timestamp.valueOf(LocalDateTime.of(flightInfoFrame.getArrivalDate(),
                                        flightInfoFrame.getArrivalTime())),
                                seatType, getSelectedSeatNumber(), false, 0);
                        System.out.println("Order added for companion ticket with id: " + order);
                        // Create small window for companion ticket if order id is not -1
                        if (order != -1) {
                            // small window with "ok" button to close and open payment frame for original seat
                            JFrame frame = new JFrame();
                            frame.setTitle("Companion Ticket");
                            frame.setSize(600, 100);
                            frame.setLayout(new BorderLayout());
                            frame.add(new JLabel("Companion ticket purchased! Please check your email for " +
                                    "the ticket. Only valid with proof of main ticket purchase"), BorderLayout.CENTER);
                            JButton okButton = new JButton("OK");
                            okButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // send email for companion ticket
                                    try {
                                        DBMS db = DBMS.getDBMS();
                                        Email_Controller.sendTicket(username, db.getEmail(username),
                                                flightInfoFrame.getSelectedFlight().getAircraft().getAircraftModel(),
                                                flightInfoFrame.getSelectedFlight().getDepartureLocation(),
                                                flightInfoFrame.getSelectedFlight().getArrivalLocation(),
                                                flightInfoFrame.getDepartureTime(), flightInfoFrame.getArrivalTime(),
                                                seatType, getSelectedSeatNumber());
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                    frame.dispose();
                                    // open payment frame for original seat
                                    PaymentFrame paymentFrame = new PaymentFrame(totalPrice, seatSelectionFrame, username);
                                    paymentFrame.setVisible(true);
                                    // close companion selection frame
                                    CompanionSelectionFrame.this.dispose();
                                }
                            });
                            frame.add(okButton, BorderLayout.SOUTH);
                            frame.setVisible(true);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(CompanionSelectionFrame.this,
                                "Error accessing database: " + ex.getMessage(),
                                "Database Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
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
            } else {
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

            // pack();
            setVisible(true);
            JPanel finalCardPanel = cardPanel;
            JTextField finalEmailField = emailField;
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Validate the card information here (If user logged in then just check
                    // with db)
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
                        } else {
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
                        ConfirmationFrame confirmationFrame = new ConfirmationFrame(email, username, selectedFlight,
                                isEconomy,
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

    public class ConfirmationFrame extends JFrame {
        public ConfirmationFrame(String email, String username, Flight selectedFlight, boolean isEconomy,
                boolean isBusiness,
                boolean hasInsurance,
                String seatNumber, double totalPrice, LocalDate departureDate, LocalTime departureTime,
                LocalDate arrivalDate, LocalTime arrivalTime) {
            setTitle("Booking Confirmation");
            setSize(800, 300); // Adjust the size as needed
            setLayout(new BorderLayout());
            int orderID = -1;
            try {
                DBMS dbms = DBMS.getDBMS();

                orderID = dbms.addOrder(email, username, selectedFlight.getFlightID(),
                        selectedFlight.getAircraft().getAircraftModel(),
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

            // This block sends the emails after purchasing a ticket
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
        }
    }
}
