package composite;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class PermissionManager {
    private Map<String, PermissionComponent> roles;
    private Map<String, String> userRoles;
    
    public PermissionManager() {
        this.roles = new HashMap<>();
        this.userRoles = new HashMap<>();
        initializeDefaultRoles();
    }
    
    private void initializeDefaultRoles() {
        Role doctorRole = new Role("Doctor");
        doctorRole.add(new Permission("View Patient Records"));
        doctorRole.add(new Permission("Update Patient Records"));
        doctorRole.add(new Permission("Prescribe Medicine"));
        doctorRole.add(new Permission("Schedule Appointments"));
        doctorRole.add(new Permission("View Medical Reports"));
        roles.put("Doctor", doctorRole);
        
        Role nurseRole = new Role("Nurse");
        nurseRole.add(new Permission("View Patient Records"));
        nurseRole.add(new Permission("Update Patient Records"));
        nurseRole.add(new Permission("Schedule Appointments"));
        nurseRole.add(new Permission("Assist in Medical Procedures"));
        roles.put("Nurse", nurseRole);
        
        Role pharmacistRole = new Role("Pharmacist");
        pharmacistRole.add(new Permission("View Prescriptions"));
        pharmacistRole.add(new Permission("Dispense Medicine"));
        pharmacistRole.add(new Permission("Update Inventory"));
        roles.put("Pharmacist", pharmacistRole);
        
        Role adminRole = new Role("Admin");
        adminRole.add(new Permission("View Patient Records"));
        adminRole.add(new Permission("Update Patient Records"));
        adminRole.add(new Permission("Delete Patient Records"));
        adminRole.add(new Permission("Manage Staff"));
        adminRole.add(new Permission("View Financial Reports"));
        adminRole.add(new Permission("Manage System Settings"));
        roles.put("Admin", adminRole);
        
        Role seniorDoctorRole = new Role("Senior Doctor");
        seniorDoctorRole.add(doctorRole);
        seniorDoctorRole.add(new Permission("Review Junior Doctor Cases"));
        seniorDoctorRole.add(new Permission("Approve Complex Treatments"));
        roles.put("Senior Doctor", seniorDoctorRole);
    }
    
    public void assignRoleToUser(String username, String roleName) {
        if (roles.containsKey(roleName)) {
            userRoles.put(username, roleName);
            JOptionPane.showMessageDialog(null, 
                "Role '" + roleName + "' assigned to user: " + username,
                "Role Assignment", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Role '" + roleName + "' does not exist!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean checkPermission(String username, String permission) {
        String roleName = userRoles.get(username);
        if (roleName == null) {
            return false;
        }
        
        PermissionComponent role = roles.get(roleName);
        return role != null && role.hasPermission(permission);
    }
    
    public void displayUserPermissions(String username) {
        String roleName = userRoles.get(username);
        if (roleName == null) {
            JOptionPane.showMessageDialog(null, 
                "No role assigned to user: " + username,
                "No Role", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PermissionComponent role = roles.get(roleName);
        if (role != null) {
            StringBuilder permissionList = new StringBuilder();
            permissionList.append("User: ").append(username).append("\n");
            permissionList.append("Role: ").append(roleName).append("\n\n");
            permissionList.append("Permissions:\n");
            
            for (String permission : role.getPermissions()) {
                permissionList.append("- ").append(permission).append("\n");
            }
            
            JOptionPane.showMessageDialog(null, 
                permissionList.toString(),
                "User Permissions", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void createCustomRole(String roleName, String[] permissions) {
        Role customRole = new Role(roleName);
        for (String permission : permissions) {
            customRole.add(new Permission(permission));
        }
        roles.put(roleName, customRole);
        
        JOptionPane.showMessageDialog(null, 
            "Custom role '" + roleName + "' created with " + permissions.length + " permissions",
            "Role Created", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void displayRoleStructure(String roleName) {
        PermissionComponent role = roles.get(roleName);
        if (role != null) {
            System.out.println("Role Structure for: " + roleName);
            role.displayStructure(0);
        }
    }
    
    public boolean enforcePermission(String username, String requiredPermission) {
        if (!checkPermission(username, requiredPermission)) {
            JOptionPane.showMessageDialog(null, 
                "Access Denied!\n" +
                "User: " + username + "\n" +
                "Required Permission: " + requiredPermission + "\n" +
                "Your current role does not have this permission.",
                "Access Denied", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public String getUserRole(String username) {
        return userRoles.get(username);
    }
    
    public PermissionComponent getRole(String roleName) {
        return roles.get(roleName);
    }
}