package src;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;
import java.time.*;

/**
 * Database Management System, handles any interaction between the program and
 * the database
 *
 * DBMS instance - The global instance for our database manager (See Singleton
 * Design Pattern)
 * Connection dbConnect - SQL connection object for initializing connection with
 * database
 * ResultSet results - The results of queries will be in this object
 *
 */
public class DBMS {

    private static DBMS instance;
    private Connection dbConnect;
    private ResultSet results;

    /**
     * DBMS Constructor
     *
     * Called only when an instance does not yet exist, creates cnnection to local
     * database
     *
     */
    private DBMS() throws SQLException {
        // the connection info here will need to be changed depending on the user
        dbConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ENSF480", "root", "ensf480");
    }

    /**
     * DBMS Constructor
     *
     * Ensures only one instance of database manager exists at once, returns DBMS
     * object
     *
     */
    public static DBMS getDBMS() throws SQLException {
        if (instance == null) {
            System.out.println("Database instance created");
            instance = new DBMS();
        }
        return instance;
    }

    /**
     * closeConnection
     *
     * Closes database connection, use at end of program
     *
     */
    public void closeConnection() throws SQLException {
        dbConnect.close();
        results.close();

    }

    public String getEmail(String username) throws SQLException {
        String email = null;

        String query = "SELECT Email FROM Users WHERE Name = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet results = pstmt.executeQuery()) {
                if (results.next()) {

                    email = results.getString("Email");
                }
            }
        }

        return email;
    }

    public String getEmail(int orderID) throws SQLException {
        String email = null;

        String query = "SELECT Email FROM Orders WHERE OrderID = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(query)) {
            pstmt.setInt(1, orderID);

            try (ResultSet results = pstmt.executeQuery()) {
                if (results.next()) {

                    email = results.getString("Email");
                }
            }
        }

        return email;
    }

    /*
     * getAircraft list from database
     */
    public ArrayList<Aircraft> getAircrafts() throws SQLException {
        ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Aircrafts");

        Statement myStmt2 = dbConnect.createStatement();

        while (results.next()) {
            int aircraftID = results.getInt("AircraftID");
            String aircraftModel = results.getString("Model");
            int numEconomySeats = results.getInt("Ordinary");
            int numComfortSeats = results.getInt("Comfort");
            int numBusinessSeats = results.getInt("Business");
            double economyPrice = results.getDouble("EconomyPrice");
            double businessPrice = results.getDouble("BusinessPrice");

            Aircraft aircraft = new Aircraft(aircraftID, aircraftModel, numEconomySeats, numComfortSeats,
                    numBusinessSeats, economyPrice, businessPrice);
            aircrafts.add(aircraft);
        }
        results.close();
        return aircrafts;
    }

    /*
     * add aircraft to database
     */

    public void addAircraft(String aircraftModel, int numEconomySeats, int numComfortSeats,
            int numBusinessSeats, double economyPrice, double businessPrice) throws SQLException {
        // Statement myStmt = dbConnect.createStatement();

        // Use PreparedStatement to avoid SQL injection
        String sql = "INSERT INTO aircrafts (Model, Ordinary, Comfort, Business, EconomyPrice, BusinessPrice) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setString(1, aircraftModel);
            pstmt.setInt(2, numEconomySeats);
            pstmt.setInt(3, numComfortSeats);
            pstmt.setInt(4, numBusinessSeats);
            pstmt.setDouble(5, economyPrice);
            pstmt.setDouble(6, businessPrice);

            pstmt.executeUpdate();
        }
    }

    /*
     * remove aircraft from database
     */

    public void removeAircraft(int aircraftID) throws SQLException {
        Statement myStmt = dbConnect.createStatement();
        String sql = "DELETE FROM aircrafts WHERE AircraftID = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, aircraftID);
            pstmt.executeUpdate();
        }
    }

    /*
     * getFlight list based on destination and origin from database
     */
    public ArrayList<Flight> getFlights(String origin, String destination) throws SQLException {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        ArrayList<Aircraft> aircrafts = getAircrafts();
        Statement myStmt = dbConnect.createStatement();
        String query = "SELECT * FROM Flights WHERE Origin = ? AND Destination = ?";
        PreparedStatement pstmt = dbConnect.prepareStatement(query);
        pstmt.setString(1, origin);
        pstmt.setString(2, destination);
        ResultSet results = pstmt.executeQuery();
        while (results.next()) {
            LocalDateTime departureDateTime = results.getTimestamp("DepartureDateTime").toLocalDateTime();
            LocalDateTime arrivalDateTime = results.getTimestamp("ArrivalDateTime").toLocalDateTime();
            int aircraftID = results.getInt("AircraftID");
            Aircraft aircraft = null;
            for (Aircraft a : aircrafts) {
                if (a.getAircraftID() == aircraftID) {
                    aircraft = a;
                    break;
                }
            }
            Flight flight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),
                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());
            flights.add(flight);
        }
        results.close();
        return flights;
    }

    public ArrayList<String> getOrigins() throws SQLException {
        ArrayList<String> origins = new ArrayList<>();
        Statement statement = dbConnect.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT Origin FROM Flights");
        while (resultSet.next()) {
            origins.add(resultSet.getString("Origin"));
        }
        resultSet.close();
        statement.close();
        return origins;
    }

    public ArrayList<String> getDestinations() throws SQLException {
        ArrayList<String> destinations = new ArrayList<>();
        Statement statement = dbConnect.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT Destination FROM Flights");
        while (resultSet.next()) {
            destinations.add(resultSet.getString("Destination"));
        }
        resultSet.close();
        statement.close();
        return destinations;
    }

    /*
     * get ALL flights from database for selected date
     */

    public ArrayList<Flight> getFlights(LocalDate selectedDate) throws SQLException {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        ArrayList<Aircraft> aircrafts = getAircrafts();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Flights");
        while (results.next()) {
            LocalDateTime departureDateTime = results.getTimestamp("DepartureDateTime").toLocalDateTime();
            LocalDateTime arrivalDateTime = results.getTimestamp("ArrivalDateTime").toLocalDateTime();
            int aircraftID = results.getInt("AircraftID");
            Aircraft aircraft = null;
            for (Aircraft a : aircrafts) {
                if (a.getAircraftID() == aircraftID) {
                    aircraft = a;
                }
            }

            Flight flight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),

                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());
            flights.add(flight);
        }
        ArrayList<Flight> flightsOnDate = new ArrayList<Flight>();
        for (Flight f : flights) {
            if (f.getDepartureDate().equals(selectedDate)) {
                flightsOnDate.add(f);
            }
        }
        return flightsOnDate;
    }

    /*
     * getFlight list from database
     */
    public ArrayList<Flight> getFlights() throws SQLException {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        ArrayList<Aircraft> aircrafts = getAircrafts();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Flights");
        while (results.next()) {
            LocalDateTime departureDateTime = results.getTimestamp("DepartureDateTime").toLocalDateTime();
            LocalDateTime arrivalDateTime = results.getTimestamp("ArrivalDateTime").toLocalDateTime();
            int aircraftID = results.getInt("AircraftID");
            Aircraft aircraft = null;
            for (Aircraft a : aircrafts) {
                if (a.getAircraftID() == aircraftID) {
                    aircraft = a;
                }
            }

            Flight flight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),

                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());
            flights.add(flight);
        }
        results.close();
        return flights;
    }

    /*
     * add flight to database
     */
    public void addFlight(Aircraft aircraft, String origin, String destination, LocalDate departureDate,
            LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime) throws SQLException {
        String sql = "INSERT INTO Flights (AircraftID, Origin, Destination, DepartureDateTime, ArrivalDateTime) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, aircraft.getAircraftID());
            pstmt.setString(2, origin);
            pstmt.setString(3, destination);
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.of(departureDate, departureTime)));
            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.of(arrivalDate, arrivalTime)));

            pstmt.executeUpdate();
        }
    }

    /*
     * remove flight from database
     */
    public void removeFlight(int flightID) throws SQLException {
        Statement myStmt = dbConnect.createStatement();
        String sql = "DELETE FROM Flights WHERE FlightID = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, flightID);
            pstmt.executeUpdate();
        }
    }

    /*
     * edit flight in database
     */

    public void editFlightLocation(int flightID, String indicator, String data) {
        if ("Origin".equals(indicator)) {
            updateLocation(flightID, "Origin", data);
        } else if ("Destination".equals(indicator)) {
            updateLocation(flightID, "Destination", data);
        } else if ("DepartureDateTime".equals(indicator)) {
            updateDateTime(flightID, "DepartureDateTime", data);
        } else if ("ArrivalDateTime".equals(indicator)) {
            updateDateTime(flightID, "ArrivalDateTime", data);
        }

    }

    public Flight getFlights(int flightID) throws SQLException {
        Flight returnFlight = null;
        ArrayList<Aircraft> aircrafts = getAircrafts();
        Statement myStmt = dbConnect.createStatement();
        String query = "SELECT * FROM Flights WHERE flightID = ?";
        PreparedStatement pstmt = dbConnect.prepareStatement(query);
        pstmt.setInt(1, flightID);
        ResultSet results = pstmt.executeQuery();
        while (results.next()) {
            LocalDateTime departureDateTime = results.getTimestamp("DepartureDateTime").toLocalDateTime();
            LocalDateTime arrivalDateTime = results.getTimestamp("ArrivalDateTime").toLocalDateTime();
            int aircraftID = results.getInt("AircraftID");
            Aircraft aircraft = null;
            for (Aircraft a : aircrafts) {
                if (a.getAircraftID() == aircraftID) {
                    aircraft = a;
                    break;
                }
            }
            returnFlight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),
                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());

        }
        results.close();
        return returnFlight;
    }

    private void updateLocation(int flightID, String locationColumn, String data) {
        try {
            String updateQuery = "UPDATE flights SET " + locationColumn + " = ? WHERE FlightID = ?";
            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, data);
                preparedStatement.setInt(2, flightID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    private void updateDateTime(int flightID, String dateTimeColumn, String dateTime) {
        try {
            String updateQuery = "UPDATE flights SET " + dateTimeColumn + " = ? WHERE FlightID = ?";
            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(updateQuery)) {
                Timestamp timestamp = Timestamp.valueOf(dateTime);
                preparedStatement.setTimestamp(1, timestamp);
                preparedStatement.setInt(2, flightID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    /*
     * TODO: bookFlight - THIS DOESNT WORK YET
     * Books a flight and insert into database
     */

    public void bookFlight(int userID, int flightID, int seatID, LocalDateTime DateTime) throws SQLException {
        Statement myStmt = dbConnect.createStatement();
        String sql = "INSERT INTO Bookings (UserID, FlightID, SeatID, CancellationInsurance, BookingDateTime) VALUES ("
                + userID + flightID + seatID + "0" + DateTime + ")";
        myStmt.executeUpdate(sql);
    }

    /*
     * getUser List from database
     */

    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Users");
        while (results.next()) {
            int userID = results.getInt("UserID");
            String username = results.getString("Name");
            String address = results.getString("Address");
            String email = results.getString("Email");
            String userType = results.getString("UserType");
            String creditCardNumber = results.getString("CreditCardInfo");
            int creditCardExpiry = results.getInt("CreditCardExpiry");
            int creditCardCVV = results.getInt("CreditCardCVV");
            int companionTickets = results.getInt("CompanionTickets");
            switch (userType) {
                case "admin":
                    Admin admin = new Admin(userID, username, email, address);
                    users.add(admin);
                    break;
                case "crew":
                    String crewMemberPos = results.getString("CrewMemberPos");
                    CrewMember crewMember = new CrewMember(userID, username, email, address, crewMemberPos);
                    users.add(crewMember);
                    break;
                case "passenger":
                    CreditCard card = new CreditCard(creditCardNumber, username, creditCardExpiry, creditCardCVV);
                    RegisteredUser passenger = new RegisteredUser(userID, username, email, address, card, companionTickets);
                    users.add(passenger);
                    break;
                case "agent":
                    Agent agent = new Agent(userID, username, email, address);
                    users.add(agent);
                    break;

                default:
                    System.out.println("Invalid user type");
                    break;
            }
        }
        results.close();
        return users;
    }

    /*
     * add user to database
     */
    public void addUser(User user) throws SQLException {
        // SQL query for insertion using a prepared statement
        String insertQuery = "INSERT INTO Users (Name, Address, Email, UserType, CreditCardNumber, CreditCardExpiry," +
                "CreditCardCVV, CompanionTickets) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Create a prepared statement
        try (PreparedStatement preparedStatement = dbConnect.prepareStatement(insertQuery)) {
            // Set values for the placeholders in the query
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getAddress());
            preparedStatement.setString(3, user.getEmail());
            // get user type from user object
            if (user instanceof Admin) {
                preparedStatement.setString(4, "admin");
            } else if (user instanceof CrewMember) {
                preparedStatement.setString(4, "crew");
            } else if (user instanceof RegisteredUser) {
                preparedStatement.setString(4, "passenger");
            } else {
                System.out.println("Invalid user type");
            }
            // get credit card from user object if passenger
            if (user instanceof RegisteredUser) {
                CreditCard card = ((RegisteredUser) user).getCreditCard();
                preparedStatement.setString(5, card.getCardNumber());
                preparedStatement.setInt(6, card.getExpiryDate());
                preparedStatement.setInt(7, card.getCVV());
                preparedStatement.setInt(8, ((RegisteredUser) user).getCompanionTickets());
            }

            // Execute the insertion
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User inserted successfully!");
            } else {
                System.out.println("Failed to insert user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Given a username, return the user object
     */
    public User getUser(String username) throws SQLException {
        User user = null;

        String query = "SELECT * FROM Users WHERE Name = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(query)) {
            pstmt.setString(1, username);

            try (ResultSet results = pstmt.executeQuery()) {
                if (results.next()) {
                    int userID = results.getInt("UserID");
                    String address = results.getString("Address");
                    String email = results.getString("Email");
                    String userType = results.getString("UserType");
                    String creditCardNumber = results.getString("CreditCardInfo");
                    int creditCardExpiry = results.getInt("CreditCardExpiry");
                    int creditCardCVV = results.getInt("CreditCardCVV");
                    int companionTickets = results.getInt("CompanionTickets");
                    switch (userType) {
                        case "admin":
                            user = new Admin(userID, username, email, address);
                            break;
                        case "crew":
                            String crewMemberPos = results.getString("CrewMemberPos");
                            user = new CrewMember(userID, username, email, address, crewMemberPos);
                            break;
                        case "passenger":
                            CreditCard card = new CreditCard(creditCardNumber, username, creditCardExpiry, creditCardCVV);
                            user = new RegisteredUser(userID, username, email, address, card, companionTickets);
                            break;
                        case "agent":
                            user = new Agent(userID, username, email, address);
                            break;

                        default:
                            System.out.println("Invalid user type");
                            break;
                    }
                }
            }
        }

        return user;
    }

    /*
     * Update user information in database given user object
     */
    public void updateUser(User user) {
        try {
            String updateQuery = "UPDATE users SET Name = ?, Address = ?, Email = ?, UserType = ?, CreditCardInfo = ?, " +
                    "CreditCardExpiry = ?, CreditCardCVV = ?, CompanionTickets = ? WHERE UserID = ?";
            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getAddress());
                preparedStatement.setString(3, user.getEmail());
                // get user type from user object
                if (user instanceof Admin) {
                    preparedStatement.setString(4, "admin");
                } else if (user instanceof CrewMember) {
                    preparedStatement.setString(4, "crew");
                } else if (user instanceof RegisteredUser) {
                    preparedStatement.setString(4, "passenger");
                } else {
                    System.out.println("Invalid user type");
                }
                // get credit card from user object if passenger
                if (user instanceof RegisteredUser) {
                    CreditCard card = ((RegisteredUser) user).getCreditCard();
                    preparedStatement.setString(5, card.getCardNumber());
                    preparedStatement.setInt(6, card.getExpiryDate());
                    preparedStatement.setInt(7, card.getCVV());
                    preparedStatement.setInt(8, ((RegisteredUser) user).getCompanionTickets());
                }
                preparedStatement.setInt(9, user.getUserID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public double getEconomyPrice(int aircraftID) throws SQLException {
        String query = "SELECT EconomyPrice FROM Aircrafts WHERE AircraftID = ?";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setInt(1, aircraftID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("EconomyPrice");
            }
        }
        return -1; // Or throw an exception
    }

    public double getBusinessPrice(int aircraftID) throws SQLException {
        String query = "SELECT BusinessPrice FROM Aircrafts WHERE AircraftID = ?";
        try (PreparedStatement stmt = dbConnect.prepareStatement(query)) {
            stmt.setInt(1, aircraftID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("BusinessPrice");
            }
        }
        return -1; // Or throw an exception
    }

    /*
     * getCrew list from database
     */

    public ArrayList<CrewMember> getCrewMembers(int flight) throws SQLException {
        ArrayList<CrewMember> crewMembers = new ArrayList<>();

        String sql = "SELECT * FROM crews WHERE FlightID = ?";

        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, flight);

            try (ResultSet results = pstmt.executeQuery()) {
                while (results.next()) {
                    int crewID = results.getInt("CrewID");
                    String crewName = results.getString("Name");
                    String position = results.getString("Position");

                    CrewMember crewMember = new CrewMember(crewID, crewName, position);
                    crewMembers.add(crewMember);
                }
            }
        }

        return crewMembers;
    }

    /*
     * add crew to flight - changes flightID in database - 0 is no flights assigned
     */
    public void updateCrew(int crewID, int flight) throws SQLException {
        String sql = "UPDATE crews SET FlightID = ? WHERE CrewID = ?";

        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, flight);
            pstmt.setInt(2, crewID);
            pstmt.executeUpdate();
        }
    }

    /*
     * getBooking list from database
     */

    public ArrayList<Booking> getBookings() throws SQLException {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        ArrayList<User> users = getUsers();
        ArrayList<Flight> flights = getFlights();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Bookings");
        while (results.next()) {
            int bookingID = results.getInt("BookingID");
            int userID = results.getInt("UserID");
            User user = null;
            for (User u : users) {
                if (u.getUserID() == userID) {
                    user = u;
                }
            }
            int flightID = results.getInt("FlightID");
            Flight flight = null;
            for (Flight f : flights) {
                if (f.getFlightID() == flightID) {
                    flight = f;
                }
            }

            int seatID = results.getInt("SeatID");

            boolean cancellationInsurance = results.getBoolean("CancellationInsurance");
            LocalDateTime bookingDateTime = results.getTimestamp("BookingDateTime").toLocalDateTime();

            // TODO: Need to replace dummieSeat with actual seat
            Booking booking = new Booking(bookingID, flight, user, seatID, cancellationInsurance, bookingDateTime);
            bookings.add(booking);
        }
        results.close();
        return bookings;
    }

    /*
     * Gets the most current promotion in the database
     */
    public String getCurrentPromotion() throws SQLException {
        Statement myStatement = dbConnect.createStatement();
        results = myStatement.executeQuery("SELECT *\n" +
                "FROM `promotions`\n" +
                "ORDER BY `ValidOn` asc\n" +
                "LIMIT 1;");

        String promo = "NaN";

        while (results.next()) {
            promo = results.getString("Description");
        }
        return promo;

    }

    /*
     * Get Orders from database
     */
    public ArrayList<Order> getOrders(int flightID) throws SQLException {
        ArrayList<Order> orders = new ArrayList<Order>();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Orders WHERE FlightID = " + flightID);
        while (results.next()) {
            int orderID = results.getInt("OrderID");
            String email = results.getString("Email");
            String username = results.getString("Username");
            int flightID2 = results.getInt("FlightID");
            String aircraftModel = results.getString("AircraftModel");
            String departureLocation = results.getString("DepartureLocation");
            String arrivalLocation = results.getString("ArrivalLocation");
            String departureTime = results.getString("DepartureDateTime");
            String arrivalTime = results.getString("ArrivalDateTime");
            String seatClass = results.getString("Class");
            String seatNumber = results.getString("SeatNumber");
            boolean insurance = results.getBoolean("Insurance");
            double totalPrice = results.getDouble("TotalPrice");
            Order order = new Order(orderID, email, username, flightID2, aircraftModel, departureLocation,
                    arrivalLocation,
                    departureTime, arrivalTime, seatClass, seatNumber, insurance, totalPrice);
            orders.add(order);
        }

        results.close();
        return orders;
    }

    public Order getOrder(int OrderID) throws SQLException {
        Statement myStmt = dbConnect.createStatement();
        String id = Integer.toString(OrderID);
        results = myStmt.executeQuery("SELECT * FROM Orders WHERE OrderID = " + id);
        while (results.next()) {
            int orderID = results.getInt("OrderID");
            String email = results.getString("Email");
            String username = results.getString("Username");
            int flightID2 = results.getInt("FlightID");
            String aircraftModel = results.getString("AircraftModel");
            String departureLocation = results.getString("DepartureLocation");
            String arrivalLocation = results.getString("ArrivalLocation");
            String departureTime = results.getString("DepartureDateTime");
            String arrivalTime = results.getString("ArrivalDateTime");
            String seatClass = results.getString("Class");
            String seatNumber = results.getString("SeatNumber");
            boolean insurance = results.getBoolean("Insurance");
            double totalPrice = results.getDouble("TotalPrice");
            Order order = new Order(orderID, email, username, flightID2, aircraftModel, departureLocation,
                    arrivalLocation,
                    departureTime, arrivalTime, seatClass, seatNumber, insurance, totalPrice);

            return order;
        }
        return null;
    }

    /*
     * Get Orders from database given email
     */

    public ArrayList<Order> getOrders(String email) throws SQLException {
        ArrayList<Order> orders = new ArrayList<Order>();
        Statement myStmt = dbConnect.createStatement();
        System.out.println("SELECT * FROM Orders WHERE Email = " + email);
        String query = "SELECT * FROM Orders WHERE Email = ?";
        PreparedStatement pstmt = dbConnect.prepareStatement(query);

        pstmt.setString(1, email);
        results = pstmt.executeQuery();

        while (results.next()) {
            int orderID = results.getInt("OrderID");
            String username = results.getString("Username");
            int flightID2 = results.getInt("FlightID");
            String aircraftModel = results.getString("AircraftModel");
            String departureLocation = results.getString("DepartureLocation");
            String arrivalLocation = results.getString("ArrivalLocation");
            String departureTime = results.getString("DepartureDateTime");
            String arrivalTime = results.getString("ArrivalDateTime");
            String seatClass = results.getString("Class");
            String seatNumber = results.getString("SeatNumber");
            boolean insurance = results.getBoolean("Insurance");
            double totalPrice = results.getDouble("TotalPrice");
            Order order = new Order(orderID, email, username, flightID2, aircraftModel, departureLocation,
                    arrivalLocation,
                    departureTime, arrivalTime, seatClass, seatNumber, insurance, totalPrice);
            orders.add(order);
        }

        results.close();
        return orders;
    }

    /*
     * Remove Order from database with orderID
     */
    public void cancelOrder(int orderID) throws SQLException {
        Statement myStmt = dbConnect.createStatement();
        String sql = "DELETE FROM Orders WHERE OrderID = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, orderID);
            pstmt.executeUpdate();
        }
    }

    public int cancelFlight(int flightID, String username) {
        // returns 0 if successful, 1 if not
        try {
            String updateQuery = "DELETE FROM orders WHERE orderID = ? AND Username = ?";
            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, flightID);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            return 1;
        }
    }
    /*
     * login verification
     */

    public boolean loginCheck(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM users WHERE Name = ? AND PasswordHash = ?";
            preparedStatement = dbConnect.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // In a real app, you should hash the password before comparing

            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // User found with matching username and password
        } finally {
            // Close resources in a finally block
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    /*
     * Register user
     */

    public boolean registerUser(String username, String password, String email, String address) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO users (Name, Email, PasswordHash, Address, CompanionTickets) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = dbConnect.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password); // Password should be hashed + salted
            preparedStatement.setString(4, address);
            preparedStatement.setInt(5, 1);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } finally {
            // Close resources in a finally block
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return false;
    }

    public int addOrder(String email, String username, int flightID, String aircraftModel, String departureLocation,
            String arrivalLocation, Timestamp departureTime, Timestamp arrivalTime, String seatClass,
            String seatNumber, boolean hasInsurance, double totalprice) throws SQLException {

        // SQL query to insert a new order.
        String sql = "INSERT INTO orders (Email, Username, FlightID, AircraftModel, DepartureLocation, ArrivalLocation, "
                +
                "DepartureDateTime, ArrivalDateTime, Class, SeatNumber, Insurance, TotalPrice) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = dbConnect.prepareStatement(sql)) {

            // Set the parameters for the prepared statement.
            statement.setString(1, email);
            statement.setString(2, username);
            statement.setInt(3, flightID);
            statement.setString(4, aircraftModel);
            statement.setString(5, departureLocation);
            statement.setString(6, arrivalLocation);
            statement.setTimestamp(7, departureTime);
            statement.setTimestamp(8, arrivalTime);
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
            PreparedStatement statement2 = dbConnect.prepareStatement(sql2);
            statement2.setString(1, email);
            statement2.setString(2, username);
            statement2.setInt(3, flightID);
            statement2.setString(4, aircraftModel);
            statement2.setString(5, departureLocation);
            statement2.setString(6, arrivalLocation);
            statement2.setTimestamp(7, departureTime);
            statement2.setTimestamp(8, arrivalTime);
            statement2.setString(9, seatClass);
            statement2.setString(10, seatNumber);
            statement2.setBoolean(11, hasInsurance);
            statement2.setDouble(12, totalprice);
            ResultSet result = statement2.executeQuery();
            if (result.next()) {
                return result.getInt("OrderID");
            } else {
                System.out.println("Error getting order ID");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database update error: " + e.getMessage());
            return -1;
        }
    }
    // SQL query to insert a new order.

    public void updateCreditCardInfo(String username, String cardNumber, String expirationDate, String cvv)
            throws SQLException {
        String sql = "UPDATE users SET CreditCardInfo = ?, CreditCardExpiry = ?, CreditCardCVV = ? WHERE Name = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, expirationDate); // Format or convert this as required by your database schema
            pstmt.setString(3, cvv);
            pstmt.setString(4, username);
            pstmt.executeUpdate();
        }
    }

    public String getCreditCardInfo(String username) throws SQLException {
        String query = "SELECT CreditCardInfo FROM Users WHERE Name = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("CreditCardInfo");
            }
        }
        return null;
    }

    public boolean hasCreditCard(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE Name = ? AND CreditCardInfo IS NOT NULL";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    public String getUserType(String username) throws SQLException {
        String sql = "SELECT UserType FROM users WHERE Name = ?";
        try (PreparedStatement statement = dbConnect.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getString("UserType");
            } else {
                System.out.println("Error getting user type");
                return null;
            }
        }
    }
}
