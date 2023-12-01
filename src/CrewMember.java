package src;

public class CrewMember extends User {
    String crewMemberPos;
    int crewMemberID;
    int flightID;
    String name;

    public CrewMember(int crewMemberID, String username, String email, String address, String crewMemberPos) {
        super(crewMemberID, username, email, address);
        this.crewMemberPos = crewMemberPos;
    }

    public CrewMember(int crewMemberID, String name, String crewMemberPos, int flightID) {
        this.crewMemberID = crewMemberID;
        this.crewMemberPos = crewMemberPos;
        this.name = name;
        this.flightID = flightID;

    }

    public CrewMember() {
        super();
        this.crewMemberPos = "";
    }

    public int getCrewID() {
        return crewMemberID;
    }

    public String getCrewName() {
        return name;
    }

    public String getCrewPos() {
        return crewMemberPos;
    }

    public int getFlightID() {
        return flightID;
    }

    /* Methods */

}
