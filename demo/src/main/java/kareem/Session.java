package kareem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Session {
    private String sessionID;
    private String name;
    private Date date;
    private String time;
    private String room;
    private List<Attendee> attendees;
    private Speaker speaker;

    public Session(String sessionID, String name, Date date, String time, String room) {
        this.sessionID = sessionID;
        this.name = name;
        this.date = date;
        this.time = time;
        this.room = room;
        this.attendees = new ArrayList<>();
    }

    public void registerAttendance(Attendee attendee) {
        attendees.add(attendee);
    }

    public void unregisterAttendance(Attendee attendee) {
        attendees.remove(attendee);
    }

    public String getDetails() {
        // Return session details
        return name + " at " + time + " in room " + room +"on"+ date;
    }

    public int getAttendeeCount() {
        // Return the count of attendees
        return attendees.size();
    }

    public String getSessionID() {
        return sessionID;
    }
    public void setSpeaker(Speaker speakerID) {
        this.speaker = speakerID;
    }
    public Speaker getSpeaker() {
        return speaker;
    }
}

