package kareem;

import java.util.ArrayList;
import java.util.List;

public class Attendee {
    private String attendeeID;
    private String name;
    private String email;
    private List<Session> schedule;

    public Attendee(String name, String email) {
        this.name = name;
        this.email = email;
        this.schedule = new ArrayList<>();
    }

    public void provideFeedback(Feedback feedback) {
        System.out.println("Feedback provided: " + feedback.getFeedbackSummary());
    }

    public void createSchedule(Session session) {
        schedule.add(session);
        System.out.println("Session added to schedule: " + session.getDetails());
    }

    public Certificate getCertificate() {
        return new Certificate("Cert123", this);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Session> getSchedule() {
        return schedule;
    }
}

