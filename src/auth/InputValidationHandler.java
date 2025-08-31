package auth;

import javax.swing.JOptionPane;

public class InputValidationHandler extends AuthHandler {
    protected boolean process(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "Please enter both username and password",
                "Login Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}