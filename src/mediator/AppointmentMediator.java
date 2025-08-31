package mediator;

import model.Appointment;

public interface AppointmentMediator {
    void addOrUpdateAppointment(Appointment appt);
    void deleteAppointment(String appointmentId);
    void searchAppointments(String keyword);
    void reloadAppointments();
}
