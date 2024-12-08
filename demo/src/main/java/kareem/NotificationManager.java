package kareem;

import java.util.List;
import javafx.scene.control.TextArea;

public class NotificationManager {
    private TextArea logArea;

    public NotificationManager(TextArea logArea) {
        this.logArea = logArea;
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    public void sendUpdate(Attendee attendee, String message) {
        log("Update sent to " + attendee.getName() + ": " + message);
    }

    public void broadcastMessage(String message) {
        log("Broadcast message: " + message);
    }

    public List<String> getNotificationHistory(Attendee attendee) {
        // Retrieve notification history for an attendee
        return List.of("Notification1", "Notification2");
    }
}

