package bridge;

import model.Patient;
import java.util.List;
import java.util.ArrayList;

public class DoctorPermission implements RolePermission {
    private String doctorId;
    
    public DoctorPermission(String doctorId) {
        this.doctorId = doctorId;
    }
    
    @Override
    public boolean canAdd() {
        return true;
    }
    
    @Override
    public boolean canUpdate() {
        return true;
    }
    
    @Override
    public boolean canView() {
        return true;
    }
    
    @Override
    public boolean canDelete() {
        return false;
    }
    
    @Override
    public List<Patient> getAccessiblePatients(List<Patient> allPatients) {
        List<Patient> accessiblePatients = new ArrayList<>();
        for (Patient patient : allPatients) {
            if (patient.getAssignedDoctorId().equals(doctorId)) {
                accessiblePatients.add(patient);
            }
        }
        return accessiblePatients;
    }
}