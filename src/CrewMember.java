
public class CrewMember {
    int crewMemberID;
    String crewName;
    String crewMemberPos;

    public CrewMember(int crewMemberID,
            String crewName, String crewMemberPos) {
        // super(username, email, address, creditCard, "crew");
        this.crewMemberID = crewMemberID;
        this.crewName = crewName;
        this.crewMemberPos = crewMemberPos;
    }

    /* Getters and Setters */
    public int getCrewID() {
        return crewMemberID;
    }

    public String getCrewName() {
        return crewName;
    }

    public String getCrewPos() {
        return crewMemberPos;
    }

    /* Methods */

}
