package visitor;

import model.Patient;
import model.Appointment;
import model.Bill;
import model.AppointmentStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class DiagnosticReportVisitor implements MedicalDataVisitor {
    private Map<String, Integer> diseaseFrequency;
    private Map<String, Integer> departmentActivity;
    private Map<AppointmentStatus, Integer> appointmentStatusCount;
    private List<String> criticalCases;
    private int totalPatients;
    
    public DiagnosticReportVisitor() {
        reset();
    }
    
    @Override
    public void visitPatient(Patient patient) {
        totalPatients++;
        
        if (patient.getDisease() != null && !patient.getDisease().trim().isEmpty()) {
            String disease = patient.getDisease().trim();
            diseaseFrequency.put(disease, 
                diseaseFrequency.getOrDefault(disease, 0) + 1);
                
            if (isCriticalCondition(disease)) {
                criticalCases.add("Patient " + patient.getId() + " (" + patient.getName() + 
                    ") - " + disease);
            }
        }
    }
    
    @Override
    public void visitAppointment(Appointment appointment) {
        if (appointment.getDepartment() != null) {
            String department = appointment.getDepartment();
            departmentActivity.put(department, 
                departmentActivity.getOrDefault(department, 0) + 1);
        }
        
        AppointmentStatus status = appointment.getStatus();
        if (status != null) {
            appointmentStatusCount.put(status, 
                appointmentStatusCount.getOrDefault(status, 0) + 1);
        }
    }
    
    @Override
    public void visitBill(Bill bill) {
        
    }
    
    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append("DIAGNOSTIC & MEDICAL ANALYSIS REPORT\n");
        report.append("Generated on: ").append(java.time.LocalDateTime.now()).append("\n");
        report.append("=".repeat(45)).append("\n\n");
        
        report.append("PATIENT STATISTICS:\n");
        report.append("Total Patients Analyzed: ").append(totalPatients).append("\n\n");
        
        report.append("DISEASE FREQUENCY ANALYSIS:\n");
        diseaseFrequency.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .forEach(entry -> {
                double percentage = (entry.getValue() * 100.0) / totalPatients;
                report.append("  ").append(entry.getKey()).append(": ")
                      .append(entry.getValue()).append(" cases (")
                      .append(String.format("%.1f", percentage)).append("%)\n");
            });
        report.append("\n");
        
        report.append("DEPARTMENT ACTIVITY:\n");
        departmentActivity.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .forEach(entry -> {
                report.append("  ").append(entry.getKey()).append(": ")
                      .append(entry.getValue()).append(" appointments\n");
            });
        report.append("\n");
        
        report.append("APPOINTMENT STATUS DISTRIBUTION:\n");
        for (Map.Entry<AppointmentStatus, Integer> entry : appointmentStatusCount.entrySet()) {
            report.append("  ").append(entry.getKey()).append(": ")
                  .append(entry.getValue()).append(" appointments\n");
        }
        report.append("\n");
        
        if (!criticalCases.isEmpty()) {
            report.append("CRITICAL CASES ALERT:\n");
            for (String criticalCase : criticalCases) {
                report.append("  ⚠ ").append(criticalCase).append("\n");
            }
            report.append("\n");
        }
        
        return report.toString();
    }
    
    @Override
    public void reset() {
        diseaseFrequency = new HashMap<>();
        departmentActivity = new HashMap<>();
        appointmentStatusCount = new HashMap<>();
        criticalCases = new ArrayList<>();
        totalPatients = 0;
    }
    
    private boolean isCriticalCondition(String disease) {
        String[] criticalConditions = {
            "Heart Attack", "Stroke", "Cancer", "Severe Pneumonia", 
            "Acute Kidney Failure", "Sepsis", "Brain Tumor"
        };
        
        for (String critical : criticalConditions) {
            if (disease.toLowerCase().contains(critical.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}