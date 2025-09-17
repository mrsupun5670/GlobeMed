package model;

import visitor.MedicalDataElement;
import visitor.MedicalDataVisitor;

public class Bill implements MedicalDataElement {
    private String id;
    private String patientId;
    private String patientName;
    private String service; 
    private double amount;
    private double paidAmount;
    private double remainingAmount;
    private double cashAmount;
    private double insuranceCoveredAmount;
    private PaymentType paymentType; 
    private BillStatus status;
    
    // Insurance details
    private String insuranceCompany;
    private String policyNumber;
    private double coveragePercentage;
    
    // Credit card details
    private String creditCardNumber; 

    public Bill(String id, String patientId, String service, double amount) {
        this.id = id;
        this.patientId = patientId;
        this.service = service;
        this.amount = amount;
        this.paidAmount = 0;
        this.remainingAmount = amount;
        this.cashAmount = 0;
        this.insuranceCoveredAmount = 0;
        this.paymentType = PaymentType.CASH;
        this.status = BillStatus.PENDING;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public String getService() { return service; }
    public double getAmount() { return amount; }
    public double getTotalAmount() { return amount; }
    public double getPaidAmount() { return paidAmount; }
    public double getRemainingAmount() { return remainingAmount; }
    public double getCashAmount() { return cashAmount; }
    public double getInsuranceCoveredAmount() { return insuranceCoveredAmount; }
    public PaymentType getPaymentType() { return paymentType; }
    public BillStatus getStatus() { return status; }
    public String getInsuranceCompany() { return insuranceCompany; }
    public String getPolicyNumber() { return policyNumber; }
    public double getCoveragePercentage() { return coveragePercentage; }
    public String getCreditCardNumber() { return creditCardNumber; }
    
    public String getPaymentMethod() {
        return paymentType != null ? paymentType.toString() : "CASH";
    }

    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }
    public void setStatus(BillStatus status) { this.status = status; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setRemainingAmount(double remainingAmount) { this.remainingAmount = remainingAmount; }
    public void setCashAmount(double cashAmount) { this.cashAmount = cashAmount; }
    public void setInsuranceCoveredAmount(double insuranceCoveredAmount) { this.insuranceCoveredAmount = insuranceCoveredAmount; }
    public void setInsuranceCompany(String insuranceCompany) { this.insuranceCompany = insuranceCompany; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }
    public void setCoveragePercentage(double coveragePercentage) { this.coveragePercentage = coveragePercentage; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }
    
    @Override
    public void accept(MedicalDataVisitor visitor) {
        visitor.visitBill(this);
    }
}
