package mediator;

import model.Appointment;
import java.util.List;

public interface AppointmentMediator {
    void addOrUpdateAppointment(Appointment appt);
    void deleteAppointment(String appointmentId);
    void searchAppointments(String keyword);
    void reloadAppointments();
    
    boolean checkConflicts(Appointment newAppt);
    List<Appointment> getAppointmentsByDoctor(String doctorId);
    List<Appointment> getAppointmentsByLocation(String location);
    List<Appointment> getAvailableSlots(String doctorId, String date);
    void notifyConflictResolved(String appointmentId);
}
