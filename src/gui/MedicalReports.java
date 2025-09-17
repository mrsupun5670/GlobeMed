/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import visitor.MedicalReportGenerator;
import util.DBData;

public class MedicalReports extends javax.swing.JPanel {

    public MedicalReports() {
        initComponents();
        setupNetBeansComponents();
    }
    
    private void setupNetBeansComponents() {
        // Connect NetBeans components to the report functionality
        
        // Set up the text area with initial content
        jTextArea1.setText("Select a report type and click 'Generate Report' to view medical reports here.\n\n" +
                          "Available Report Types:\n" +
                          "• Patient Summary Report - Complete overview of all patients\n" +
                          "• Financial Report - Revenue and billing information\n" +
                          "• Diagnostic Report - Medical diagnosis analysis\n" +
                          "• Custom Patient Report - Individual patient details (requires Patient ID)");
        
        // Set up action listeners for NetBeans buttons
        setupButtonListeners();
    }
    
    private void setupButtonListeners() {
        // Remove existing listeners and add new ones
        for (java.awt.event.ActionListener al : jButton1.getActionListeners()) {
            jButton1.removeActionListener(al);
        }
        jButton1.addActionListener(this::generateReportNetBeans);
        
        for (java.awt.event.ActionListener al : jButton2.getActionListeners()) {
            jButton2.removeActionListener(al);
        }
        jButton2.addActionListener(this::exportToPDFNetBeans);
        
        for (java.awt.event.ActionListener al : jButton3.getActionListeners()) {
            jButton3.removeActionListener(al);
        }
        jButton3.addActionListener(this::printReportNetBeans);
        
        for (java.awt.event.ActionListener al : jButton4.getActionListeners()) {
            jButton4.removeActionListener(al);
        }
        jButton4.addActionListener(this::clearReportNetBeans);
    }
    
    private void generateReportNetBeans(ActionEvent evt) {
        String selectedReport = (String) jComboBox1.getSelectedItem();
        String patientId = jTextField1.getText().trim();
        
        try {
            String reportContent = "";
            
            switch (selectedReport) {
                case "Patient Summary Report":
                    reportContent = generatePatientSummaryReportContent();
                    break;
                case "Finacial Report":
                    reportContent = generateFinancialReportContent();
                    break;
                case "Diagnostic Report":
                    reportContent = generateDiagnosticReportContent();
                    break;
                case "Custom Patient Report":
                    if (patientId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter a Patient ID for custom reports", 
                                                    "Patient ID Required", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (!DBData.patients().containsKey(patientId)) {
                        JOptionPane.showMessageDialog(this, "Patient ID '" + patientId + "' not found!", 
                                                    "Patient Not Found", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    reportContent = generateCustomPatientReportContent(patientId);
                    break;
            }
            
            jTextArea1.setText(reportContent);
            jTextArea1.setCaretPosition(0); // Scroll to top
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String generatePatientSummaryReportContent() {
        visitor.PatientSummaryReportVisitor visitor = new visitor.PatientSummaryReportVisitor();
        
        for (model.Patient patient : DBData.patients().values()) {
            visitor.visitPatient(patient);
            
            for (model.Appointment appointment : DBData.appointments()) {
                if (appointment.getPatientId().equals(patient.getId())) {
                    visitor.visitAppointment(appointment);
                }
            }
            
            for (model.Bill bill : DBData.bills()) {
                if (bill.getPatientId().equals(patient.getId())) {
                    visitor.visitBill(bill);
                }
            }
        }
        
        return visitor.getReport();
    }
    
    private String generateFinancialReportContent() {
        visitor.FinancialReportVisitor visitor = new visitor.FinancialReportVisitor();
        
        for (model.Bill bill : DBData.bills()) {
            visitor.visitBill(bill);
        }
        
        return visitor.getReport();
    }
    
    private String generateDiagnosticReportContent() {
        visitor.DiagnosticReportVisitor visitor = new visitor.DiagnosticReportVisitor();
        
        for (model.Patient patient : DBData.patients().values()) {
            visitor.visitPatient(patient);
        }
        
        for (model.Appointment appointment : DBData.appointments()) {
            visitor.visitAppointment(appointment);
        }
        
        return visitor.getReport();
    }
    
    private String generateCustomPatientReportContent(String patientId) {
        visitor.PatientSummaryReportVisitor visitor = new visitor.PatientSummaryReportVisitor();
        
        model.Patient patient = DBData.patients().get(patientId);
        visitor.visitPatient(patient);
        
        for (model.Appointment appointment : DBData.appointments()) {
            if (appointment.getPatientId().equals(patientId)) {
                visitor.visitAppointment(appointment);
            }
        }
        
        for (model.Bill bill : DBData.bills()) {
            if (bill.getPatientId().equals(patientId)) {
                visitor.visitBill(bill);
            }
        }
        
        return visitor.getReport();
    }
    
    private void clearReportNetBeans(ActionEvent evt) {
        jTextArea1.setText("Report cleared. Select a report type and generate a new report.");
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(0);
    }
    
    private void exportToPDFNetBeans(ActionEvent evt) {
        String reportContent = jTextArea1.getText();
        if (reportContent == null || reportContent.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please generate a report first before exporting to PDF!", 
                                        "No Report to Export", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report as PDF");
        fileChooser.setSelectedFile(new java.io.File("Medical_Report.pdf"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            
            // Simulate PDF export
            JOptionPane.showMessageDialog(this, 
                "PDF export functionality would save the report to:\n" + fileToSave.getAbsolutePath() + 
                "\n\nIn a real implementation, this would create a PDF file with the report content.", 
                "PDF Export", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void printReportNetBeans(ActionEvent evt) {
        String reportContent = jTextArea1.getText();
        if (reportContent == null || reportContent.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please generate a report first before printing!", 
                                        "No Report to Print", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Simple print functionality using Java's built-in print support
            boolean printed = jTextArea1.print();
            if (printed) {
                JOptionPane.showMessageDialog(this, "Report sent to printer successfully!", 
                                            "Print Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Print operation was cancelled.", 
                                            "Print Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error printing report: " + e.getMessage(), 
                                        "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel2.setText("Report Type :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Patient Summary Report", "Finacial Report", "Diagnostic Report", "Custom Patient Report" }));

        jLabel3.setText("Patient Id :");

        jButton1.setText("Generate Report ");

        jLabel4.setText("Report Content");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("Export to PDF");

        jButton3.setText("Print Report");

        jButton4.setText("Clear Report");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(47, 47, 47)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(452, 452, 452)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)))
                        .addGap(0, 479, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

}
