  package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.*;

public class DBData {

    private static Map<String, Patient> patientData = new HashMap<>();

            static {
        Patient p1 = new Patient("P001", "Supun Gunasinghe", 30, "Male", "0772010915", "O+",
                "Diabetes", "Family history of diabetes", "Metformin 500mg daily");
        p1.setAssignedDoctorId("D001");
        patientData.put("P001", p1);

        Patient p2 = new Patient("P002", "Kasun Perera", 42, "Male", "0714567890", "A+",
                "Hypertension", "High blood pressure history", "Lifestyle changes, Atenolol 50mg");
        p2.setAssignedDoctorId("D001");
        patientData.put("P002", p2);

        Patient p3 = new Patient("P003", "Nadeesha Fernando", 35, "Female", "0723456789", "B+",
                "Asthma", "Childhood asthma", "Inhaler therapy");
        p3.setAssignedDoctorId("D002");
        patientData.put("P003", p3);

        Patient p4 = new Patient("P004", "Sahan Wijesinghe", 28, "Male", "0779876543", "AB+",
                "Migraine", "Frequent headaches", "Pain management therapy");
        p4.setAssignedDoctorId("D002");
        patientData.put("P004", p4);

        Patient p5 = new Patient("P005", "Priyanka Rajapaksha", 45, "Female", "0712345678", "B-",
                "Arthritis", "Joint pain", "Anti-inflammatory medication");
        p5.setAssignedDoctorId("D003");
        patientData.put("P005", p5);
    }

    public static Map<String, Patient> patients() {
        return patientData;
    }

    public static void addOrUpdatePatient(Patient p) {
        patientData.put(p.getId(), p);
    }

    public static void deletePatient(String patientId) {
        patientData.remove(patientId);
    }

    public static List<User> users() {
        return List.of(
                new User("admin", Role.ADMIN, "admin123"),
                new User("D001", Role.DOCTOR, "doc123"),
                new User("D002", Role.DOCTOR, "doc123"),
                new User("nurse.sahan", Role.NURSE, "nurse123"),
                new User("pharm.kasun", Role.PHARMACIST, "pharm123")
        );
    }

    private static Map<String, Doctor> doctorData = new HashMap<>();

    static {
        doctorData.put("D001", new Doctor("D001", "Dr. Chathura Silva", "Cardiology"));
        doctorData.put("D002", new Doctor("D002", "Dr. Anushka Perera", "Neurology"));
        doctorData.put("D003", new Doctor("D003", "Dr. Dilshan Fernando", "Surgery"));
        doctorData.put("D004", new Doctor("D004", "Dr. Nadeesha Jayasinghe", "Diagnostics"));
    }

    public static Map<String, Doctor> doctors() {
        return doctorData;
    }

    public static void addOrUpdateDoctor(Doctor d) {
        doctorData.put(d.getId(), d);
    }

    public static void deleteDoctor(String doctorId) {
        doctorData.remove(doctorId);
    }

    private static List<Appointment> appointmentData = new ArrayList<>();

    static {
        appointmentData.add(new Appointment("APT001", "P001", "D001", "Cardiology", "Consultation",
                "2025-09-01", "09:00", AppointmentStatus.DUE, "Initial cardiology consultation",
                "Building A - Floor 1 - Room 101"));

        appointmentData.add(new Appointment("APT002", "P002", "D001", "Cardiology", "Consultation",
                "2025-09-01", "10:30", AppointmentStatus.DONE, "Follow-up for chest pain",
                "Building A - Floor 1 - Room 102"));

        appointmentData.add(new Appointment("SURG001", "P003", "D003", "Surgery", "Surgery",
                "2025-09-02", "14:00", AppointmentStatus.DUE, "Appendix surgery scheduled",
                "Building B - Operation Theater 3"));

        appointmentData.add(new Appointment("SURG002", "P002", "D002", "Neurology", "Surgery",
                "2025-09-05", "11:00", AppointmentStatus.CANCELLED, "Brain tumor operation (cancelled)",
                "Building C - Neuro Surgery Ward"));
    }

    public static List<Appointment> appointments() {
        return appointmentData;
    }

    public static void addOrUpdateAppointment(Appointment a) {
        appointmentData.removeIf(ap -> ap.getId().equals(a.getId()));
        appointmentData.add(a);
    }

    public static void deleteAppointment(String appointmentId) {
        appointmentData.removeIf(ap -> ap.getId().equals(appointmentId));
    }
    
    private static List<Bill> billData = new ArrayList<>();

static {
    // Consultation Bills
    Bill b1 = new Bill("B001", "P001", "Consultation - Cardiology", 2500.00);
    b1.setPatientName("Supun Gunasinghe");
    b1.setStatus(BillStatus.PENDING);
    b1.setPaymentType(PaymentType.CASH);
    b1.setPaidAmount(0.00);
    billData.add(b1);

    Bill b2 = new Bill("B002", "P002", "Consultation - Cardiology", 2500.00);
    b2.setPatientName("Kasun Perera");
    b2.setStatus(BillStatus.PAID);
    b2.setPaymentType(PaymentType.INSURANCE);
    b2.setPaidAmount(2500.00);
    billData.add(b2);

    // Surgery Bills
    Bill b3 = new Bill("B003", "P003", "Surgery - Appendix Removal", 85000.00);
    b3.setPatientName("Nadeesha Fernando");
    b3.setStatus(BillStatus.PARTIAL);
    b3.setPaymentType(PaymentType.MIXED);
    b3.setPaidAmount(45000.00);
    billData.add(b3);

    // Diagnostic Bills
    Bill b4 = new Bill("B004", "P004", "CT Scan - Brain", 15000.00);
    b4.setPatientName("Sahan Wijesinghe");
    b4.setStatus(BillStatus.PENDING);
    b4.setPaymentType(PaymentType.CASH);
    b4.setPaidAmount(0.00);
    billData.add(b4);

    Bill b5 = new Bill("B005", "P005", "X-Ray - Joint Assessment", 3500.00);
    b5.setPatientName("Priyanka Rajapaksha");
    b5.setStatus(BillStatus.PAID);
    b5.setPaymentType(PaymentType.CREDIT_CARD);
    b5.setPaidAmount(3500.00);
    billData.add(b5);

    // Treatment Bills
    Bill b6 = new Bill("B006", "P001", "Blood Test - Diabetes Panel", 4500.00);
    b6.setPatientName("Supun Gunasinghe");
    b6.setStatus(BillStatus.PENDING);
    b6.setPaymentType(PaymentType.INSURANCE);
    b6.setPaidAmount(0.00);
    billData.add(b6);

    Bill b7 = new Bill("B007", "P002", "ECG Test", 2000.00);
    b7.setPatientName("Kasun Perera");
    b7.setStatus(BillStatus.PARTIAL);
    b7.setPaymentType(PaymentType.CASH);
    b7.setPaidAmount(1000.00);
    billData.add(b7);

    // Medication Bills
    Bill b8 = new Bill("B008", "P003", "Prescription Medications", 12500.00);
    b8.setPatientName("Nadeesha Fernando");
    b8.setStatus(BillStatus.PAID);
    b8.setPaymentType(PaymentType.MIXED);
    b8.setPaidAmount(12500.00);
    billData.add(b8);

    // Emergency Bills
    Bill b9 = new Bill("B009", "P004", "Emergency Treatment - Migraine", 8500.00);
    b9.setPatientName("Sahan Wijesinghe");
    b9.setStatus(BillStatus.PENDING);
    b9.setPaymentType(PaymentType.CREDIT_CARD);
    b9.setPaidAmount(0.00);
    billData.add(b9);

    // Physical Therapy Bills
    Bill b10 = new Bill("B010", "P005", "Physiotherapy Session", 5000.00);
    b10.setPatientName("Priyanka Rajapaksha");
    b10.setStatus(BillStatus.PARTIAL);
    b10.setPaymentType(PaymentType.INSURANCE);
    b10.setPaidAmount(3000.00);
    billData.add(b10);
}

public static List<Bill> bills() {
    return billData;
}

public static void addOrUpdateBill(Bill b) {
    billData.removeIf(bill -> bill.getId().equals(b.getId()));
    billData.add(b);
}

public static void deleteBill(String billId) {
    billData.removeIf(bill -> bill.getId().equals(billId));
}

// Staff data for demonstration
private static List<model.StaffModel> staffData = new ArrayList<>();

static {
    // Doctor staff with doctor permissions
    composite.Role doctorRole1 = new composite.Role("Doctor");
    doctorRole1.add(new composite.Permission("View Reports"));
    doctorRole1.add(new composite.Permission("Update Patients"));
    doctorRole1.add(new composite.Permission("Prescribe Medicines"));
    model.StaffModel s1 = new model.StaffModel("S001", "Dr. Chathura Silva", doctorRole1);
    java.util.List<composite.PermissionComponent> docPerms1 = new java.util.ArrayList<>();
    docPerms1.add(new composite.Permission("View Reports"));
    docPerms1.add(new composite.Permission("Update Patients"));
    docPerms1.add(new composite.Permission("Prescribe Medicines"));
    s1.setPermissions(docPerms1);
    staffData.add(s1);

    composite.Role doctorRole2 = new composite.Role("Doctor");
    doctorRole2.add(new composite.Permission("View Reports"));
    doctorRole2.add(new composite.Permission("Update Patients"));
    doctorRole2.add(new composite.Permission("Prescribe Medicines"));
    model.StaffModel s2 = new model.StaffModel("S002", "Dr. Anushka Perera", doctorRole2);
    java.util.List<composite.PermissionComponent> docPerms2 = new java.util.ArrayList<>();
    docPerms2.add(new composite.Permission("View Reports"));
    docPerms2.add(new composite.Permission("Update Patients"));
    docPerms2.add(new composite.Permission("Prescribe Medicines"));
    s2.setPermissions(docPerms2);
    staffData.add(s2);

    // Nurse staff with nurse permissions
    composite.Role nurseRole1 = new composite.Role("Nurse");
    nurseRole1.add(new composite.Permission("View Patients"));
    nurseRole1.add(new composite.Permission("Update Patients"));
    model.StaffModel s3 = new model.StaffModel("S003", "Sahan Wijesinghe", nurseRole1);
    java.util.List<composite.PermissionComponent> nursePerms1 = new java.util.ArrayList<>();
    nursePerms1.add(new composite.Permission("View Patients"));
    nursePerms1.add(new composite.Permission("Update Patients"));
    s3.setPermissions(nursePerms1);
    staffData.add(s3);

    composite.Role nurseRole2 = new composite.Role("Nurse");
    nurseRole2.add(new composite.Permission("View Patients"));
    nurseRole2.add(new composite.Permission("Update Patients"));
    model.StaffModel s4 = new model.StaffModel("S004", "Priyanka Rajapaksha", nurseRole2);
    java.util.List<composite.PermissionComponent> nursePerms2 = new java.util.ArrayList<>();
    nursePerms2.add(new composite.Permission("View Patients"));
    nursePerms2.add(new composite.Permission("Update Patients"));
    s4.setPermissions(nursePerms2);
    staffData.add(s4);

    // Pharmacist staff with pharmacist permissions
    composite.Role pharmacistRole = new composite.Role("Pharmacist");
    pharmacistRole.add(new composite.Permission("Prescribe Medicines"));
    pharmacistRole.add(new composite.Permission("View Patients"));
    model.StaffModel s5 = new model.StaffModel("S005", "Kasun Dharmasena", pharmacistRole);
    java.util.List<composite.PermissionComponent> pharmPerms = new java.util.ArrayList<>();
    pharmPerms.add(new composite.Permission("Prescribe Medicines"));
    pharmPerms.add(new composite.Permission("View Patients"));
    s5.setPermissions(pharmPerms);
    staffData.add(s5);

    // Admin staff with admin permissions
    composite.Role adminRole = new composite.Role("Admin");
    adminRole.add(new composite.Permission("Bill Payments"));
    adminRole.add(new composite.Permission("Manage Appointments"));
    adminRole.add(new composite.Permission("View Reports"));
    model.StaffModel s6 = new model.StaffModel("S006", "Nadeesha Kumari", adminRole);
    java.util.List<composite.PermissionComponent> adminPerms = new java.util.ArrayList<>();
    adminPerms.add(new composite.Permission("Bill Payments"));
    adminPerms.add(new composite.Permission("Manage Appointments"));
    adminPerms.add(new composite.Permission("View Reports"));
    s6.setPermissions(adminPerms);
    staffData.add(s6);

    // Additional doctor
    composite.Role doctorRole3 = new composite.Role("Doctor");
    doctorRole3.add(new composite.Permission("View Reports"));
    doctorRole3.add(new composite.Permission("Update Patients"));
    doctorRole3.add(new composite.Permission("Prescribe Medicines"));
    model.StaffModel s7 = new model.StaffModel("S007", "Dilshan Fernando", doctorRole3);
    java.util.List<composite.PermissionComponent> docPerms3 = new java.util.ArrayList<>();
    docPerms3.add(new composite.Permission("View Reports"));
    docPerms3.add(new composite.Permission("Update Patients"));
    docPerms3.add(new composite.Permission("Prescribe Medicines"));
    s7.setPermissions(docPerms3);
    staffData.add(s7);

    // Additional nurse
    composite.Role nurseRole3 = new composite.Role("Nurse");
    nurseRole3.add(new composite.Permission("View Patients"));
    nurseRole3.add(new composite.Permission("Update Patients"));
    model.StaffModel s8 = new model.StaffModel("S008", "Chaminda Perera", nurseRole3);
    java.util.List<composite.PermissionComponent> nursePerms3 = new java.util.ArrayList<>();
    nursePerms3.add(new composite.Permission("View Patients"));
    nursePerms3.add(new composite.Permission("Update Patients"));
    s8.setPermissions(nursePerms3);
    staffData.add(s8);
}

public static List<model.StaffModel> staff() {
    return staffData;
}

public static void addOrUpdateStaff(model.StaffModel s) {
    staffData.removeIf(staff -> staff.getId().equals(s.getId()));
    staffData.add(s);
}

public static void deleteStaff(String staffId) {
    staffData.removeIf(staff -> staff.getId().equals(staffId));
}

}
