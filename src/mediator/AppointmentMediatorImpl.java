package mediator;

import gui.Appointments;
import model.Appointment;
import util.DBData;

public class AppointmentMediatorImpl implements AppointmentMediator {

    private Appointments appointmentsPanel;
public AppointmentMediatorImpl() {
}
    public AppointmentMediatorImpl(Appointments appointmentsPanel) {
        this.appointmentsPanel = appointmentsPanel;
    }

    @Override
    public void addOrUpdateAppointment(Appointment appt) {
        DBData.addOrUpdateAppointment(appt);
        reloadAppointments();
    }

    @Override
    public void deleteAppointment(String appointmentId) {
        DBData.deleteAppointment(appointmentId);
        reloadAppointments();
    }

    @Override
    public void searchAppointments(String keyword) {
        appointmentsPanel.showSearchResults(keyword);
    }

    @Override
    public void reloadAppointments() {
        appointmentsPanel.loadAppointmentsFromMediator();
    }
    
  public void setAppointmentsPanel(Appointments appointmentsPanel) {
    this.appointmentsPanel = appointmentsPanel;
}

}
