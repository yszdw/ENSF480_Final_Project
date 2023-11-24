package src;

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
     * Called only when an instance does not yet exist, creates onnection to local
     * database
     *
     */
    private DBMS() throws SQLException {
        // the connection info here will need to be changed depending on the user
        dbConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ENSF480", "root", "password");
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

    /**
     * A sample test to verify connection
     */
    public void test_SQL() throws SQLException {

        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Users");

        while (results.next()) {
            System.out.println(results.getString("Address"));
        }

    }

    public ArrayList<Aircraft> getAircrafts() throws SQLException {
        ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Aircrafts");
        while (results.next()) {
            String aircraftID = results.getString("AircraftID");
            String aircraftModel = results.getString("AircraftModel");
            int numEconomySeats = results.getInt("NumEconomySeats");
            int numComfortSeats = results.getInt("NumComfortSeats");
            int numBusinessSeats = results.getInt("NumBusinessSeats");
            Aircraft aircraft = new Aircraft(aircraftID, aircraftModel, numEconomySeats, numComfortSeats,
                    numBusinessSeats);
            aircrafts.add(aircraft);
        }
        return aircrafts;
    }

    /*
     * getFlight list
     */
    public ArrayList<Flight> getFlights() throws SQLException {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Flights");
        while (results.next()) {
            LocalDateTime departureDateTime = results.getTimestamp("DepartureDateTime").toLocalDateTime();
            LocalDateTime arrivalDateTime = results.getTimestamp("ArrivalDateTime").toLocalDateTime();
            Aircraft aircraft = new Aircraft("AA123", "Boeing780", 100, 50, 20);
            Flight flight = new Flight(aircraft, results.getInt("FlightID"), results.getString("Origin"),
                    results.getString("Destination"), departureDateTime.toLocalDate(),

                    departureDateTime.toLocalTime(), arrivalDateTime.toLocalDate(), arrivalDateTime.toLocalTime());
            flights.add(flight);
        }
        return flights;
    }

    /*
     * Books a flight and insert into database
     */

    public void bookFlight(int userID, int flightID, int seatID, LocalDateTime DateTime) throws SQLException {
        Statement myStmt = dbConnect.createStatement();
        String sql = "INSERT INTO Bookings (UserID, FlightID, SeatID, CancellationInsurance, BookingDateTime) VALUES ("
                + userID + flightID + seatID + "0" + DateTime + ")";
        myStmt.executeUpdate(sql);
    }

    /*
     * add user to database
     */
    public void addUser(User user) throws SQLException {
        // SQL query for insertion using a prepared statement
        String insertQuery = "INSERT INTO Users (Name, Address, Email, UserType, MembershipStatus, CreditCardInfo) VALUES (?, ?, ?, ?, ?, ?)";

        // Create a prepared statement
        try (PreparedStatement preparedStatement = dbConnect.prepareStatement(insertQuery)) {
            // Set values for the placeholders in the query
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getAddress());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUserType());
            preparedStatement.setBoolean(5, user.getIsMember());
            preparedStatement.setString(6, user.getCreditCard());

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
     * getSeating list
     */
    // public ArrayList<Seat> getSeats() throws SQLException {
    // ArrayList<Seat> seats = new ArrayList<Seat>();
    // Statement myStmt = dbConnect.createStatement();
    // results = myStmt.executeQuery("SELECT * FROM Seats");
    // while (results.next()) {
    // String seatID = results.getString("SeatID");
    // String seatType = results.getString("SeatType");
    // int seatPrice = results.getInt("SeatPrice");
    // Seat seat = new Seat(seatID, seatType, seatPrice);
    // seats.add(seat);
    // }
    // return seats;
    // }

    public static void main(String args[]) throws SQLException {

        DBMS connect = getDBMS();

        // This is a list of all flight information in the database
        ArrayList<Flight> flightList = connect.getFlights();
        // for (Flight flight : flightList) {
        // System.out.println("Departure: " + flight.getDepartureLocation());
        // System.out.println("Arrival: " + flight.getArrivalLocation());
        // System.out.println("Departure Date: " + flight.getDepartureDate());
        // System.out.println("Departure Time: " + flight.getDepartureTime());
        // System.out.println("Arrival Date: " + flight.getArrivalDate());
        // System.out.println("Arrival Time: " + flight.getArrivalTime());
        // }

        /*
         * TODO: with no login, customer can browse flights and book a flight
         * can search for flights by date, origin, destination
         * - use flight class getter methods to get info from flightList
         */

        // selected a flight
        Flight testFlight = flightList.get(0);

        /*
         * TODO: prompt user to login or create an account
         * this is a test object
         */

        User testObject = new User("test", "test st", "test@email", "test", "passenger");
        connect.addUser(testObject);

        // Upon selecting a flight, display seat availability

        // System.out.println("Available seats: ");
        // for (Seat seat : testFlight.getSeats().values()) {
        // if (seat.getIsAvailable()) {
        // System.out.println(seat.getSeatNumber());
        // }
        // }

        // create a new booking
        testFlight.bookSeat(new Seat("1", "economy", 100), testObject);
        System.out.println("Available seats: ");
        for (Seat seat : testFlight.getSeats().values()) {
            if (seat.getIsAvailable()) {
                System.out.println(seat.getSeatNumber());
            }
        }
        // connect.bookFlight(testFlight.getFlightID(), testObject);

        /*
         * TODO: with login, customer can view booked flights and cancel a flight
         * do they need to login? or they can view by bookingID?
         */

        connect.closeConnection();
    }
}