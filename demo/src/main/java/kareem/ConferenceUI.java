package kareem;

import java.util.List;

public class ConferenceUI {
    public void displaySessions(List<Session> sessions) {
        for (Session session : sessions) {
            System.out.println(session.getDetails());
        }
    }

    public void showAttendance(Attendee attendee) {
        System.out.println("Attendance for " + attendee.getName() + ": " + attendee.getSchedule().size() + " sessions");
    }

    public void collectFeedback(Attendee attendee) {
        System.out.println("Collecting feedback from " + attendee.getName());
    }

    public void updateSchedule(Attendee attendee) {
        System.out.println("Updating schedule for " + attendee.getName());
    }

    public void showCertificates(Attendee attendee) {
        System.out.println("Certificate for " + attendee.getName() + ": " + attendee.getCertificate().generateText());
    }
}

