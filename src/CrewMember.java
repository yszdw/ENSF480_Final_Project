package src;

public class CrewMember extends User{
    String crewMemberID;
    String crewMemberPos;

    public CrewMember(String username, String email, String address, String creditCard, String crewMemberID,
                      String crewMemberPos) {
        //super(username, email, address, creditCard, "crew");
        this.crewMemberID = crewMemberID;
        this.crewMemberPos = crewMemberPos;
    }

    public CrewMember() {
        super();
        this.crewMemberID = "";
        this.crewMemberPos = "";
    }

    /* Getters and Setters */
    public void setCrewMemberID(String crewMemberID) {
        this.crewMemberID = crewMemberID;
    }
    public void setCrewMemberPos(String crewMemberPos) {
        this.crewMemberPos = crewMemberPos;
    }
    public String getCrewMemberID() {
        return crewMemberID;
    }
    public String getCrewMemberPos() {
        return crewMemberPos;
    }

    /* Methods */

}
