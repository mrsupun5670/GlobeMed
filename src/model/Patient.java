package model;

public class Patient {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String contact;
    private String bloodGroup;
    private String disease;
    private String medicalHistory;
    private String treatmentPlan;

    public Patient(String id, String name, int age, String gender, String contact,
                   String bloodGroup, String disease, String medicalHistory, String treatmentPlan) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.bloodGroup = bloodGroup;
        this.disease = disease;
        this.medicalHistory = medicalHistory;
        this.treatmentPlan = treatmentPlan;
    }
    
    public Patient(String id, String name, String disease, int age, String contact) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.contact = contact;
    this.disease = disease;
}


    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getContact() { return contact; }
    public String getBloodGroup() { return bloodGroup; }
    public String getDisease() { return disease; }
    public String getMedicalHistory() { return medicalHistory; }
    public String getTreatmentPlan() { return treatmentPlan; }
}
