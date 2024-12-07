package kareem;

import java.util.Date;

public class Certificate {
    private String certificateID;
    private Attendee attendee;
    private Date issueDate;

    public Certificate(String certificateID, Attendee attendee) {
        this.certificateID = certificateID;
        this.attendee = attendee;
        this.issueDate = new Date();
    }

    public String generateText() {
        return "Certificate for " + attendee.getName();
    }
}

