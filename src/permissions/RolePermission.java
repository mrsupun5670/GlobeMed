package permissions;

public interface RolePermission {
    boolean canAdd();
    boolean canUpdate();
    boolean canDelete();
    boolean canView();
}

