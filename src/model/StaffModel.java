package model;

import permissions.PermissionComponent;
import permissions.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaffModel {
    private String id;
    private String name;
    private Role role;
    private List<PermissionComponent> customPermissions; 

    public StaffModel(String id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.customPermissions = new ArrayList<>(role != null ? role.getPermissions() : List.of()); 
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
    return customPermissions.stream()
            .map(PermissionComponent::getName)
            .collect(Collectors.joining(", "));
}

}
