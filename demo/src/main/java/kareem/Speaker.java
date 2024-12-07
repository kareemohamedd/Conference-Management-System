package kareem;

import java.util.ArrayList;
import java.util.List;

public class Speaker {
    private String speakerID;
    private String name;
    private String bio;
    private List<Session> associatedSessions;

    public Speaker(String speakerID, String name, String bio) {
        this.speakerID = speakerID;
        this.name = name;
        this.bio = bio;
        this.associatedSessions = new ArrayList<>();
    }

    public List<Session> getSessionList() {
        // Return a list of associated sessions
        return associatedSessions;
    }

    public void addSession(Session session) {
        associatedSessions.add(session);
    }

    public void removeSession(Session session) {
        associatedSessions.remove(session);
    }

    public String getName() {
        return name;
    }
}

