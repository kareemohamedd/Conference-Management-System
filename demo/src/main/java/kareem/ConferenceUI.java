package kareem;

import java.util.List;
import javafx.scene.control.TextArea;

public class ConferenceUI {
    private TextArea logArea;

    public ConferenceUI(TextArea logArea) {
        this.logArea = logArea;
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    public void displaySessions(List<Session> sessions) {
        for (Session session : sessions) {
            log(session.getDetails());
        }
    }

    public void showAttendance(Attendee attendee) {
        log("Attendance for " + attendee.getName() + ": " + attendee.getSchedule().size() + " sessions");
    }

    public void collectFeedback(Attendee attendee) {
        log("Collecting feedback from " + attendee.getName());
    }

    public void updateSchedule(Attendee attendee) {
        log("Updating schedule for " + attendee.getName());
    }

    public void showCertificates(Attendee attendee) {
        if (attendee.hasCertificate()) {
            log(attendee.getName() + " has a certificate");
        } else {
            log(attendee.getName() + " does not have a certificate");
        }
    }
}

