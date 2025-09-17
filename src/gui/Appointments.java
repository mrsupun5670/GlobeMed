package gui;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mediator.AppointmentMediator;
import model.Appointment;
import model.Doctor;
import model.Patient;
import model.Role;
import util.DBData;

public class Appointments extends javax.swing.JPanel {

   private AppointmentMediator mediator;
   private model.User currentUser;

 public Appointments() {
     initComponents();
         loadAppointmentsFromMediator();
        setupListeners();
    }
    
    public void setCurrentUser(model.User user) {
        this.currentUser = user;
        loadAppointmentsFromMediator(); // Reload with user context
    }
public void setMediator(AppointmentMediator mediator) {
    this.mediator = mediator;
}

    public void loadAppointmentsFromMediator() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        List<Appointment> appointmentsToShow = DBData.appointments();
        
        // Filter appointments based on user role  
        if (currentUser != null && currentUser.getRole() == Role.DOCTOR) {
            appointmentsToShow = new java.util.ArrayList<>();
            for (Appointment appt : DBData.appointments()) {
                if (appt.getDoctorId().equals(currentUser.getUsername())) {
                    appointmentsToShow.add(appt);
                }
            }
        }
        
        for (Appointment appt : appointmentsToShow) {
            Patient patient = DBData.patients().get(appt.getPatientId());
            Doctor doctor = DBData.doctors().get(appt.getDoctorId());
            model.addRow(new Object[]{
                    appt.getId(),
                    appt.getPatientId() + " - " + (patient != null ? patient.getName() : ""),
                    appt.getDoctorId() + " - " + (doctor != null ? doctor.getName() : ""),
                    appt.getDepartment(),
                    appt.getDate(),
                    appt.getTime(),
                    appt.getStatus(),
                    appt.getNotes()
            });
        }
    }

    public void showSearchResults(String keyword) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        keyword = keyword.toLowerCase();
        for (Appointment appt : DBData.appointments()) {
            Patient patient = DBData.patients().get(appt.getPatientId());
            Doctor doctor = DBData.doctors().get(appt.getDoctorId());
            String patientName = (patient != null ? patient.getName() : "").toLowerCase();
            String doctorName = (doctor != null ? doctor.getName() : "").toLowerCase();

            if (appt.getId().toLowerCase().contains(keyword) ||
                    patientName.contains(keyword) ||
                    doctorName.contains(keyword)) {
                model.addRow(new Object[]{
                        appt.getId(),
                        appt.getPatientId() + " - " + (patient != null ? patient.getName() : ""),
                        appt.getDoctorId() + " - " + (doctor != null ? doctor.getName() : ""),
                        appt.getDepartment(),
                        appt.getDate(),
                        appt.getTime(),
                        appt.getStatus(),
                        appt.getNotes()
                });
            }
        }
    }

    private void setupListeners() {
        jButton1.addActionListener(e -> openAddOrUpdate(null));
        jButton2.addActionListener(e -> mediator.searchAppointments(jTextField1.getText()));

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = jTable1.getSelectedRow();
                    if (row >= 0) {
                        String appointmentId = jTable1.getValueAt(row, 0).toString();
                        Appointment appt = DBData.appointments().stream()
                                .filter(a -> a.getId().equals(appointmentId))
                                .findFirst().orElse(null);
                        if (appt != null) openAddOrUpdate(appt);
                    }
                }
            }
        });

        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    int row = jTable1.getSelectedRow();
                    if (row >= 0) {
                        String appointmentId = jTable1.getValueAt(row, 0).toString();
                        int confirm = JOptionPane.showConfirmDialog(
                                Appointments.this,
                                "Delete appointment " + appointmentId + "?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            mediator.deleteAppointment(appointmentId);
                        }
                    }
                }
            }
        });
    }

    private void openAddOrUpdate(Appointment appt) {
        String mode = (appt == null) ? "add" : "edit";
        AddOrUpdateAppointment frame = new AddOrUpdateAppointment(mode, appt, mediator);
        frame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Appointments");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Add New Appointment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(24, 24, 24))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AppointmentID", "PatientID", "DoctorName", "Department", "Date", "Time", "Status", "Notes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1138, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
    public void highlightResolvedConflict(String appointmentId) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().equals(appointmentId)) {
                jTable1.setRowSelectionInterval(i, i);
                JOptionPane.showMessageDialog(this, 
                    "Appointment " + appointmentId + " conflict resolved!",
                    "Conflict Resolution", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }
}
