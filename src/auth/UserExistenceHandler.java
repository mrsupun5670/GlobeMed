package auth;


import auth.AuthHandler;
import javax.swing.JOptionPane;
import util.DBData;

public class UserExistenceHandler extends AuthHandler {
    protected boolean process(String username, String password) {
        if (DBData.users().stream().noneMatch(u -> u.getUsername().equals(username))) {
            JOptionPane.showMessageDialog(null,
                "User not found!", "Login Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}