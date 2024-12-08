package kareem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConferenceApp extends Application {

    private Conference conference;
    private ComboBox<String> speakerComboBox = new ComboBox<>();
    private CertificateIssuer issuer;
    private ConferenceUI ui;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private TextArea logArea;
    private Label statusLabel;
    private Admin admin = new Admin("123456","kareem","kareem@gmail.com");
    private List<ConferenceManager> managers = List.of(
        new ConferenceManager("1", "Manager One", "manager1@example.com"),
        new ConferenceManager("2", "Manager Two", "manager2@example.com"),
        new ConferenceManager("3", "Manager Three", "manager3@example.com")
    );

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Conference Management System");

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(200);

        statusLabel = new Label("Ready");

        showAdminLoginScreen(primaryStage);
    }

    private void showAdminLoginScreen(Stage primaryStage) {
        // Create a GridPane layout
        GridPane grid = new GridPane();
        grid.setHgap(10); // Horizontal gap between columns
        grid.setVgap(10); // Vertical gap between rows
        grid.setPadding(new Insets(10, 10, 10, 10)); // Padding around the grid

        Label emailLabel = new Label("Admin Email:");
        TextField emailField = new TextField();
        Label idLabel = new Label("Admin ID:");
        TextField idField = new TextField();
        Button loginButton = new Button("Login");
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-size: 12px;"); 
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String id = idField.getText().toString();
            if (email.isEmpty() || id.isEmpty()) {
                errorMessage.setText("note:\n id: \"123456\",email:\"kareem@gmail.com\" "); 
            } else if (admin.checkEmail(email, id)) {
                showConferenceDetailsScreen(primaryStage); 
            } else {
                errorMessage.setText("Invalid email or ID. Please try again."); 
            }
        });

        grid.add(emailLabel, 0, 0);       
        grid.add(emailField, 1, 0);      
        grid.add(idLabel, 0, 1);         
        grid.add(idField, 1, 1);       
        grid.add(loginButton, 1, 2);   
        grid.add(errorMessage, 1, 3);    
    
        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showConferenceDetailsScreen(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label nameLabel = new Label("Conference Name:");
        TextField nameField = new TextField();
        Label startDateLabel = new Label("Start Date (yyyy-MM-dd):");
        TextField startDateField = new TextField();
        Label endDateLabel = new Label("End Date (yyyy-MM-dd):");
        TextField endDateField = new TextField();
        Label managerLabel = new Label("Select Manager:");
        ComboBox<String> managerComboBox = new ComboBox<>();
        managers.forEach(manager -> managerComboBox.getItems().add(manager.getName()));
        Button createButton = new Button("Create Conference");

        createButton.setOnAction(e -> {
            String name = nameField.getText();
            String startDateStr = startDateField.getText();
            String endDateStr = endDateField.getText();
            String selectedManagerName = managerComboBox.getValue();
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = dateFormat.parse(startDateStr);
                endDate = dateFormat.parse(endDateStr);
            } catch (ParseException ex) {
                log("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }
            ConferenceManager selectedManager = managers.stream()
                    .filter(manager -> manager.getName().equals(selectedManagerName))
                    .findFirst()
                    .orElse(null);
            if (selectedManager == null) {
                log("Please select a manager.");
                return;
            }
            conference = new Conference(name, startDate, endDate, logArea);
            selectedManager.setupConference(conference);
            issuer = new CertificateIssuer(logArea);
            ui = new ConferenceUI(logArea);
            showMainGUI(primaryStage);
        });

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(startDateLabel, 0, 1);
        grid.add(startDateField, 1, 1);
        grid.add(endDateLabel, 0, 2);
        grid.add(endDateField, 1, 2);
        grid.add(managerLabel, 0, 3);
        grid.add(managerComboBox, 1, 3);
        grid.add(createButton, 1, 4);

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMainGUI(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        Tab registerTab = new Tab("Register Attendee", createRegisterPane());
        Tab sessionTab = new Tab("Add Session", createSessionPane());
        Tab feedbackTab = new Tab("Provide Feedback", createFeedbackPane());
        Tab certificateTab = new Tab("Generate Certificate", createCertificatePane());
        Tab detailsTab = new Tab("Conference Details", createDetailsPane());
        Tab viewAllTab = new Tab("View All", createViewAllPane());
        Tab addSpeakerTab = new Tab("Add Speaker", createAddSpeakerPane());
        Tab attendTab = new Tab("Attend Session", createAttendPane());

        tabPane.getTabs().addAll(registerTab, sessionTab, feedbackTab, certificateTab, detailsTab, viewAllTab, addSpeakerTab, attendTab);

        VBox vbox = new VBox(tabPane, logArea);
        BorderPane root = new BorderPane();
        root.setTop(createMenuBar());
        root.setCenter(vbox);
        root.setBottom(createStatusBar());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitMenuItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> showAboutDialog());
        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(5, 10, 5, 10));
        statusBar.setStyle("-fx-background-color: #E8E8E8;");
        statusBar.getChildren().add(statusLabel);
        return statusBar;
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Conference Management System v1.0\nDeveloped by Kareem");
        alert.showAndWait();
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
        statusLabel.setText(message);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private GridPane createRegisterPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label nameLabel = new Label("Attendee Name:");
        TextField nameField = new TextField();
        Label emailLabel = new Label("Attendee Email:");
        TextField emailField = new TextField();
        Button registerButton = new Button("Register Attendee");

        registerButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            if (!isValidEmail(email)) {
                log("Invalid email format.");
                return;
            }
            Attendee attendee = conference.registerAttendee(name, email);
        });

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(emailLabel, 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(registerButton, 1, 2);

        return grid;
    }

    private GridPane createSessionPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label idLabel = new Label("Session ID:");
        TextField idField = new TextField();
        Label nameLabel = new Label("Session Name:");
        TextField nameField = new TextField();
        Label dateLabel = new Label("Session Date (yyyy-MM-dd):");
        TextField dateField = new TextField();
        Label timeLabel = new Label("Session Time:");
        TextField timeField = new TextField();
        Label roomLabel = new Label("Session Room:");
        TextField roomField = new TextField();
        Label speakerLabel = new Label("Speaker:");
       
        speakerComboBox.getItems().addAll(conference.getSpeakerNames());
        Button addButton = new Button("Add Session");

        addButton.setOnAction(e -> {
            String sessionID = idField.getText();
            String sessionName = nameField.getText();
            String sessionDateStr = dateField.getText();
            Date sessionDate = null;
            try {
                sessionDate = dateFormat.parse(sessionDateStr);
            } catch (ParseException ex) {
                log("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }
            String sessionTime = timeField.getText();
            String sessionRoom = roomField.getText();
            Speaker speaker = conference.getSpeakers().stream()
                    .filter(a -> a.getName().equals(speakerComboBox.getValue()))
                    .findFirst()
                    .orElse(null);
            Session session = new Session(sessionID, sessionName, sessionDate, sessionTime, sessionRoom);
            session.setSpeaker(speaker);
            conference.addSession(session);
        });

        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(dateLabel, 0, 2);
        grid.add(dateField, 1, 2);
        grid.add(timeLabel, 0, 3);
        grid.add(timeField, 1, 3);
        grid.add(roomLabel, 0, 4);
        grid.add(roomField, 1, 4);
        grid.add(speakerLabel, 0, 5);
        grid.add(speakerComboBox, 1, 5);
        grid.add(addButton, 1, 6);

        return grid;
    }

    private GridPane createFeedbackPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label attendeeLabel = new Label("Attendee Name:");
        TextField attendeeField = new TextField();
        Label sessionLabel = new Label("Session ID:");
        TextField sessionField = new TextField();
        Label commentsLabel = new Label("Comments:");
        TextField commentsField = new TextField();
        Label ratingLabel = new Label("Rating (1-5):");
        TextField ratingField = new TextField();
        Button feedbackButton = new Button("Provide Feedback");

        feedbackButton.setOnAction(e -> {
            String attendeeName = attendeeField.getText();
            Attendee attendee = conference.getAttendeeList().stream()
                    .filter(a -> a.getName().equals(attendeeName))
                    .findFirst()
                    .orElse(null);
            if (attendee == null) {
                log("Attendee not found.");
                return;
            }
            String sessionID = sessionField.getText();
            Session session = conference.getSessionDetails().stream()
                    .filter(s -> s.getSessionID().equals(sessionID))
                    .findFirst()
                    .orElse(null);
            if (session == null) {
                log("Session not found.");
                return;
            }
            String comments = commentsField.getText();
            int rating = Integer.parseInt(ratingField.getText());
            Feedback feedback = new Feedback(attendee.getName(), comments, rating, session.getSessionID());
            attendee.provideFeedback(feedback);
            log("Feedback provided by " + attendee.getName());
        });

        grid.add(attendeeLabel, 0, 0);
        grid.add(attendeeField, 1, 0);
        grid.add(sessionLabel, 0, 1);
        grid.add(sessionField, 1, 1);
        grid.add(commentsLabel, 0, 2);
        grid.add(commentsField, 1, 2);
        grid.add(ratingLabel, 0, 3);
        grid.add(ratingField, 1, 3);
        grid.add(feedbackButton, 1, 4);

        return grid;
    }

    private GridPane createCertificatePane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label attendeeLabel = new Label("Attendee Name:");
        TextField attendeeField = new TextField();
        Button certificateButton = new Button("Generate Certificate");

        certificateButton.setOnAction(e -> {
            String attendeeName = attendeeField.getText();
            Attendee attendee = conference.getAttendeeList().stream()
                    .filter(a -> a.getName().equals(attendeeName))
                    .findFirst()
                    .orElse(null);
            if (attendee == null) {
                log("Attendee not found.");
                return;
            }
            if(attendee.hasCertificate()){
                log("Certificate already generated for " + attendee.getName());
                return;
            }
            else{
                attendee.addCertificate();
                Certificate certificate = issuer.generateCertificate(attendee);
                issuer.sendCertificate(attendee);
            }
        });

        grid.add(attendeeLabel, 0, 0);
        grid.add(attendeeField, 1, 0);
        grid.add(certificateButton, 1, 1);

        return grid;
    }

    private GridPane createDetailsPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label attendeeLabel = new Label("Attendee Name:");
        TextField attendeeField = new TextField();
        Button detailsButton = new Button("Display Conference Details");

        detailsButton.setOnAction(e -> {
            String attendeeName = attendeeField.getText();
            Attendee attendee = conference.getAttendeeList().stream()
                    .filter(a -> a.getName().equals(attendeeName))
                    .findFirst()
                    .orElse(null);
            if (attendee != null) {
                ui.showAttendance(attendee);
                ui.showCertificates(attendee);
            } else {
                log("Attendee not found.");
            }
        });

        grid.add(attendeeLabel, 0, 0);
        grid.add(attendeeField, 1, 0);
        grid.add(detailsButton, 1, 1);

        return grid;
    }

    private GridPane createViewAllPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextArea viewAllArea = new TextArea();
        viewAllArea.setEditable(false);

        Button viewAllButton = new Button("View All Details");

        viewAllButton.setOnAction(e -> {
            StringBuilder details = new StringBuilder();
        
            // Attendees Section
            details.append("=== Attendees ===\n\n");
            for (Attendee attendee : conference.getAttendeeList()) {
                details.append("- ").append(attendee.getName()).append("\n")
                       .append("  Email: ").append(attendee.getEmail()).append("\n\n");
            }
        
            // Speakers Section
            details.append("=== Speakers ===\n\n");
            for (Speaker speaker : conference.getSpeakers()) {
                details.append("- ").append(speaker.getName()).append("\n")
                       .append("  Bio: ").append(speaker.getBio()).append("\n\n");
            }
        
            // Sessions Section
            details.append("=== Sessions ===\n\n");
            for (Session session : conference.getSessionDetails()) {
                details.append("- ").append(session.getDetails()).append("\n\n");
            }
        
            // Display the formatted details
            viewAllArea.setText(details.toString());
        });

        grid.add(viewAllButton, 0, 0);
        grid.add(viewAllArea, 0, 1, 2, 1);

        return grid;
    }

    private GridPane createAddSpeakerPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label idLabel = new Label("Speaker ID:");
        TextField idField = new TextField();
        Label nameLabel = new Label("Speaker Name:");
        TextField nameField = new TextField();
        Label bioLabel = new Label("Speaker Bio:");
        TextField bioField = new TextField();
        Button addButton = new Button("Add Speaker");

        addButton.setOnAction(e -> {
            String speakerID = idField.getText();
            String name = nameField.getText();
            String bio = bioField.getText();
            Speaker speaker = new Speaker(speakerID, name, bio);
            conference.addSpeaker(speaker);
            speakerComboBox.getItems().addAll(conference.getSpeakerNames());
        });

        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(bioLabel, 0, 2);
        grid.add(bioField, 1, 2);
        grid.add(addButton, 1, 3);

        return grid;
    }

    private GridPane createAttendPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label attendeeLabel = new Label("Attendee Name:");
        TextField attendeeField = new TextField();
        Label sessionLabel = new Label("Session ID:");
        TextField sessionField = new TextField();
        Button attendButton = new Button("Attend Session");

        attendButton.setOnAction(e -> {
            String attendeeName = attendeeField.getText();
            Attendee attendee = conference.getAttendeeList().stream()
                    .filter(a -> a.getName().equals(attendeeName))
                    .findFirst()
                    .orElse(null);
            if (attendee == null) {
                log("Attendee not found.");
                return;
            }
            String sessionID = sessionField.getText();
            Session session = conference.getSessionDetails().stream()
                    .filter(s -> s.getSessionID().equals(sessionID))
                    .findFirst()
                    .orElse(null);

            if (session == null) {
                log("Session not found.");
                return;
            }
            else{
                attendee.createSchedule(sessionField.getText());
            }
            session.registerAttendance(attendee);
        });

        grid.add(attendeeLabel, 0, 0);
        grid.add(attendeeField, 1, 0);
        grid.add(sessionLabel, 0, 1);
        grid.add(sessionField, 1, 1);
        grid.add(attendButton, 1, 2);

        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
