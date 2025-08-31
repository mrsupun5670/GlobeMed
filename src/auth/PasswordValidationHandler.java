package auth;

import javax.swing.JOptionPane;
import model.User;
import util.DBData;

public class PasswordValidationHandler extends AuthHandler {
    protected boolean process(String username, String password) {
        User u = DBData.users().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);

        if (u == null || !u.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(null,
                "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
