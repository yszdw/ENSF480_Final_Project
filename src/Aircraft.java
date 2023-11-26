
public class Aircraft {
    final int AIRCRAFTID;
    final String AIRCRAFMODEL;
    int numEconomySeats;
    int numComfortSeats;
    int numBusinessSeats;
    private double economyPrice;
    private double businessPrice;

    boolean inUse;

    public Aircraft(int aircraftID, String aircraftModel, int numEconomySeats, int numComfortSeats,
            int numBusinessSeats) {
        this.AIRCRAFTID = aircraftID;
        this.AIRCRAFMODEL = aircraftModel;
        this.numEconomySeats = numEconomySeats;
        this.numComfortSeats = numComfortSeats;
        this.numBusinessSeats = numBusinessSeats;
        this.inUse = false;
    }

    /* Getters and Setters */
    public int getAircraftID() {
        return AIRCRAFTID;
    }

    public String getAircraftModel() {
        return AIRCRAFMODEL;
    }

    public boolean getInUse() {
        return inUse;
    }

    /* Methods */
    public void setInUse() {
        this.inUse = true;
    }

    public void setNotInUse() {
        this.inUse = false;
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

    // New setters for the prices (if necessary)
    public void setEconomyPrice(double price) {
        this.economyPrice = price;
    }

    public void setBusinessPrice(double price) {
        this.businessPrice = price;
    }

    // In Aircraft.java
    // Assuming that your Aircraft class already has the getters and setters,
    // You may want to add methods to retrieve seat information.
    public int getNumEconomySeats() {
        return this.numEconomySeats;
    }

    public int getNumBusinessSeats() {
        return this.numBusinessSeats;
    }

}
