
public class CrewMember extends User{
    String crewMemberPos;

    public CrewMember(int crewMemberID, String username, String email, String address, String crewMemberPos) {
        super(crewMemberID, username, email, address);
        this.crewMemberPos = crewMemberPos;
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

    /* Methods */

}
