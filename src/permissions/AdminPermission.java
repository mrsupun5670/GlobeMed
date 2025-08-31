
package permissions;

public class AdminPermission implements RolePermission {

    @Override
    public boolean canAdd() {
        return false;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public boolean canDelete() {
        return true;
    }

    @Override
    public boolean canView() {
        return true;
    }
}
