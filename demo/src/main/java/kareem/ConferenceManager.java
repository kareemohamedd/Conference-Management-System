package kareem;
import java.util.List;

public class ConferenceManager {
    private String managerID;
    private String name;
    private String email;


    public ConferenceManager(String managerID, String name, String email) {
        this.managerID = managerID;
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setupConference(Conference conference) {
        System.out.println("Setting up conference: " + conference.getName());
    }

    public void manageSessions(Session session) {
        System.out.println("Managing session: " + session.getDetails());
    }

    public void manageSpeakers(Speaker speaker) {
        System.out.println("Managing speaker: " + speaker.getName());
    }

    public List<String> generateReports() {
        return List.of("Report1", "Report2");
    }
}
