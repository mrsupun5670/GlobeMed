package visitor;

import model.Patient;
import model.Appointment;
import model.Bill;
import java.util.ArrayList;
import java.util.List;

public class PatientSummaryReportVisitor implements MedicalDataVisitor {
    private StringBuilder report;
    private List<Patient> patients;
    private List<Appointment> appointments;
    private List<Bill> bills;
    
    public PatientSummaryReportVisitor() {
        this.report = new StringBuilder();
        this.patients = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.bills = new ArrayList<>();
    }
    
    @Override
    public void visitPatient(Patient patient) {
        patients.add(patient);
        report.append("=== PATIENT SUMMARY ===\n");
        report.append("Patient ID: ").append(patient.getId()).append("\n");
        report.append("Name: ").append(patient.getName()).append("\n");
        report.append("Age: ").append(patient.getAge()).append("\n");
        report.append("Contact: ").append(patient.getContact()).append("\n");
        if (patient.getGender() != null) {
            report.append("Gender: ").append(patient.getGender()).append("\n");
        }
        if (patient.getBloodGroup() != null) {
            report.append("Blood Group: ").append(patient.getBloodGroup()).append("\n");
        }
        if (patient.getDisease() != null) {
            report.append("Primary Condition: ").append(patient.getDisease()).append("\n");
        }
        if (patient.getMedicalHistory() != null) {
            report.append("Medical History: ").append(patient.getMedicalHistory()).append("\n");
        }
        if (patient.getTreatmentPlan() != null) {
            report.append("Treatment Plan: ").append(patient.getTreatmentPlan()).append("\n");
        }
        report.append("\n");
    }
    
    @Override
    public void visitAppointment(Appointment appointment) {
        appointments.add(appointment);
        report.append("--- Appointment Record ---\n");
        report.append("Appointment ID: ").append(appointment.getId()).append("\n");
        report.append("Doctor ID: ").append(appointment.getDoctorId()).append("\n");
        report.append("Department: ").append(appointment.getDepartment()).append("\n");
        report.append("Date: ").append(appointment.getDate()).append("\n");
        report.append("Time: ").append(appointment.getTime()).append("\n");
        report.append("Status: ").append(appointment.getStatus()).append("\n");
        if (appointment.getNotes() != null && !appointment.getNotes().trim().isEmpty()) {
            report.append("Notes: ").append(appointment.getNotes()).append("\n");
        }
        report.append("\n");
    }
    
    @Override
    public void visitBill(Bill bill) {
        bills.add(bill);
        report.append("--- Billing Record ---\n");
        report.append("Bill ID: ").append(bill.getId()).append("\n");
        report.append("Service: ").append(bill.getService()).append("\n");
        report.append("Total Amount: Rs.").append(String.format("%.2f", bill.getTotalAmount())).append("\n");
        report.append("Paid Amount: Rs.").append(String.format("%.2f", bill.getPaidAmount())).append("\n");
        report.append("Status: ").append(bill.getStatus()).append("\n");
        report.append("Payment Method: ").append(bill.getPaymentType()).append("\n");
        report.append("\n");
    }
    
    @Override
    public String getReport() {
        StringBuilder finalReport = new StringBuilder();
        finalReport.append("PATIENT SUMMARY REPORT\n");
        finalReport.append("Generated on: ").append(new java.util.Date()).append("\n");
        finalReport.append("==================================================\n\n");
        
        finalReport.append("STATISTICS:\n");
        finalReport.append("Total Patients: ").append(patients.size()).append("\n");
        finalReport.append("Total Appointments: ").append(appointments.size()).append("\n");
        finalReport.append("Total Bills: ").append(bills.size()).append("\n\n");
        
        finalReport.append(report.toString());
        
        return finalReport.toString();
    }
    
    @Override
    public void reset() {
        report = new StringBuilder();
        patients.clear();
        appointments.clear();
        bills.clear();
    }
}