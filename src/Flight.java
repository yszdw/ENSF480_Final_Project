package src;

import java.util.HashMap;
import java.time.*;
import java.util.ArrayList;

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

        this.crew = new ArrayList<>();

    }

    /* Getters */

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
