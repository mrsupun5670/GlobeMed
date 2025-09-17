package model;

import composite.PermissionComponent;
import composite.Role;

import java.util.ArrayList;
import java.util.List;

public class StaffModel {
    private String id;
    private String name;
    private Role role;
    private List<PermissionComponent> customPermissions; 

    public StaffModel(String id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.customPermissions = new ArrayList<>();
        if (role != null) {
            for (String permission : role.getPermissions()) {
                this.customPermissions.add(new composite.Permission(permission));
            }
        } 
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }
    
    public Role getRole() { return role; }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public List<PermissionComponent> getPermissions() { return customPermissions; }

    public void setPermissions(List<PermissionComponent> permissions) {
        this.customPermissions = permissions;
    }

   public String getPermissionsString() {
    if (customPermissions == null || customPermissions.isEmpty()) return "";
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < customPermissions.size(); i++) {
        if (i > 0) result.append(", ");
        result.append(customPermissions.get(i).getName());
    }
    return result.toString();
}

}
