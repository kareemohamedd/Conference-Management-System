package kareem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TextArea;

public class Conference {
    private String name;
    private Date startDate;
    private Date endDate;
    private List<Session> sessions;
    private List<Attendee> attendees;
    private List<Speaker> speakers;
    private TextArea logArea;

    public Conference(String name, Date startDate, Date endDate, TextArea logArea) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessions = new ArrayList<>();
        this.attendees = new ArrayList<>();
        this.speakers = new ArrayList<>();
        this.logArea = logArea;
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    public Attendee registerAttendee(String name, String email) {
        Attendee attendee = new Attendee(name, email, logArea);
        attendees.add(attendee);
        log("Attendee registered: " + attendee.getName());
        return attendee;
    }
    public void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
        log("Attendee removed: " + attendee.getName());
    }

    public void addSession(Session session) {
        sessions.add(session);
        log("Session added: " + session.getDetails());
    }

    public void addSpeaker(Speaker speaker) {
        speakers.add(speaker);
        log("Speaker added: " + speaker.getName());
    }
    public String viewDetails(){
        return "Conference Name: "+this.name+"\n"+"Start Date: "+this.startDate+"\n"+"End Date: "+this.endDate;
    }

    public List<Session> getSessionDetails() {
        return sessions;
    }

    public List<Attendee> getAttendeeList() {
        return attendees;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }
    public List<String> getSpeakerNames() {
        List<String> speakerNames = new ArrayList<>();
        for (Speaker speaker : speakers) {
            speakerNames.add(speaker.getName()); // Assuming getSpeaker() returns the name
        }
        return speakerNames;
    }

    public String getName() {
        return name;
    }
}

