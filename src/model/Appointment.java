package model;

public class Appointment {

    private String id;
    private String patientId;
    private String doctorId;
    private String department;
    private String type;
    private String date;
    private String time;
    private AppointmentStatus status;
    private String notes;
    private String location;

    public Appointment(String id, String patientId, String doctorId, String department, String type,
                       String date, String time, AppointmentStatus status,
                       String notes, String location) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.department = department;
        this.type = type;
        this.date = date;
        this.time = time;
        this.status = status;
        this.notes = notes;
        this.location = location;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public String toString() {
        return id + " | " + patientId + " | " + doctorId + " | " + department + " | " + type + " | " + date + " " + time;
    }
}
