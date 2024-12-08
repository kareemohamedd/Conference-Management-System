package kareem;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextArea;

public class CertificateIssuer {
    private TextArea logArea;
    private Map<Attendee, Certificate> issuedCertificates;

    public CertificateIssuer(TextArea logArea) {
        this.logArea = logArea;
        this.issuedCertificates = new HashMap<>();
    }

    private void log(String message) {
        logArea.appendText(message + "\n");
    }

    public Certificate generateCertificate(Attendee attendee) {
        if (issuedCertificates.containsKey(attendee)) {
            log("Certificate already issued for " + attendee.getName());
            return issuedCertificates.get(attendee);
        }
        Certificate certificate = new Certificate("Cert123", attendee);
        issuedCertificates.put(attendee, certificate);
        log("Certificate generated for " + attendee.getName());
        return certificate;
    }

    public void sendCertificate(Attendee attendee) {
        // Implementation for sending the certificate
    }

    public boolean validateCertificate(Certificate cert) {
        // Validate the certificate
        return cert != null;
    }
}
