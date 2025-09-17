package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.StaffModel;
import composite.Permission;
import composite.PermissionComponent;
import composite.Role;

public class Staff extends javax.swing.JPanel {

    private List<StaffModel> staffList = new ArrayList<>();
    private List<JCheckBox> permissionCheckBoxes = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();
    private Map<String, Role> roleMap = new HashMap<>();

    // Runtime design components
    private JTextField txtStaffId;
    private JTextField txtStaffName;
    private JTextField txtContact;
    private JComboBox<String> cmbRole;
    private JComboBox<String> cmbDepartment;
    private JComboBox<String> cmbSpecification;
    private List<JCheckBox> runtimePermissionBoxes = new ArrayList<>();
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable staffTable;

    public Staff() {
        initRoles();
        initComponents();
        loadRoles();
        setupComponentReferences();
        loadExistingStaffData();
    }
    
    private void loadExistingStaffData() {
        // Load existing staff data from DBData and convert to local staffList
        staffList.clear();
        
        for (model.StaffModel dbStaff : util.DBData.staff()) {
            // Create a new StaffModel for the local list
            StaffModel localStaff = new StaffModel(dbStaff.getId(), dbStaff.getName(), dbStaff.getRole());
            
            // Copy permissions if they exist
            if (dbStaff.getPermissions() != null) {
                localStaff.setPermissions(new ArrayList<>(dbStaff.getPermissions()));
            }
            
            staffList.add(localStaff);
        }
        
        // Refresh the table to show loaded data
        refreshTable();
    }
    
    private void createRuntimeStaffDesign() {
        // Remove old design and create new runtime design
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(248, 252, 255));
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setPreferredSize(new Dimension(1250, 60));
        
        JLabel titleLabel = new JLabel("Staff Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(248, 252, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Left Panel - Staff Input Form
        JPanel leftPanel = createStaffInputPanel();
        leftPanel.setPreferredSize(new Dimension(500, 500));
        
        // Right Panel - Staff Table
        JPanel rightPanel = createStaffTablePanel();
        
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        // Add panels to main layout
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        
        this.revalidate();
        this.repaint();
    }
    
    private JPanel createStaffInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2), 
                "Staff Information", 0, 0, new Font("Segoe UI", Font.BOLD, 16), new Color(70, 130, 180)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 12);
        Color labelColor = new Color(70, 130, 180);
        
        // Staff ID
        panel.add(createFieldPanel("Staff ID:", txtStaffId = createTextField(inputFont), labelFont, labelColor));
        panel.add(Box.createVerticalStrut(15));
        
        // Staff Name
        panel.add(createFieldPanel("Full Name:", txtStaffName = createTextField(inputFont), labelFont, labelColor));
        panel.add(Box.createVerticalStrut(15));
        
        // Role
        cmbRole = new JComboBox<>(new String[]{"Doctor", "Nurse", "Admin"});
        cmbRole.setFont(inputFont);
        cmbRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updatePermissionsForRole();
            }
        });
        panel.add(createFieldPanel("Staff Role:", cmbRole, labelFont, labelColor));
        panel.add(Box.createVerticalStrut(15));
        
        // Department
        cmbDepartment = new JComboBox<>(new String[]{"Cardiology", "Neurology", "Surgery", "Diagnostics"});
        cmbDepartment.setFont(inputFont);
        panel.add(createFieldPanel("Department:", cmbDepartment, labelFont, labelColor));
        panel.add(Box.createVerticalStrut(15));
        
        // Specification
        cmbSpecification = new JComboBox<>(new String[]{"Consultation", "Surgery", "Diagnostics", "Follow-up"});
        cmbSpecification.setFont(inputFont);
        panel.add(createFieldPanel("Specification:", cmbSpecification, labelFont, labelColor));
        panel.add(Box.createVerticalStrut(15));
        
        // Contact
        panel.add(createFieldPanel("Contact:", txtContact = createTextField(inputFont), labelFont, labelColor));
        panel.add(Box.createVerticalStrut(20));
        
        // Permissions Panel
        JPanel permissionsPanel = createPermissionsPanel();
        panel.add(permissionsPanel);
        panel.add(Box.createVerticalStrut(20));
        
        // Buttons Panel
        JPanel buttonsPanel = createButtonsPanel();
        panel.add(buttonsPanel);
        
        return panel;
    }
    
    private JTextField createTextField(Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(300, 35));
        field.setMaximumSize(new Dimension(300, 35));
        return field;
    }
    
    private JPanel createFieldPanel(String labelText, JComponent component, Font labelFont, Color labelColor) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(450, 40));
        
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(labelColor);
        label.setPreferredSize(new Dimension(120, 30));
        
        panel.add(label);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(component);
        
        return panel;
    }
    
    private JPanel createPermissionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70, 130, 180)), 
                                                        "Permissions"));
        
        String[] permissions = {"View Reports", "Update Patients", "Delete Patients", "Prescribe Medicines", 
                               "Bill Payments", "View Patients", "Manage Appointments"};
        
        runtimePermissionBoxes.clear();
        JPanel checkboxGrid = new JPanel(new GridLayout(3, 3, 10, 5));
        checkboxGrid.setBackground(Color.WHITE);
        
        for (String perm : permissions) {
            JCheckBox checkbox = new JCheckBox(perm);
            checkbox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            checkbox.setBackground(Color.WHITE);
            runtimePermissionBoxes.add(checkbox);
            checkboxGrid.add(checkbox);
        }
        
        panel.add(checkboxGrid);
        return panel;
    }
    
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.WHITE);
        
        btnAdd = createStyledButton("Add Staff", new Color(46, 125, 50));
        btnUpdate = createStyledButton("Update Staff", new Color(25, 118, 210));
        btnDelete = createStyledButton("Delete Staff", new Color(244, 67, 54));
        btnClear = createStyledButton("Clear Form", new Color(156, 39, 176));
        
        btnAdd.addActionListener(this::addStaff);
        btnUpdate.addActionListener(this::updateStaff);
        btnDelete.addActionListener(this::deleteStaff);
        btnClear.addActionListener(this::clearFields);
        
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(110, 35));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private JPanel createStaffTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2), 
                "Staff Records", 0, 0, new Font("Segoe UI", Font.BOLD, 16), new Color(70, 130, 180)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Create table with data
        String[] columnNames = {"ID", "Name", "Role", "Department", "Contact", "Permissions"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        staffTable = new JTable(model);
        staffTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        staffTable.setRowHeight(25);
        staffTable.setGridColor(new Color(230, 230, 230));
        staffTable.setSelectionBackground(new Color(70, 130, 180));
        staffTable.setSelectionForeground(Color.WHITE);
        
        // Style table header
        staffTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        staffTable.getTableHeader().setBackground(new Color(70, 130, 180));
        staffTable.getTableHeader().setForeground(Color.WHITE);
        
        // Add click listener for table selection
        staffTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fillFormFromTable();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void updatePermissionsForRole() {
        String selectedRole = (String) cmbRole.getSelectedItem();
        
        // Clear all checkboxes first
        for (JCheckBox cb : runtimePermissionBoxes) {
            cb.setSelected(false);
        }
        
        // Set permissions based on role
        if ("Doctor".equals(selectedRole)) {
            setPermission("View Reports", true);
            setPermission("Update Patients", true);
            setPermission("Prescribe Medicines", true);
        } else if ("Nurse".equals(selectedRole)) {
            setPermission("View Patients", true);
            setPermission("Update Patients", true);
        } else if ("Admin".equals(selectedRole)) {
            setPermission("Bill Payments", true);
            setPermission("Manage Appointments", true);
            setPermission("View Reports", true);
        }
    }
    
    private void setPermission(String permissionName, boolean selected) {
        for (JCheckBox cb : runtimePermissionBoxes) {
            if (cb.getText().equals(permissionName)) {
                cb.setSelected(selected);
                break;
            }
        }
    }
    
    private void fillFormFromTable() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtStaffId.setText((String) staffTable.getValueAt(selectedRow, 0));
            txtStaffId.setEditable(false);
            txtStaffName.setText((String) staffTable.getValueAt(selectedRow, 1));
            cmbRole.setSelectedItem(staffTable.getValueAt(selectedRow, 2));
            cmbDepartment.setSelectedItem(staffTable.getValueAt(selectedRow, 3));
            txtContact.setText((String) staffTable.getValueAt(selectedRow, 4));
            
            // Update permissions based on role
            updatePermissionsForRole();
        }
    }
    
    private List<PermissionComponent> getSelectedRuntimePermissions() {
        List<PermissionComponent> list = new ArrayList<>();
        for (JCheckBox cb : runtimePermissionBoxes) {
            if (cb.isSelected()) {
                list.add(new Permission(cb.getText()));
            }
        }
        return list;
    }
    
    private void refreshRuntimeTable() {
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        model.setRowCount(0);
        for (StaffModel s : staffList) {
            model.addRow(new Object[]{
                s.getId(),
                s.getName(),
                s.getRole() != null ? s.getRole().getName() : "",
                cmbDepartment.getSelectedItem(), // We'll store this info later
                txtContact.getText(), // For now, using current contact
                s.getPermissionsString()
            });
        }
    }
    
    private void clearRuntimeFields() {
        txtStaffId.setText("");
        txtStaffId.setEditable(true);
        txtStaffName.setText("");
        txtContact.setText("");
        cmbRole.setSelectedIndex(0);
        cmbDepartment.setSelectedIndex(0);
        cmbSpecification.setSelectedIndex(0);
        
        for (JCheckBox cb : runtimePermissionBoxes) {
            cb.setSelected(false);
        }
    }
    
    private void setupComponentReferences() {
        // Map old component references to new runtime components for compatibility
        permissionCheckBoxes.clear();
        
        // Add the old NetBeans checkboxes to the permissionCheckBoxes list
        permissionCheckBoxes.add(jCheckBox1);
        permissionCheckBoxes.add(jCheckBox2);
        permissionCheckBoxes.add(jCheckBox3);
        permissionCheckBoxes.add(jCheckBox4);
        permissionCheckBoxes.add(jCheckBox6);
        permissionCheckBoxes.add(jCheckBox7);
        permissionCheckBoxes.add(jCheckBox8);
        
        // Also add runtime permission boxes if they exist
        permissionCheckBoxes.addAll(runtimePermissionBoxes);
        
        // Set up button action listeners to bridge old and new components
        if (jButton1 != null) {
            // Remove existing listeners first
            for (java.awt.event.ActionListener al : jButton1.getActionListeners()) {
                jButton1.removeActionListener(al);
            }
            jButton1.addActionListener(e -> {
                try {
                    addStaffWithOldComponents();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error adding staff: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        
        if (jButton2 != null) {
            for (java.awt.event.ActionListener al : jButton2.getActionListeners()) {
                jButton2.removeActionListener(al);
            }
            jButton2.addActionListener(e -> deleteStaffWithOldComponents());
        }
        
        if (jButton3 != null) {
            for (java.awt.event.ActionListener al : jButton3.getActionListeners()) {
                jButton3.removeActionListener(al);
            }
            jButton3.addActionListener(e -> updateStaffWithOldComponents());
        }
        
        if (jButton4 != null) {
            for (java.awt.event.ActionListener al : jButton4.getActionListeners()) {
                jButton4.removeActionListener(al);
            }
            jButton4.addActionListener(e -> clearFields());
        }
        
        // Add table click listener for prefilling fields
        prefillFields();
    }
    
    private void addStaffWithOldComponents() {
        String id = pi.getText().trim();
        String name = pi1.getText().trim();
        String roleName = (String) jComboBox1.getSelectedItem();
        String contact = pi7.getText().trim();
        
        if (id.isEmpty() || name.isEmpty() || roleName == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!", 
                                        "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check if ID already exists
        for (StaffModel existing : staffList) {
            if (existing.getId().equals(id)) {
                JOptionPane.showMessageDialog(this, "Staff ID already exists!", 
                                            "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Role role = roleMap.get(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleMap.put(roleName, role);
        }
        
        StaffModel staff = new StaffModel(id, name, role);
        staff.setPermissions(getSelectedPermissions());
        staffList.add(staff);

        refreshTable();
        clearFields();
        
        JOptionPane.showMessageDialog(this, "Staff member added successfully!", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateStaffWithOldComponents() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to update!", 
                                        "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StaffModel staff = staffList.get(row);
        staff.setPermissions(getSelectedPermissions());
        staff.setName(pi1.getText().trim());

        String roleName = (String) jComboBox1.getSelectedItem();
        Role role = roleMap.get(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleMap.put(roleName, role);
        }
        staff.setRole(role);

        refreshTable();
        clearFields();
        
        JOptionPane.showMessageDialog(this, "Staff member updated successfully!", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void deleteStaffWithOldComponents() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to delete!", 
                                        "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this staff member?",
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            staffList.remove(row);
            refreshTable();
            clearFields();
            
            JOptionPane.showMessageDialog(this, "Staff member deleted successfully!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void enhanceRuntimeDesign() {
        // Set overall background
        this.setBackground(new Color(248, 252, 255));
        
        // Update title styling
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 28));
        jLabel1.setForeground(new Color(70, 130, 180));
        
        // Style input labels
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = new Color(70, 130, 180);
        
        jLabel2.setFont(labelFont);
        jLabel2.setForeground(labelColor);
        jLabel7.setFont(labelFont);
        jLabel7.setForeground(labelColor);
        jLabel8.setFont(labelFont);
        jLabel8.setForeground(labelColor);
        jLabel9.setFont(labelFont);
        jLabel9.setForeground(labelColor);
        jLabel10.setFont(labelFont);
        jLabel10.setForeground(labelColor);
        jLabel11.setFont(labelFont);
        jLabel11.setForeground(labelColor);
        jLabel12.setFont(labelFont);
        jLabel12.setForeground(labelColor);
        
        // Style input fields
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 12);
        pi.setFont(inputFont);
        pi1.setFont(inputFont);
        pi7.setFont(inputFont);
        
        // Style combo boxes
        jComboBox1.setFont(inputFont);
        jComboBox3.setFont(inputFont);
        jComboBox4.setFont(inputFont);
        
        // Style checkboxes
        Font checkboxFont = new Font("Segoe UI", Font.PLAIN, 12);
        jCheckBox1.setFont(checkboxFont);
        jCheckBox1.setBackground(new Color(248, 252, 255));
        jCheckBox2.setFont(checkboxFont);
        jCheckBox2.setBackground(new Color(248, 252, 255));
        jCheckBox3.setFont(checkboxFont);
        jCheckBox3.setBackground(new Color(248, 252, 255));
        jCheckBox4.setFont(checkboxFont);
        jCheckBox4.setBackground(new Color(248, 252, 255));
        jCheckBox6.setFont(checkboxFont);
        jCheckBox6.setBackground(new Color(248, 252, 255));
        jCheckBox7.setFont(checkboxFont);
        jCheckBox7.setBackground(new Color(248, 252, 255));
        jCheckBox8.setFont(checkboxFont);
        jCheckBox8.setBackground(new Color(248, 252, 255));
        
        // Style buttons
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        
        jButton1.setFont(buttonFont);
        jButton1.setBackground(new Color(46, 125, 50));
        jButton1.setForeground(Color.WHITE);
        jButton1.setBorderPainted(false);
        jButton1.setFocusPainted(false);
        
        jButton2.setFont(buttonFont);
        jButton2.setBackground(new Color(244, 67, 54));
        jButton2.setForeground(Color.WHITE);
        jButton2.setBorderPainted(false);
        jButton2.setFocusPainted(false);
        
        jButton3.setFont(buttonFont);
        jButton3.setBackground(new Color(25, 118, 210));
        jButton3.setForeground(Color.WHITE);
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);
        
        // Add hover effects to buttons
        addButtonHoverEffect(jButton1, new Color(46, 125, 50));
        addButtonHoverEffect(jButton2, new Color(244, 67, 54));
        addButtonHoverEffect(jButton3, new Color(25, 118, 210));
        
        // Style table
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jTable1.setRowHeight(25);
        jTable1.setGridColor(new Color(230, 230, 230));
        jTable1.setSelectionBackground(new Color(70, 130, 180));
        jTable1.setSelectionForeground(Color.WHITE);
        
        // Style table header
        if (jTable1.getTableHeader() != null) {
            jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            jTable1.getTableHeader().setBackground(new Color(70, 130, 180));
            jTable1.getTableHeader().setForeground(Color.WHITE);
        }
        
        // Style scroll pane
        jScrollPane1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2), 
                "Staff Records", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(70, 130, 180)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
    
    private void addButtonHoverEffect(JButton button, Color originalColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    private void initRoles() {
        Role doctor = new Role("Doctor");
        doctor.add(new Permission("View Reports"));
        doctor.add(new Permission("Update Patients"));
        doctor.add(new Permission("Prescribe Medicines"));

        Role nurse = new Role("Nurse");
        nurse.add(new Permission("View Patients"));
        nurse.add(new Permission("Update Patients"));

        Role admin = new Role("Admin");
        admin.add(new Permission("Bill Payments"));
        admin.add(new Permission("Manage Appointments"));
        admin.add(new Permission("View Reports"));

        roles.add(doctor);
        roles.add(nurse);
        roles.add(admin);
    }

    private void loadRoles() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        roleMap.clear();
        for (Role r : roles) {
            model.addElement(r.getName());
            roleMap.put(r.getName(), r);
        }
        jComboBox1.setModel(model);
    }

    private void autoSelectPermissions(Role role) {
        List<String> perms = role.getPermissions();
        for (JCheckBox cb : permissionCheckBoxes) {
            boolean isSelected = false;
            for (String perm : perms) {
                if (perm.equals(cb.getText())) {
                    isSelected = true;
                    break;
                }
            }
            cb.setSelected(isSelected);
        }
    }

    private List<PermissionComponent> getSelectedPermissions() {
        List<PermissionComponent> list = new ArrayList<>();
        for (JCheckBox cb : permissionCheckBoxes) {
            if (cb.isSelected()) {
                list.add(new Permission(cb.getText()));
            }
        }
        return list;
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (StaffModel s : staffList) {
            model.addRow(new Object[]{
                s.getId(),
                s.getName(),
                s.getRole() != null ? s.getRole().getName() : "",
                s.getPermissionsString()
            });
        }
    }

    private void addStaff(ActionEvent e) {
        String id = txtStaffId.getText().trim();
        String name = txtStaffName.getText().trim();
        String roleName = (String) cmbRole.getSelectedItem();
        String department = (String) cmbDepartment.getSelectedItem();
        String contact = txtContact.getText().trim();
        
        if (id.isEmpty() || name.isEmpty() || roleName == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!", 
                                        "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check if ID already exists
        for (StaffModel existing : staffList) {
            if (existing.getId().equals(id)) {
                JOptionPane.showMessageDialog(this, "Staff ID already exists!", 
                                            "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Role role = roleMap.get(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleMap.put(roleName, role);
        }
        
        StaffModel staff = new StaffModel(id, name, role);
        staff.setPermissions(getSelectedRuntimePermissions());
        staffList.add(staff);

        refreshRuntimeTable();
        clearRuntimeFields();
        
        JOptionPane.showMessageDialog(this, "Staff member added successfully!", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStaff(ActionEvent e) {
        int row = staffTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to update!", 
                                        "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StaffModel staff = staffList.get(row);
        staff.setPermissions(getSelectedRuntimePermissions());
        staff.setName(txtStaffName.getText().trim());

        String roleName = (String) cmbRole.getSelectedItem();
        Role role = roleMap.get(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleMap.put(roleName, role);
        }
        staff.setRole(role);

        refreshRuntimeTable();
        clearRuntimeFields();
        
        JOptionPane.showMessageDialog(this, "Staff member updated successfully!", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteStaff(ActionEvent e) {
        int row = staffTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to delete!", 
                                        "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this staff member?",
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            staffList.remove(row);
            refreshRuntimeTable();
            clearRuntimeFields();
            
            JOptionPane.showMessageDialog(this, "Staff member deleted successfully!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void clearFields(ActionEvent e) {
        clearRuntimeFields();
    }

    private void prefillFields() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.getSelectedRow();
                if (row == -1) {
                    return;
                }

                StaffModel staff = staffList.get(row);
                pi.setText(staff.getId());
                pi.setEditable(false);
                pi1.setText(staff.getName());
                jComboBox1.setSelectedItem(staff.getRole().getName());

                for (JCheckBox cb : permissionCheckBoxes) {
                    boolean isSelected = false;
                    for (PermissionComponent p : staff.getPermissions()) {
                        if (p.getName().equals(cb.getText())) {
                            isSelected = true;
                            break;
                        }
                    }
                    cb.setSelected(isSelected);
                }
            }
        });

    }

    private void clearFields() {
        pi.setText("");
        pi.setEditable(true);
        pi1.setText("");
        jComboBox1.setSelectedIndex(0);
        for (JCheckBox cb : permissionCheckBoxes) {
            cb.setSelected(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        pi7 = new javax.swing.JTextField();
        pi = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        pi1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Staff Management");

        pi7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pi7ActionPerformed(evt);
            }
        });

        pi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piActionPerformed(evt);
            }
        });

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Id");

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Staff Type");

        jLabel8.setText("Department");

        jLabel9.setText("Name");

        jLabel10.setText("Contact");

        jLabel11.setText("Specification");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setMinimumSize(new java.awt.Dimension(64, 22));
        jComboBox1.setPreferredSize(new java.awt.Dimension(65, 22));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Diagnostics", "Cardiology", "Neurology", "Surgery" }));
        jComboBox3.setPreferredSize(new java.awt.Dimension(65, 22));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Consultation", "Surgery", "Diagnostics", "Follow-up" }));

        pi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pi1ActionPerformed(evt);
            }
        });

        jLabel12.setText("Permissions");

        jCheckBox1.setText("View Reports");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Update Patients");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Delete Patients");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("Prescribe Medicines");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jCheckBox6.setText("Bill Payments");
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });

        jCheckBox7.setText("View Patients");
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });

        jCheckBox8.setText("Manage Appointments");
        jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox8ActionPerformed(evt);
            }
        });

        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Role", "Permissions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(pi7, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(pi, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pi1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, 224, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox4))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jCheckBox8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBox7)
                                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCheckBox6)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(28, 28, 28)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pi7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox3)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox8)
                            .addComponent(jCheckBox6)
                            .addComponent(jCheckBox7))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3)
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(359, 359, 359))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pi7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pi7ActionPerformed
        
    }//GEN-LAST:event_pi7ActionPerformed

    private void piActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piActionPerformed
        
    }//GEN-LAST:event_piActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addStaff(evt);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        deleteStaff(evt);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pi1ActionPerformed
        updateStaff(evt);
    }//GEN-LAST:event_pi1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed
        
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox8ActionPerformed
        
    }//GEN-LAST:event_jCheckBox8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField pi;
    private javax.swing.JTextField pi1;
    private javax.swing.JTextField pi7;
    // End of variables declaration//GEN-END:variables
}
