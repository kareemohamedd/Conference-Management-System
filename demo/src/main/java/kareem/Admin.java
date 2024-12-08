package kareem;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextArea;

import java.util.Date;

public class Admin extends User {
    private String adminID;
    private List<Attendee> attendees;
    private Conference conference;

    public Admin(String adminID, String name, String email) {
        super(name, email);
        this.adminID = adminID;
        this.attendees = new ArrayList<>();
        
    }
    public boolean checkEmail(String email,String id){
        return (email.equals(this.email) && id.equals(this.adminID));
    }
    public Conference createConference(String name, Date startDate, Date endDate,TextArea logArea) {
        conference = new Conference(name, startDate, endDate,logArea);
        return conference;
    }

    public void addUser(String name, String email)  {
        conference.registerAttendee(name, email);
    }

    public void removeUser(Attendee user) {
        conference.removeAttendee(user);;
    }

    public String viewAnalytics() {
        return "Total attendees: " + attendees.size();
    }
}
