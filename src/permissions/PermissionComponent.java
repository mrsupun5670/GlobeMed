package permissions;

import java.util.List;

public interface PermissionComponent {
    String getName();
    List<PermissionComponent> getPermissions();
}
