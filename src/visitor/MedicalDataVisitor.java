package visitor;

public interface MedicalDataVisitor {
    void visitPatient(model.Patient patient);
    void visitAppointment(model.Appointment appointment);
    void visitBill(model.Bill bill);
    String getReport();
    void reset();
}