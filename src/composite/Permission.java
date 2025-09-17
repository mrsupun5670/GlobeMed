package composite;

import java.util.ArrayList;
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
    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        permissions.add(name);
        return permissions;
    }
    
    @Override
    public void add(PermissionComponent component) {
        throw new UnsupportedOperationException("Cannot add to a leaf component");
    }
    
    @Override
    public void remove(PermissionComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf component");
    }
    
    @Override
    public boolean hasPermission(String permission) {
        return this.name.equalsIgnoreCase(permission);
    }
    
    @Override
    public void displayStructure(int depth) {
        String indent = "";
        for(int i = 0; i < depth; i++) {
            indent += "  ";
        }
        System.out.println(indent + "Permission: " + name);
    }
    
    @Override
    public String toString() {
        return name;
    }
}