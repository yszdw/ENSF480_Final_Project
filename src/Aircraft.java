package src;

public class Aircraft {
    final String AIRCRAFTID;
    final String AIRCRAFMODEL;
    int numSeats;
    boolean inUse;

    public Aircraft(String aircraftID, String aircraftModel, int numSeats) {
        this.AIRCRAFTID = aircraftID;
        this.AIRCRAFMODEL = aircraftModel;
        this.numSeats = numSeats;
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
