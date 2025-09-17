package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import util.DBData;

public class Dashboard extends javax.swing.JPanel {

    public Dashboard() {
        initComponents();
    }
    
    private void createRuntimeDashboard() {
        // Remove the current layout and components
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 248, 255));
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.setPreferredSize(new Dimension(1250, 60));
        
        JLabel titleLabel = new JLabel("GlobeMed - Medical Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Statistics Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(new Color(240, 248, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Create stat cards
        JPanel patientsCard = createStatCard("Total Patients", String.valueOf(DBData.patients().size()), new Color(46, 125, 50));
        JPanel appointmentsCard = createStatCard("Total Appointments", String.valueOf(DBData.appointments().size()), new Color(25, 118, 210));
        JPanel doctorsCard = createStatCard("Available Doctors", String.valueOf(DBData.doctors().size()), new Color(156, 39, 176));
        JPanel billsCard = createStatCard("Pending Bills", String.valueOf(DBData.bills().size()), new Color(255, 152, 0));
        
        statsPanel.add(patientsCard);
        statsPanel.add(appointmentsCard);
        statsPanel.add(doctorsCard);
        statsPanel.add(billsCard);
        
        // Quick Actions Panel
        JPanel actionsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        actionsPanel.setBackground(new Color(240, 248, 255));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));
        actionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2), 
                "Quick Actions", 0, 0, new Font("Segoe UI", Font.BOLD, 16), new Color(70, 130, 180)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JButton newPatientBtn = createActionButton("New Patient", "Add a new patient", new Color(46, 125, 50));
        JButton newAppointmentBtn = createActionButton("New Appointment", "Schedule appointment", new Color(25, 118, 210));
        JButton viewReportsBtn = createActionButton("Medical Reports", "Generate reports", new Color(156, 39, 176));
        JButton manageBillsBtn = createActionButton("Billing", "Manage payments", new Color(255, 152, 0));
        JButton manageStaffBtn = createActionButton("Staff Management", "Manage hospital staff", new Color(76, 175, 80));
        JButton viewRecordsBtn = createActionButton("Patient Records", "View all records", new Color(233, 30, 99));
        
        actionsPanel.add(newPatientBtn);
        actionsPanel.add(newAppointmentBtn);
        actionsPanel.add(viewReportsBtn);
        actionsPanel.add(manageBillsBtn);
        actionsPanel.add(manageStaffBtn);
        actionsPanel.add(viewRecordsBtn);
        
        // Recent Activities Panel
        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBackground(Color.WHITE);
        recentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2), 
                "Recent Activities", 0, 0, new Font("Segoe UI", Font.BOLD, 16), new Color(70, 130, 180)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        String[] columnNames = {"Time", "Activity", "Details"};
        Object[][] data = {
            {"09:30", "New Patient", "John Doe registered"},
            {"10:15", "Appointment", "Dr. Silva - Cardiology"},
            {"11:00", "Payment", "Bill #B001 paid"},
            {"11:45", "Report", "Lab results updated"},
            {"12:30", "Appointment", "Dr. Perera - Surgery"}
        };
        
        JTable recentTable = new JTable(data, columnNames);
        recentTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        recentTable.setRowHeight(25);
        recentTable.setBackground(Color.WHITE);
        recentTable.getTableHeader().setBackground(new Color(70, 130, 180));
        recentTable.getTableHeader().setForeground(Color.WHITE);
        recentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(recentTable);
        scrollPane.setPreferredSize(new Dimension(600, 150));
        recentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Bottom Panel with Recent Activities
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 248, 255));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
        bottomPanel.add(recentPanel, BorderLayout.CENTER);
        
        // Add all panels to main layout
        this.add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.add(statsPanel, BorderLayout.NORTH);
        centerPanel.add(actionsPanel, BorderLayout.CENTER);
        
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        
        this.revalidate();
        this.repaint();
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(200, 100));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel titleLabelCard = new JLabel(title);
        titleLabelCard.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabelCard.setForeground(new Color(100, 100, 100));
        titleLabelCard.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(titleLabelCard, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JButton createActionButton(String title, String description, Color color) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        button.setPreferredSize(new Dimension(200, 80));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        descLabel.setForeground(new Color(100, 100, 100));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        button.add(titleLabel, BorderLayout.CENTER);
        button.add(descLabel, BorderLayout.SOUTH);
        
        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(245, 245, 245));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });
        
        return button;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/10173282_8496.jpg"))); // NOI18N
        add(jLabel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
