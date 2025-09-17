package bridge;

import model.Patient;
import java.util.List;

public interface RolePermission {
    boolean canAdd();
    boolean canUpdate();
    boolean canView();
    boolean canDelete();
    List<Patient> getAccessiblePatients(List<Patient> allPatients);
}