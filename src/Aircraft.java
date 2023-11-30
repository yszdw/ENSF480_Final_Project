package src;

/**
 * The Aircraft class represents an aircraft used in the airline system.
 * It stores information about the aircraft, such as its ID, model, number of
 * seats, and prices.
 * The class provides methods to access and modify the aircraft's information.
 */
public class Aircraft {
    final int AIRCRAFTID;
    final String AIRCRAFMODEL;
    int numEconomySeats;
    int numComfortSeats;
    int numBusinessSeats;
    private double economyPrice;
    private double businessPrice;

    /**
     * Represents an aircraft with its properties and methods.
     */
    public Aircraft(int aircraftID, String aircraftModel, int numEconomySeats, int numComfortSeats,
            int numBusinessSeats, double economyPrice, double businessPrice) {
        this.AIRCRAFTID = aircraftID;
        this.AIRCRAFMODEL = aircraftModel;
        this.numEconomySeats = numEconomySeats;
        this.numComfortSeats = numComfortSeats;
        this.numBusinessSeats = numBusinessSeats;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
    }

    /* Getters */
    public int getAircraftID() {
        return AIRCRAFTID;
    }

    public String getAircraftModel() {
        return AIRCRAFMODEL;
    }

    // New getters for the prices
    public double getEconomyPrice() {
        // This should actually retrieve the price from the database or object fields
        return economyPrice;
    }

    public double getBusinessPrice() {
        // This should actually retrieve the price from the database or object fields
        return businessPrice;
    }

    // Assuming that your Aircraft class already has the getters and setters,
    // You may want to add methods to retrieve seat information.
    public int getNumEconomySeats() {
        return this.numEconomySeats;
    }

    public int getNumBusinessSeats() {
        return this.numBusinessSeats;
    }

    public int getNumComfortSeats() {
        return this.numComfortSeats;
    }

}
