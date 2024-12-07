package kareem;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private String adminID;
    private String name;
    private String email;
    private List<Attendee> attendees;

    public Admin(String adminID, String name, String email) {
        this.adminID = adminID;
        this.name = name;
        this.email = email;
        this.attendees = new ArrayList<>();
    }

    public void addUser(Attendee user) {
        attendees.add(user);
        System.out.println("User added: " + user.getName());
    }

    public void removeUser(Attendee user) {
        attendees.remove(user);
        System.out.println("User removed: " + user.getName());
    }

    public String viewAnalytics() {
        return "Total attendees: " + attendees.size();
    }
}
