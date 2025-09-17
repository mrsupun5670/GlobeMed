package bridge;

import model.Patient;
import java.util.List;

public class NursePermission implements RolePermission {
    
    @Override
    public boolean canAdd() {
        return false;
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
        return allPatients;
    }
}