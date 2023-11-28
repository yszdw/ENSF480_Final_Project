package src;

public class CrewMember extends User {
    String crewMemberPos;
    int crewMemberID;
    String flightID;
    String name;

    public CrewMember(int crewMemberID, String username, String email, String address, String crewMemberPos) {
        super(crewMemberID, username, email, address);
        this.crewMemberPos = crewMemberPos;
    }

    public CrewMember(int crewMemberID, String name, String crewMemberPos) {
        this.crewMemberID = crewMemberID;
        this.crewMemberPos = crewMemberPos;
        this.name = name;

    }

    public CrewMember() {
        super();
        this.crewMemberPos = "";
    }

    /* Getters and Setters */
    public void setCrewMemberPos(String crewMemberPos) {
        this.crewMemberPos = crewMemberPos;
    }

    public String getCrewMemberPos() {
        return crewMemberPos;
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

    /* Methods */

}
