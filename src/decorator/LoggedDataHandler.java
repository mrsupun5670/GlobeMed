package decorator;

import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggedDataHandler extends DataHandlerDecorator {
    
    public LoggedDataHandler(DataHandler handler) {
        super(handler);
    }
    
    @Override
    public String read(String data) {
        logOperation("READ", data, null);
        return super.read(data);
    }
    
    @Override
    public void write(String data) {
        logOperation("WRITE", data, null);
        super.write(data);
    }
    
    @Override
    public void delete(String identifier) {
        logOperation("DELETE", identifier, null);
        super.delete(identifier);
    }
    
    @Override
    public void update(String identifier, String newData) {
        logOperation("UPDATE", identifier, newData);
        super.update(identifier, newData);
    }
    
    private void logOperation(String operation, String data, String newData) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        StringBuilder logEntry = new StringBuilder();
        logEntry.append("📝 AUDIT LOG\n");
        logEntry.append("Timestamp: ").append(timestamp).append("\n");
        logEntry.append("Operation: ").append(operation).append("\n");
        logEntry.append("Data: ").append(data).append("\n");
        
        if (newData != null) {
            logEntry.append("New Data: ").append(newData).append("\n");
        }
        
        logEntry.append("User: Current System User\n");
        logEntry.append("Security Level: HIGH");
        
        System.out.println(logEntry.toString());
        
        JOptionPane.showMessageDialog(null, 
            logEntry.toString(),
            "Audit Log", JOptionPane.INFORMATION_MESSAGE);
    }
}