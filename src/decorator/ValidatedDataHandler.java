package decorator;

import javax.swing.JOptionPane;

public class ValidatedDataHandler extends DataHandlerDecorator {
    
    public ValidatedDataHandler(DataHandler handler) {
        super(handler);
    }
    
    @Override
    public String read(String data) {
        if (validateReadAccess(data)) {
            return super.read(data);
        } else {
            throw new SecurityException("Invalid read access for: " + data);
        }
    }
    
    @Override
    public void write(String data) {
        if (validateWriteData(data)) {
            super.write(data);
        } else {
            throw new SecurityException("Invalid data format for write operation");
        }
    }
    
    @Override
    public void delete(String identifier) {
        if (validateDeletePermission(identifier)) {
            super.delete(identifier);
        } else {
            throw new SecurityException("Insufficient permissions to delete: " + identifier);
        }
    }
    
    @Override
    public void update(String identifier, String newData) {
        if (validateUpdateData(identifier, newData)) {
            super.update(identifier, newData);
        } else {
            throw new SecurityException("Invalid update data for: " + identifier);
        }
    }
    
    private boolean validateReadAccess(String data) {
        return data != null && !data.trim().isEmpty() && !data.contains("RESTRICTED");
    }
    
    private boolean validateWriteData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return false;
        }
        return data.length() <= 1000;
    }
    
    private boolean validateDeletePermission(String identifier) {
        return identifier != null && !identifier.trim().isEmpty() && 
               !identifier.startsWith("SYSTEM_") && !identifier.startsWith("ADMIN_");
    }
    
    private boolean validateUpdateData(String identifier, String newData) {
        return validateDeletePermission(identifier) && validateWriteData(newData);
    }
}