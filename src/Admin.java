package src;

import java.util.ArrayList;
import java.util.Date;

public class Admin extends User {

    public Admin(String username, String email, String address, String creditCard) {
        super(username, email, address, creditCard, "admin");
    }

    /* Methods */

    // This method is used to return a list of all flights from the database
    public ArrayList<Flight> getFlights() {
        // TODO: Get flights from database
        return null;
    }

    public Flight flightExists(String flightID) {
        Flight flight = null;
        for (Flight f : getFlights()) {
            if (f.getFlightID().equals(flightID)) {
                flight = f;
            }
        }
        return flight;
    }

    // This method is used to add a flight to the database
    public void addFlight(Aircraft aircraft, String flightID, int departureTime, int arrivalTime,
                          String departureLocation, String arrivalLocation, Date departureDate, Date arrivalDate) {
        Flight flight = new Flight(aircraft, flightID, departureTime, arrivalTime, departureLocation, arrivalLocation,
                                   departureDate, arrivalDate);
        // TODO: Add flight to database
    }

    // This method is used to remove a flight from the database
    public void removeFlight(String flightID) {
        // TODO: Remove flight from database
    }

    // This method is used to add a crew member to the database
    public void addCrewMember(String username, String email, String address, String creditCard, String crewMemberID,
                              String crewMemberPos) {
        CrewMember crewMember = new CrewMember(username, email, address, creditCard, crewMemberID, crewMemberPos);
        // TODO: Add crew member to database. Call overloaded method
    }

    // This method is used to return a list of all crew members from a specific flight
    public ArrayList<CrewMember> getCrewMembers(String flightID) {
        // TODO: Get crew members from database
        return null;
    }

    public void addCrewMember(CrewMember crewMember) {
        // TODO: Add crew member to database
    }

    // This method is used to remove a crew member from the database
    public void removeCrewMember(String username) {
        // TODO: Remove crew member from database
    }

    // This method is used to return a list of all aircrafts from the database
    public ArrayList<Aircraft> getAircrafts() {
        // TODO: Get aircrafts from database
        return null;
    }

    // This method is used to add an aircraft to the database
    public void addAircraft(String aircraftID, String aircraftModel, int numEconomySeats, int numComfortSeats,
                            int numBusinessSeats ) {
        Aircraft aircraft = new Aircraft(aircraftID, aircraftModel, numEconomySeats, numComfortSeats, numBusinessSeats);
        // TODO: Add aircraft to database
    }

    // This method is used to remove an aircraft from the database
    public void removeAircraft(String aircraftID) {
        // TODO: Remove aircraft from database
    }

    // this method is used to modify flight information
    public void modifyFlight(String flightID, String toModify) {
        Flight flight = flightExists(flightID);
        if (flight == null) {
            System.out.println("Flight does not exist");
            return;
        }

        // These need to be modified on the database and then update the GUI
        // probably better to breakup this info into separate methods
        switch (toModify) {
            case "aircraft":
                // TODO: Modify aircraft
                break;
            case "departureTime":
                // TODO: Modify departure time
                break;
            case "arrivalTime":
                // TODO: Modify arrival time
                break;
            case "departureLocation":
                // TODO: Modify departure location
                break;
            case "arrivalLocation":
                // TODO: Modify arrival location
                break;
            case "departureDate":
                // TODO: Modify departure date
                break;
            case "arrivalDate":
                // TODO: Modify arrival date
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    // This method returns a list of all users from the database if the userType is "passenger" in the database
    public ArrayList<User> getPassengers() {
        // TODO: Get passengers from database
        return null;
    }
}