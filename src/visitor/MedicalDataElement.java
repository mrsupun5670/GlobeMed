package visitor;

public interface MedicalDataElement {
    void accept(MedicalDataVisitor visitor);
}