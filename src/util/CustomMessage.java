package util;

import javax.swing.*;
import java.awt.*;

public class CustomMessage {

    public enum MessageType {
        SUCCESS(new Color(34, 139, 34), "Success"),   
        ERROR(new Color(220, 20, 60), "Error"),       
        WARNING(new Color(255, 193, 7), "Warning"),  
        INFO(new Color(30, 144, 255), "Information"); 

        public final Color color;
        public final String title;

        MessageType(Color color, String title) {
            this.color = color;
            this.title = title;
        }
    }

    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);

    public static void showMessage(Component parent, String message, MessageType type) {
        JLabel label = createStyledLabel(message, type.color);
        JOptionPane.showMessageDialog(parent, label, type.title, JOptionPane.PLAIN_MESSAGE);
    }

    public static boolean showConfirm(Component parent, String message, MessageType type) {
        JLabel label = createStyledLabel(message, type.color);
        int result = JOptionPane.showConfirmDialog(
                parent,
                label,
                type.title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }

    private static JLabel createStyledLabel(String message, Color color) {
        JLabel label = new JLabel("<html><div style='text-align:center;'>" + message + "</div></html>");
        label.setFont(DEFAULT_FONT);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static void showSuccess(Component parent, String msg) { showMessage(parent, msg, MessageType.SUCCESS); }
    public static void showError(Component parent, String msg)   { showMessage(parent, msg, MessageType.ERROR); }
    public static void showWarning(Component parent, String msg) { showMessage(parent, msg, MessageType.WARNING); }
    public static void showInfo(Component parent, String msg)    { showMessage(parent, msg, MessageType.INFO); }
}
