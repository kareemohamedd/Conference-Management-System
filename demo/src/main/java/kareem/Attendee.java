package kareem;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;

public class Attendee extends Person {
    private String attendeeID;
    private List<String> schedule;
    private TextArea logArea;
    private boolean hasCertificate ;

    public Attendee(String name, String email, TextArea logArea) {
        super(name, email);
        this.schedule = new ArrayList<>();
        this.logArea = logArea;
        this.hasCertificate = false;
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    public void provideFeedback(Feedback feedback) {
        log("Feedback provided: " + feedback.getFeedbackSummary());
    }

    public void createSchedule(String session) {
        schedule.add(session);
        log("Session added to schedule: " + session);
    }

    public Certificate getCertificate() {
        return new Certificate("Cert123", this);
    }

    public List<String> getSchedule() {
        return schedule;
    }
    public void addCertificate(){
        hasCertificate = true;
    }
    public boolean hasCertificate(){
        return hasCertificate;
    }
}

