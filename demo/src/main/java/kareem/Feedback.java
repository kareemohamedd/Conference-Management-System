package kareem;

public class Feedback {
    private String attendeeID;
    private String comments;
    private int rating;
    private String sessionID;

    public Feedback(String attendeeID, String comments, int rating, String sessionID) {
        this.attendeeID = attendeeID;
        this.comments = comments;
        this.rating = rating;
        this.sessionID = sessionID;
    }

    public String getFeedbackSummary() {
        // Generate and return feedback summary
        return "Feedback Summary: " +
               "\nAttendee ID: " + attendeeID +
               "\nComments: " + comments +
               "\nRating: " + rating +
               "\nSession ID: " + sessionID;
    }

    public String getAttendeeID() {
        return attendeeID;
    }

    public String getComments() {
        return comments;
    }

    public int getRating() {
        return rating;
    }

    public String getSessionID() {
        return sessionID;
    }
}

