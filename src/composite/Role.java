package composite;

import java.util.ArrayList;
import java.util.List;

public class Role implements PermissionComponent {
    private String name;
    private List<PermissionComponent> components;
    
    public Role(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public List<String> getPermissions() {
        List<String> allPermissions = new ArrayList<>();
        for (PermissionComponent component : components) {
            allPermissions.addAll(component.getPermissions());
        }
        return allPermissions;
    }
    
    @Override
    public void add(PermissionComponent component) {
        components.add(component);
    }
    
    @Override
    public void remove(PermissionComponent component) {
        components.remove(component);
    }
    
    @Override
    public boolean hasPermission(String permission) {
        for (PermissionComponent component : components) {
            if (component.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void displayStructure(int depth) {
        String indent = "";
        for(int i = 0; i < depth; i++) {
            indent += "  ";
        }
        System.out.println(indent + "Role: " + name);
        for (PermissionComponent component : components) {
            component.displayStructure(depth + 1);
        }
    }
    
    public List<PermissionComponent> getComponents() {
        return new ArrayList<>(components);
    }
    
    @Override
    public String toString() {
        return name + " (" + components.size() + " permissions)";
    }
}