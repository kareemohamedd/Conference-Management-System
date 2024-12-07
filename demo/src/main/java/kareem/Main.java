package kareem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conference conference = new Conference("Tech Conference", new Date(), new Date());
        CertificateIssuer issuer = new CertificateIssuer();
        ConferenceUI ui = new ConferenceUI();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Register an attendee");
            System.out.println("2. Add a session");
            System.out.println("3. Provide feedback");
            System.out.println("4. Generate and send certificate");
            System.out.println("5. Display conference details");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter attendee name: ");
                    String attendeeName = scanner.nextLine();
                    System.out.print("Enter attendee email: ");
                    String attendeeEmail = scanner.nextLine();
                    Attendee attendee = conference.registerAttendee(attendeeName, attendeeEmail);
                    System.out.println("Attendee registered: " + attendee.getName());
                    break;

                case 2:
                    System.out.print("Enter session ID: ");
                    String sessionID = scanner.nextLine();
                    System.out.print("Enter session name: ");
                    String sessionName = scanner.nextLine();
                    System.out.print("Enter session date (yyyy-MM-dd): ");
                    String sessionDateStr = scanner.nextLine();
                    Date sessionDate = null;
                    try {
                        sessionDate = dateFormat.parse(sessionDateStr);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                        break;
                    }
                    System.out.print("Enter session time: ");
                    String sessionTime = scanner.nextLine();
                    System.out.print("Enter session room: ");
                    String sessionRoom = scanner.nextLine();
                    Session session = new Session(sessionID, sessionName, sessionDate, sessionTime, sessionRoom);
                    conference.addSession(session);
                    System.out.println("Session added: " + session.getDetails());
                    break;

                case 3:
                    System.out.print("Enter attendee name: ");
                    attendeeName = scanner.nextLine();
                    attendee = conference.getAttendeeList().stream()
                            .filter(a -> a.getName().equals(attendeeName))
                            .findFirst()
                            .orElse(null);
                    if (attendee == null) {
                        System.out.println("Attendee not found.");
                        break;
                    }
                    System.out.print("Enter session ID: ");
                    sessionID = scanner.nextLine();
                    session = conference.getSessionDetails().stream()
                            .filter(s -> s.getSessionID().equals(sessionID))
                            .findFirst()
                            .orElse(null);
                    if (session == null) {
                        System.out.println("Session not found.");
                        break;
                    }
                    System.out.print("Enter feedback comments: ");
                    String comments = scanner.nextLine();
                    System.out.print("Enter feedback rating (1-5): ");
                    int rating = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Feedback feedback = new Feedback(attendee.getName(), comments, rating, session.getSessionID());
                    attendee.provideFeedback(feedback);
                    break;

                case 4:
                    System.out.print("Enter attendee name: ");
                    attendeeName = scanner.nextLine();
                    attendee = conference.getAttendeeList().stream()
                            .filter(a -> a.getName().equals(attendeeName))
                            .findFirst()
                            .orElse(null);
                    if (attendee == null) {
                        System.out.println("Attendee not found.");
                        break;
                    }
                    Certificate certificate = issuer.generateCertificate(attendee);
                    issuer.sendCertificate(attendee);
                    break;

                case 5:
                    ui.displaySessions(conference.getSessionDetails());
                    System.out.print("Enter attendee name to show attendance and certificates: ");
                    attendeeName = scanner.nextLine();
                    attendee = conference.getAttendeeList().stream()
                            .filter(a -> a.getName().equals(attendeeName))
                            .findFirst()
                            .orElse(null);
                    if (attendee != null) {
                        ui.showAttendance(attendee);
                        ui.showCertificates(attendee);
                    } else {
                        System.out.println("Attendee not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}