/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package permissions;

 public class DoctorPermission implements RolePermission {
    @Override
    public boolean canAdd() { return true; }
    @Override
    public boolean canUpdate() { return true; }
    @Override
    public boolean canDelete() { return false; }
    @Override
    public boolean canView() { return true; }
}