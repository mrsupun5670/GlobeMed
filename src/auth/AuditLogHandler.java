package auth;

public class AuditLogHandler extends AuthHandler {
    protected boolean process(String username, String password) {
        System.out.println("Audit: " + username + " attempted login at " + java.time.LocalDateTime.now());
        return true;
    }
}