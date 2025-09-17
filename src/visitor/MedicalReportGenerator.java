package visitor;

import model.Patient;
import model.Appointment;
import model.Bill;
import util.DBData;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class MedicalReportGenerator {
    
    public static void generatePatientSummaryReport() {
        PatientSummaryReportVisitor visitor = new PatientSummaryReportVisitor();
        
        for (Patient patient : DBData.patients().values()) {
            visitor.visitPatient(patient);
            
            for (Appointment appointment : DBData.appointments()) {
                if (appointment.getPatientId().equals(patient.getId())) {
                    visitor.visitAppointment(appointment);
                }
            }
            
            for (Bill bill : DBData.bills()) {
                if (bill.getPatientId().equals(patient.getId())) {
                    visitor.visitBill(bill);
                }
            }
        }
        
        displayReport("Patient Summary Report", visitor.getReport());
    }
    
    public static void generateFinancialReport() {
        FinancialReportVisitor visitor = new FinancialReportVisitor();
        
        for (Bill bill : DBData.bills()) {
            visitor.visitBill(bill);
        }
        
        displayReport("Financial Report", visitor.getReport());
    }
    
    public static void generateDiagnosticReport() {
        DiagnosticReportVisitor visitor = new DiagnosticReportVisitor();
        
        for (Patient patient : DBData.patients().values()) {
            visitor.visitPatient(patient);
        }
        
        for (Appointment appointment : DBData.appointments()) {
            visitor.visitAppointment(appointment);
        }
        
        displayReport("Diagnostic Analysis Report", visitor.getReport());
    }
    
    public static void generateCustomReport(String patientId) {
        PatientSummaryReportVisitor visitor = new PatientSummaryReportVisitor();
        
        Patient patient = DBData.patients().get(patientId);
        if (patient == null) {
            JOptionPane.showMessageDialog(null, 
                "Patient with ID " + patientId + " not found!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        visitor.visitPatient(patient);
        
        for (Appointment appointment : DBData.appointments()) {
            if (appointment.getPatientId().equals(patientId)) {
                visitor.visitAppointment(appointment);
            }
        }
        
        for (Bill bill : DBData.bills()) {
            if (bill.getPatientId().equals(patientId)) {
                visitor.visitBill(bill);
            }
        }
        
        displayReport("Patient Report - " + patient.getName(), visitor.getReport());
    }
    
    private static void displayReport(String title, String reportContent) {
        JTextArea textArea = new JTextArea(reportContent);
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
}