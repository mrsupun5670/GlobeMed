package bridge;

import model.Patient;
import java.util.List;

public class AdminPermission implements RolePermission {
    
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
        return true;
    }
    
    @Override
    public List<Patient> getAccessiblePatients(List<Patient> allPatients) {
        return allPatients;
    }
}