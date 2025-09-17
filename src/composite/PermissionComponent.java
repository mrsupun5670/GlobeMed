package composite;

import java.util.List;

public interface PermissionComponent {
    String getName();
    List<String> getPermissions();
    void add(PermissionComponent component);
    void remove(PermissionComponent component);
    boolean hasPermission(String permission);
    void displayStructure(int depth);
}