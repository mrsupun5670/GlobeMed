package bridge;

import model.Patient;
import java.util.List;
import javax.swing.JOptionPane;

public abstract class PatientRecordBridge {
    protected RolePermission rolePermission;
    
    protected PatientRecordBridge(RolePermission rolePermission) {
        this.rolePermission = rolePermission;
    }
    
    public abstract void displayPatients();
    public abstract void addPatient(Patient patient);
    public abstract void updatePatient(Patient patient);
    public abstract void deletePatient(String patientId);
    
    protected boolean checkPermission(String action) {
        boolean hasPermission = false;
        
        switch(action.toLowerCase()) {
            case "add":
                hasPermission = rolePermission.canAdd();
                break;
            case "update":
                hasPermission = rolePermission.canUpdate();
                break;
            case "view":
                hasPermission = rolePermission.canView();
                break;
            case "delete":
                hasPermission = rolePermission.canDelete();
                break;
        }
        
        if (!hasPermission) {
            JOptionPane.showMessageDialog(null, 
                "Access Denied: You don't have permission to " + action + " patient records.",
                "Security Warning", JOptionPane.WARNING_MESSAGE);
        }
        
        return hasPermission;
    }
}