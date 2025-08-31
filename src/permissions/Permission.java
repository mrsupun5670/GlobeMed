package permissions;

import java.util.Collections;
import java.util.List;

public class Permission implements PermissionComponent {
    private String name;

    public Permission(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<PermissionComponent> getPermissions() {
        return Collections.singletonList(this); 
    }

    @Override
    public String toString() {
        return name;
    }
}
