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
        dbConnect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ENSF480", "root", "AbXy219!");
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

    /*
     * getAircraft list from database
     */
    public ArrayList<Aircraft> getAircrafts() throws SQLException {
        ArrayList<Aircraft> aircrafts = new ArrayList<Aircraft>();
        Statement myStmt = dbConnect.createStatement();
        results = myStmt.executeQuery("SELECT * FROM Aircrafts");
        while (results.next()) {
            int aircraftID = results.getInt("AircraftID");
            String aircraftModel = results.getString("Model");
            int numEconomySeats = results.getInt("Ordinary");
            int numComfortSeats = results.getInt("Comfort");
            int numBusinessSeats = results.getInt("Business");
            Aircraft aircraft = new Aircraft(aircraftID, aircraftModel, numEconomySeats, numComfortSeats,
                    numBusinessSeats);
            aircrafts.add(aircraft);
        }
        results.close();
        return aircrafts;
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
            int creditCardNumber = results.getInt("CreditCardNumber");
            int creditCardExpiry = results.getInt("CreditCardExpiry");
            int creditCardCVV = results.getInt("CreditCardCVV");
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
                    CreditCard card = new CreditCard(creditCardNumber, username,creditCardExpiry, creditCardCVV);
                    RegisteredUser passenger = new RegisteredUser(userID, username, email, address, card);
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
                "CreditCardCVV) VALUES (?, ?, ?, ?, ?, ?, ?)";

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
                preparedStatement.setInt(5, card.getCardNumber());
                preparedStatement.setInt(6, card.getExpiryDate());
                preparedStatement.setInt(7, card.getCVV());
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

    public static void main(String args[]) throws SQLException {

        DBMS connect = getDBMS();

        // This is a list of all flight information in the database
        // - can use all flight getter methods for flight info
        ArrayList<Flight> flightList = connect.getFlights();
        // This is a list of all user information in the database
        // - can use all user getter methods for user info
        ArrayList<User> userList = connect.getUsers();

        ArrayList<Booking> bookingList = connect.getBookings();
        for (Booking booking : bookingList) {
            System.out.println(booking.getBookingID());
            int flightID = booking.getFlight().getFlightID();
            for (Flight flight : flightList) {
                if (flight.getFlightID() == flightID) {
                    System.out.println(flight.getDepartureLocation());
                    System.out.println(flight.getArrivalLocation());
                    System.out.println(flight.getDepartureDate());
                    System.out.println(flight.getArrivalDate());
                    System.out.println(flight.getDepartureTime());
                    System.out.println(flight.getArrivalTime());
                }
            }
            System.out.println(booking.getUser().getUsername());
            System.out.println(booking.getBookedSeats());
            System.out.println(booking.getCancellationInsurance());
            System.out.println(booking.getBookingDateTime());
        }

        // for (Flight flight : flightList) {
        // System.out.println(flight.getAircraft().getAircraftModel());
        // System.out.println(flight.getAircraft().getAircraftID());
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

        // User testObject = new User("test", "test st", "test@email", "test",
        // "passenger");

        User testObject = userList.get(0);

        // connect.addUser(testObject);

        // Upon selecting a flight, display seat availability

        // create a new booking
        // testFlight.bookSeat(new Seat("1", "economy", 80), testObject);
        // System.out.println("Available seats: ");
        // for (Seat seat : testFlight.getSeats().values()) {
        // if (seat.getIsAvailable()) {
        // System.out.println(seat.getSeatNumber());
        // }
        // }
        // connect.bookFlight(testFlight.getFlightID(), testObject);

        /*
         * TODO: with login, customer can view booked flights and cancel a flight
         * do they need to login? or they can view by bookingID?
         */

        connect.closeConnection();
    }
}