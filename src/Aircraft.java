package src;

public class Aircraft {
    final String AIRCRAFTID;
    final String AIRCRAFMODEL;
    int numEconomySeats;
    int numComfortSeats;
    int numBusinessSeats;

    boolean inUse;

    public Aircraft(String aircraftID, String aircraftModel, int numEconomySeats, int numComfortSeats,
                    int numBusinessSeats) {
        this.AIRCRAFTID = aircraftID;
        this.AIRCRAFMODEL = aircraftModel;
        this.numEconomySeats = numEconomySeats;
        this.numComfortSeats = numComfortSeats;
        this.numBusinessSeats = numBusinessSeats;
        this.inUse = false;
    }

    /* Getters and Setters */
    public String getAircraftID() {
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
}
