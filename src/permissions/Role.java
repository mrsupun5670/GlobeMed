package permissions;

import java.util.ArrayList;
import java.util.List;

public class Role implements PermissionComponent {
    private String name;
    private List<PermissionComponent> permissions = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }

    public void addPermission(PermissionComponent permission) {
        permissions.add(permission);
    }

    public void removePermission(PermissionComponent permission) {
        permissions.remove(permission);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<PermissionComponent> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return name;
    }
    

}
