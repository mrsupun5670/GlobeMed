package bridge;

import model.Patient;
import java.util.List;
import java.util.ArrayList;

public class PharmacistPermission implements RolePermission {
    
    @Override
    public boolean canAdd() {
        return false;
    }
    
    @Override
    public boolean canUpdate() {
        return false;
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