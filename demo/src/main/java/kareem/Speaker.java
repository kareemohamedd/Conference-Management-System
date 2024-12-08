package kareem;

import java.util.ArrayList;
import java.util.List;

public class Speaker extends Person {
    private String speakerID;
    private String bio;
    private List<Session> associatedSessions;

    public Speaker(String speakerID, String name, String bio) {
        super(name, null); // Assuming email is not required for Speaker
        this.speakerID = speakerID;
        this.bio = bio;
        this.associatedSessions = new ArrayList<>();
    }

    public List<Session> getSessions() {
        return associatedSessions;
    }

    public void addSession(Session session) {
        associatedSessions.add(session);
    }

    public void removeSession(Session session) {
        associatedSessions.remove(session);
    }

    public String getBio() {
        return bio;
    }

    public String getSpeakerID() {
        return speakerID;
    }
}
