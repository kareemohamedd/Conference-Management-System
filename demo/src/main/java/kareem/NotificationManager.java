package kareem;

import java.util.List;

public class NotificationManager {
    public void sendUpdate(Attendee attendee, String message) {
        System.out.println("Update sent to " + attendee.getName() + ": " + message);
    }

    public void broadcastMessage(String message) {
        System.out.println("Broadcast message: " + message);
    }

    public List<String> getNotificationHistory(Attendee attendee) {
        // Retrieve notification history for an attendee
        return List.of("Notification1", "Notification2");
    }
}

