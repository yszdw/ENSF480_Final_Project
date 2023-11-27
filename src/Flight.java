
import java.util.HashMap;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;

public class Flight {
    // HashMap of seats, key is seat number, value is Seat object
    HashMap<String, Seat> seats;
    Aircraft aircraft;
    // Assuming flight ID is in the format AA###, where A is a letter and # is a
    // number
    int flightID;
    // ArrayList of crew members
    ArrayList<CrewMember> crew;
    // Departure and arrival times are in military time
    LocalTime departureTime;
    LocalTime arrivalTime;
    String departureLocation;
    String arrivalLocation;
    // Departure and arrival dates are in the format YYYY-MM-DD
    LocalDate departureDate;
    LocalDate arrivalDate;

    public Flight(Aircraft aircraft, int flightID, String departureLocation,
            String arrivalLocation, LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate,
            LocalTime arrivalTime) {
        this.aircraft = aircraft;
        this.flightID = flightID;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.seats = new HashMap<>();
        // Create seats for the flight
        for (int i = 0; i < aircraft.numEconomySeats; i++) {
            Seat temp = new Seat(Integer.toString(i), "economy", 100);
            this.seats.put(temp.getSeatNumber(), temp);
        }
        for (int i = 0; i < aircraft.numComfortSeats; i++) {
            Seat temp = new Seat(Integer.toString(i), "Comfort", 145);
            this.seats.put(temp.getSeatNumber(), temp);
        }
        for (int i = 0; i < aircraft.numBusinessSeats; i++) {
            Seat temp = new Seat(Integer.toString(i), "business", 250);
            this.seats.put(temp.getSeatNumber(), temp);
        }

        this.crew = new ArrayList<CrewMember>();
    }

    /* Methods */

    public void bookSeat(Seat seat, User username) {
        if (seat.getIsAvailable() && seats.containsKey(seat.getSeatNumber())) {
            seat.setPassenger(username);
            seat.setAvailablibility(false);
        }
        if (!seats.containsKey(seat.getSeatNumber())) {
            System.out.println("Seat does not exist");
        }
    }

    // This method is used to cancel a seat
    public void cancelSeat(String username) {
        for (Seat seat : seats.values()) {
            if (seat.getPassenger().getUsername().equals(username)) {
                seat.removePassenger();
            }
        }
    }

    /* Getters and Setters */
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public void setArrivalLocation(String arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public int getFlightID() {
        return flightID;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public HashMap<String, Seat> getSeats() {
        return seats;
    }

    public ArrayList<CrewMember> getCrew() {
        return crew;
    }

    // Static method to find a Flight by its ID
    public static Flight getFlightByID(ArrayList<Flight> flights, int flightID) {
        for (Flight flight : flights) {
            if (flight.getFlightID() == flightID) {
                return flight;
            }
        }
        return null; // Or handle the case where the flight isn't found
    }

}
