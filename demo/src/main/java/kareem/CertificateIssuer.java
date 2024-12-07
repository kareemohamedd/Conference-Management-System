package kareem;

public class CertificateIssuer {
    public Certificate generateCertificate(Attendee attendee) {
        // Generate and return a certificate for the attendee
        return new Certificate("Cert123", attendee);
    }

    public void sendCertificate(Attendee attendee) {
        // Send certificate to attendee
        System.out.println("Certificate sent to " + attendee.getName());
    }

    public boolean validateCertificate(Certificate cert) {
        // Validate the certificate
        return cert != null;
    }
}
