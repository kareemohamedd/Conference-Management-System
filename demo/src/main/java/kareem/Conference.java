package kareem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conference {
    private String name;
    private Date startDate;
    private Date endDate;
    private List<Session> sessions;
    private List<Attendee> attendees;

    public Conference(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sessions = new ArrayList<>();
        this.attendees = new ArrayList<>();
    }

    public Attendee registerAttendee(String name, String email) {
        Attendee attendee = new Attendee(name, email);
        attendees.add(attendee);
        return attendee;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public List<Session> getSessionDetails() {
        return sessions;
    }

    public List<Attendee> getAttendeeList() {
        return attendees;
    }

    public String getName() {
        return name;
    }
}

