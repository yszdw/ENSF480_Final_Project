package src;

import java.sql.*;
import java.util.*;
import java.time.*;

/**
 * Database Management System, handles any interaction between the program and
 * the database
 * DBMS instance - The global instance for our database manager (See Singleton
 * Design Pattern)
 * Connection dbConnect - SQL connection object for initializing connection with
 * database
 * ResultSet results - The results of queries will be in this object
 *
 */

public class DBMS {

    private static DBMS instance;
    private final Connection dbConnect;
    private ResultSet results;

    /**
     * DBMS Constructor
     * Called only when an instance does not yet exist, creates connection to local
     * database
     *
     */
    private DBMS() throws SQLException {
        // the connection info here will need to be changed depending on the user
        dbConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ENSF480", "root", "password");
    }

    /**
     * DBMS Constructor
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
     * Closes database connection, use at end of program
     *
     */
    public void closeConnection() throws SQLException {
        dbConnect.close();
        results.close();
    }

    /**
     * Retrieves the email associated with the given username from the database.
     * 
     * @param username the username of the user
     * @return the email associated with the username, or null if not found
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Retrieves the email associated with the given order ID from the database.
     * 
     * @param orderID the ID of the order
     * @return the email associated with the order ID, or null if not found
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Retrieves the username associated with the given email from the database.
     *
     * @return the username associated with the email, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public ArrayList<Aircraft> getAircrafts() throws SQLException {
        ArrayList<Aircraft> aircrafts = new ArrayList<>();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Aircrafts");

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

    /**
     * Adds a new aircraft to the database.
     * 
     * @param aircraftModel    the model of the aircraft
     * @param numEconomySeats  the number of economy seats in the aircraft
     * @param numComfortSeats  the number of comfort seats in the aircraft
     * @param numBusinessSeats the number of business seats in the aircraft
     * @param economyPrice     the price of economy seats in the aircraft
     * @param businessPrice    the price of business seats in the aircraft
     * @throws SQLException if there is an error executing the SQL statement
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

    /**
     * Removes an aircraft from the database.
     * 
     * @param aircraftID the ID of the aircraft to remove
     * @throws SQLException if there is an error executing the SQL statement
     */
    public void removeAircraft(int aircraftID) throws SQLException {
        String sql = "DELETE FROM aircrafts WHERE AircraftID = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, aircraftID);
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves the aircraft ID associated with the given flight ID from the
     * database.
     * 
     * @param origin the origin for which to retrieve flights
     * @param destination the destination for which to retrieve flights
     * @return the aircraft ID associated with the flight ID, or -1 if not found
     * @throws SQLException if a database access error occurs
     */
    public ArrayList<Flight> getFlights(String origin, String destination) throws SQLException {
        ArrayList<Flight> flights = new ArrayList<>();
        ArrayList<Aircraft> aircrafts = getAircrafts();
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
            assert aircraft != null;
            Flight flight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),
                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());
            flights.add(flight);
        }
        results.close();
        return flights;
    }

    /**
     * Retrieves a list of distinct origins from the Flights table.
     * 
     * @return ArrayList<String> - a list of origins
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Retrieves a list of distinct destinations from the Flights table.
     * 
     * @return ArrayList<String> - a list of destinations
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Retrieves a list of flights scheduled on the specified date.
     * 
     * @param selectedDate the date for which to retrieve flights
     * @return an ArrayList of Flight objects representing the flights scheduled on
     *         the specified date
     * @throws SQLException if there is an error executing the SQL query
     */
    public ArrayList<Flight> getFlights(LocalDate selectedDate) throws SQLException {
        ArrayList<Flight> flights = new ArrayList<>();
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

            assert aircraft != null;
            Flight flight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),

                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());
            flights.add(flight);
        }
        ArrayList<Flight> flightsOnDate = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDepartureDate().equals(selectedDate)) {
                flightsOnDate.add(f);
            }
        }
        return flightsOnDate;
    }

    /**
     * Retrieves a list of flights from the database.
     * 
     * @return An ArrayList of Flight objects representing the flights.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public ArrayList<Flight> getFlights() throws SQLException {
        ArrayList<Flight> flights = new ArrayList<>();
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

            assert aircraft != null;
            Flight flight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),

                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());
            flights.add(flight);
        }
        results.close();
        return flights;
    }

    /**
     * Adds a flight to the database.
     * 
     * @param aircraft      the aircraft for the flight
     * @param origin        the origin of the flight
     * @param destination   the destination of the flight
     * @param departureDate the departure date of the flight
     * @param departureTime the departure time of the flight
     * @param arrivalDate   the arrival date of the flight
     * @param arrivalTime   the arrival time of the flight
     * @throws SQLException if there is an error executing the SQL statement
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

    /**
     * Removes a flight from the database.
     * 
     * @param flightID the ID of the flight to remove
     * @throws SQLException if there is an error executing the SQL statement
     */
    public void removeFlight(int flightID) throws SQLException {
        String sql = "DELETE FROM Flights WHERE FlightID = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, flightID);
            pstmt.executeUpdate();
        }
    }

    /**
     * Updates the location or date/time of a flight based on the given flight ID,
     * indicator, and data.
     * 
     * @param flightID  the ID of the flight to be edited
     * @param indicator the indicator specifying which attribute of the flight to
     *                  update (e.g., "Origin", "Destination", "DepartureDateTime",
     *                  "ArrivalDateTime")
     * @param data      the new value to be set for the specified attribute
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

    /**
     * Retrieves the flight with the given flight ID from the database.
     * 
     * @param flightID the ID of the flight to retrieve
     * @return the Flight object representing the flight with the given flight ID
     * @throws SQLException if there is an error executing the SQL query
     */
    public Flight getFlights(int flightID) throws SQLException {
        Flight returnFlight = null;
        ArrayList<Aircraft> aircrafts = getAircrafts();
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
            assert aircraft != null;
            returnFlight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),
                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());

        }
        results.close();
        return returnFlight;
    }

    /**
     * Updates the location of a flight in the database.
     * 
     * @param flightID       the ID of the flight to update
     * @param locationColumn the column name representing the location in the
     *                       database
     * @param data           the new location data to set
     */
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

    /**
     * Updates the date/time of a flight in the database.
     * 
     * @param flightID       the ID of the flight to update
     * @param dateTimeColumn the column name representing the date/time in the
     *                       database
     * @param dateTime       the new date/time data to set
     */
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

    /**
     * Retrieves a list of registered users from the database.
     * Only users with the UserType 'passenger' are included in the list.
     * 
     * @return An ArrayList of RegisteredUser objects representing the registered
     *         users.
     * @throws SQLException if there is an error executing the SQL query.
     */
    public ArrayList<RegisteredUser> getRegisteredUsers() throws SQLException {
        ArrayList<RegisteredUser> users = new ArrayList<>();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Users WHERE UserType = 'passenger'");
        while (results.next()) {
            int userID = results.getInt("UserID");
            String username = results.getString("Name");
            String address = results.getString("Address");
            String email = results.getString("Email");
            // String userType = results.getString("UserType");
            String creditCardNumber = results.getString("CreditCardInfo");
            int creditCardExpiry = results.getInt("CreditCardExpiry");
            int creditCardCVV = results.getInt("CreditCardCVV");
            int companionTickets = results.getInt("CompanionTickets");
            CreditCard card = new CreditCard(creditCardNumber, username, creditCardExpiry, creditCardCVV);
            RegisteredUser passenger = new RegisteredUser(userID, username, email, address, card,
                    companionTickets);
            users.add(passenger);
        }
        results.close();
        return users;
    }

    /**
     * Adds a user to the database.
     * 
     * @param user the User object representing the user to be added
     */
    public void addUser(User user) {
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

    /**
     * Represents a user in the system.
     * @param username the username of the user
     * @return the User object representing the user
     * @throws SQLException if there is an error executing the SQL query
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
                            CreditCard card = new CreditCard(creditCardNumber, username, creditCardExpiry,
                                    creditCardCVV);
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

    /**
     * Updates the information of a user in the database.
     * 
     * @param user The User object containing the updated information.
     */
    public void updateUser(User user) {
        try {
            String updateQuery = "UPDATE users SET Name = ?, Address = ?, Email = ?, UserType = ?, CreditCardInfo = ?, "
                    +
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

    /**
     * Retrieves the economy price of an aircraft based on its ID.
     * 
     * @param aircraftID the ID of the aircraft
     * @return the economy price of the aircraft
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Retrieves the business price of an aircraft based on its ID.
     * 
     * @param aircraftID the ID of the aircraft
     * @return the business price of the aircraft
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Retrieves a list of crew members assigned to a specific flight.
     * 
     * @param flight the flight ID for which to retrieve crew members
     * @return an ArrayList of CrewMember objects representing the crew members
     *         assigned to the flight
     * @throws SQLException if there is an error executing the SQL query
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
                    int flightID = results.getInt("FlightID");

                    CrewMember crewMember = new CrewMember(crewID, crewName, position, flightID);
                    crewMembers.add(crewMember);
                }
            }
        }

        return crewMembers;
    }

    /**
     * Updates the flight assignment for a crew member.
     * 
     * @param crewID the ID of the crew member
     * @param flight the ID of the flight to be assigned
     * @throws SQLException if a database access error occurs
     */
    public void updateCrew(int crewID, int flight) throws SQLException {
        String sql = "UPDATE crews SET FlightID = ? WHERE CrewID = ?";

        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, flight);
            pstmt.setInt(2, crewID);
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves the current promotion from the database.
     *
     * @return The description of the current promotion as a String.
     * @throws SQLException if there is an error executing the SQL query.
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

    /**
     * Retrieves a list of orders for a specific flight ID from the database.
     * 
     * @param flightID the ID of the flight
     * @return an ArrayList of Order objects representing the orders for the
     *         specified flight
     * @throws SQLException if there is an error executing the SQL query
     */
    public ArrayList<Order> getOrders(int flightID) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
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
            Order order = new Order(orderID, email, username, flightID2, departureLocation,
                    arrivalLocation,
                    departureTime, arrivalTime, seatClass, seatNumber, insurance, totalPrice);
            orders.add(order);
        }

        results.close();
        return orders;
    }

    /**
     * Retrieves an Order object from the database based on the given OrderID.
     * 
     * @param OrderID the ID of the order to retrieve
     * @return the Order object corresponding to the given OrderID, or null if no
     *         order is found
     * @throws SQLException if there is an error executing the SQL query
     */
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

            return new Order(orderID, email, username, flightID2, departureLocation,
                    arrivalLocation,
                    departureTime, arrivalTime, seatClass, seatNumber, insurance, totalPrice);
        }
        return null;
    }

    /**
     * Retrieves a list of orders associated with the given email.
     * 
     * @param email the email address of the user
     * @return an ArrayList of Order objects representing the orders
     * @throws SQLException if there is an error executing the SQL query
     */
    public ArrayList<Order> getOrders(String email) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
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
            Order order = new Order(orderID, email, username, flightID2, departureLocation,
                    arrivalLocation,
                    departureTime, arrivalTime, seatClass, seatNumber, insurance, totalPrice);
            orders.add(order);
        }

        results.close();
        return orders;
    }

    /**
     * Cancels an order by deleting it from the database.
     * 
     * @param orderID the ID of the order to be canceled
     * @throws SQLException if a database access error occurs
     */
    public void cancelOrder(int orderID) throws SQLException {
        String sql = "DELETE FROM Orders WHERE OrderID = ?";
        try (PreparedStatement pstmt = dbConnect.prepareStatement(sql)) {
            pstmt.setInt(1, orderID);
            pstmt.executeUpdate();
        }
    }

    /**
     * Checks if the provided username and password match a user in the database.
     * 
     * @param username the username to check
     * @param password the password to check
     * @return true if the username and password match a user in the database, false
     *         otherwise
     * @throws SQLException if an error occurs while accessing the database
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
            // Close resources in a final block
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    /**
     * Registers a new user in the database.
     *
     * @param username the username of the user
     * @param password the password of the user (should be hashed and salted)
     * @param email    the email address of the user
     * @param address  the address of the user
     * @return true if the user is successfully registered, false otherwise
     * @throws SQLException if a database access error occurs
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
            // Close resources in a final block
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return false;
    }

    /**
     * Adds a new order to the database.
     *
     * @param email             the email of the customer placing the order
     * @param username          the username of the customer placing the order
     * @param flightID          the ID of the flight associated with the order
     * @param aircraftModel     the model of the aircraft for the flight
     * @param departureLocation the departure location of the flight
     * @param arrivalLocation   the arrival location of the flight
     * @param departureTime     the departure time of the flight
     * @param arrivalTime       the arrival time of the flight
     * @param seatClass         the class of the seat for the order
     * @param seatNumber        the seat number for the order
     * @param hasInsurance      indicates whether the order has insurance or not
     * @param totalPrice        the total price of the order
     * @return the ID of the inserted order if successful, -1 otherwise
     * @throws SQLException if there is an error executing the SQL statements
     */
    public int addOrder(String email, String username, int flightID, String aircraftModel, String departureLocation,
            String arrivalLocation, Timestamp departureTime, Timestamp arrivalTime, String seatClass,
            String seatNumber, boolean hasInsurance, double totalPrice) throws SQLException {

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
            statement.setDouble(12, totalPrice);

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
            statement2.setDouble(12, totalPrice);
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

    /**
     * Updates the credit card information for a user in the database.
     * 
     * @param username       the username of the user
     * @param cardNumber     the new credit card number
     * @param expirationDate the new credit card expiration date
     * @param cvv            the new credit card CVV
     * @throws SQLException if there is an error executing the SQL statement
     */
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

    /**
     * Retrieves the credit card information for a given username from the database.
     * 
     * @param username the username of the user
     * @return the credit card information as a String, or null if the user does not
     *         exist
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Checks if a user has a credit card associated with their account.
     * 
     * @param username the username of the user
     * @return true if the user has a credit card, false otherwise
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Retrieves the user type for a given username from the database.
     * 
     * @param username the username of the user
     * @return the user type as a String, or null if the user type cannot be
     *         retrieved
     * @throws SQLException if there is an error executing the SQL query
     */
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

    /**
     * Retrieves the Aircraft for a given aircraftID from the database.
     * 
     * @param aircraftID the username of the user
     * @return the Aircraft object corresponding to the given aircraftID, or null if
     * @throws SQLException if there is an error executing the SQL query
     */
    public Aircraft getAircraftbyID(int aircraftID) throws SQLException {
        String sql = "SELECT * FROM aircrafts WHERE AircraftID = ?";
        try (PreparedStatement statement = dbConnect.prepareStatement(sql)) {
            statement.setInt(1, aircraftID);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String aircraftModel = result.getString("Model");
                int numEconomySeats = result.getInt("Ordinary");
                int numComfortSeats = result.getInt("Comfort");
                int numBusinessSeats = result.getInt("Business");
                double economyPrice = result.getDouble("EconomyPrice");
                double businessPrice = result.getDouble("BusinessPrice");
                return new Aircraft(aircraftID, aircraftModel, numEconomySeats, numComfortSeats, numBusinessSeats,
                        economyPrice, businessPrice);
            } else {
                System.out.println("Error getting aircraft");
                return null;
            }
        }
    }
}
