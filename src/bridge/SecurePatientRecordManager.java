
package bridge;

import model.Patient;
import util.DBData;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SecurePatientRecordManager extends PatientRecordBridge {
    
    public SecurePatientRecordManager(RolePermission rolePermission) {
        super(rolePermission);
    }
    
    @Override
    public void displayPatients() {
        if (!checkPermission("view")) {
            return;
        }
        
        List<Patient> allPatients = new ArrayList<>(DBData.patients().values());
        List<Patient> accessiblePatients = rolePermission.getAccessiblePatients(allPatients);
        
        StringBuilder patientInfo = new StringBuilder("Accessible Patient Records:\n\n");
        for (Patient patient : accessiblePatients) {
            patientInfo.append("ID: ").append(patient.getId())
                      .append(", Name: ").append(patient.getName())
                      .append(", Phone: ").append(patient.getPhone())
                      .append("\n");
        }
        
        JOptionPane.showMessageDialog(null, patientInfo.toString(), 
            "Patient Records", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void addPatient(Patient patient) {
        if (!checkPermission("add")) {
            return;
        }
        
        DBData.patients().put(patient.getId(), patient);
        JOptionPane.showMessageDialog(null, 
            "Patient " + patient.getName() + " added successfully!",
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void updatePatient(Patient patient) {
        if (!checkPermission("update")) {
            return;
        }
        
        if (DBData.patients().containsKey(patient.getId())) {
            List<Patient> accessiblePatients = rolePermission.getAccessiblePatients(
                new ArrayList<>(DBData.patients().values()));
            
            boolean canUpdateThisPatient = false;
            for (Patient p : accessiblePatients) {
                if (p.getId().equals(patient.getId())) {
                    canUpdateThisPatient = true;
                    break;
                }
            }
                
            if (canUpdateThisPatient) {
                DBData.patients().put(patient.getId(), patient);
                JOptionPane.showMessageDialog(null, 
                    "Patient " + patient.getName() + " updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Access Denied: You cannot update this patient's record.",
                    "Security Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, 
                "Patient not found!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void deletePatient(String patientId) {
        if (!checkPermission("delete")) {
            return;
        }
        
        if (DBData.patients().containsKey(patientId)) {
            Patient removedPatient = DBData.patients().remove(patientId);
            JOptionPane.showMessageDialog(null, 
                "Patient " + removedPatient.getName() + " deleted successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Patient not found!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}