package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.*;

public class DBData {

    private static Map<String, Patient> patientData = new HashMap<>();

    static {
        patientData.put("P001", new Patient("P001", "Supun Gunasinghe", 30, "Male", "0772010915", "O+",
                "Diabetes", "Family history of diabetes", "Metformin 500mg daily"));
        patientData.put("P002", new Patient("P002", "Kasun Perera", 42, "Male", "0714567890", "A+",
                "Hypertension", "High blood pressure history", "Lifestyle changes, Atenolol 50mg"));
        patientData.put("P003", new Patient("P003", "Nadeesha Fernando", 35, "Female", "0723456789", "B+",
                "Asthma", "Childhood asthma", "Inhaler therapy"));
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
                new User("dr.silva", Role.DOCTOR, "doc123"),
                new User("nurse.alex", Role.NURSE, "nurse123"),
                new User("pharm.ravi", Role.PHARMACIST, "pharm123")
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
    billData.add(new Bill("B001", "P001", "Consultation - Cardiology", 1000));
    billData.add(new Bill("B002", "P002", "Consultation - Cardiology", 1200));
    billData.add(new Bill("B003", "P003", "Surgery - Appendix", 5000));
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

}
