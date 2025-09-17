package mediator;

import gui.Appointments;
import model.Appointment;
import model.AppointmentStatus;
import util.DBData;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AppointmentMediatorImpl implements AppointmentMediator {

    private Appointments appointmentsPanel;
public AppointmentMediatorImpl() {
}
    public AppointmentMediatorImpl(Appointments appointmentsPanel) {
        this.appointmentsPanel = appointmentsPanel;
    }

    @Override
    public void addOrUpdateAppointment(Appointment appt) {
        if (checkConflicts(appt)) {
            int choice = JOptionPane.showConfirmDialog(null, 
                "Scheduling conflict detected! The doctor has another appointment at this time.\n" +
                "Would you like to schedule anyway?",
                "Conflict Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
            if (choice == JOptionPane.NO_OPTION) {
                return;
            }
        }
        
        DBData.addOrUpdateAppointment(appt);
        reloadAppointments();
        
        JOptionPane.showMessageDialog(null, 
            "Appointment scheduled successfully!\nDoctor: " + appt.getDoctorId() + 
            "\nDate: " + appt.getDate() + "\nTime: " + appt.getTime(),
            "Success", JOptionPane.INFORMATION_MESSAGE);
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

    @Override
    public boolean checkConflicts(Appointment newAppt) {
        List<Appointment> doctorAppointments = getAppointmentsByDoctor(newAppt.getDoctorId());
        
        for (Appointment appt : doctorAppointments) {
            if (!appt.getId().equals(newAppt.getId()) && 
                appt.getDate().equals(newAppt.getDate()) &&
                appt.getStatus() != AppointmentStatus.CANCELLED &&
                timeOverlap(appt.getTime(), newAppt.getTime())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment appt : DBData.appointments()) {
            if (appt.getDoctorId().equals(doctorId)) {
                doctorAppointments.add(appt);
            }
        }
        return doctorAppointments;
    }
    
    @Override
    public List<Appointment> getAppointmentsByLocation(String location) {
        List<Appointment> locationAppointments = new ArrayList<>();
        for (Appointment appt : DBData.appointments()) {
            if (appt.getDepartment().equals(location)) {
                locationAppointments.add(appt);
            }
        }
        return locationAppointments;
    }
    
    @Override
    public List<Appointment> getAvailableSlots(String doctorId, String date) {
        List<Appointment> bookedSlots = new ArrayList<>();
        for (Appointment appt : DBData.appointments()) {
            if (appt.getDoctorId().equals(doctorId) && 
                appt.getDate().equals(date) && 
                appt.getStatus() != AppointmentStatus.CANCELLED) {
                bookedSlots.add(appt);
            }
        }
        return bookedSlots;
    }
    
    @Override
    public void notifyConflictResolved(String appointmentId) {
        if (appointmentsPanel != null) {
            appointmentsPanel.highlightResolvedConflict(appointmentId);
        }
    }
    
    private boolean timeOverlap(String time1, String time2) {
        return time1.equals(time2);
    }
}
