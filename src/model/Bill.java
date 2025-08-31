package model;

public class Bill {
    private String id;
    private String patientId;
    private String service; 
    private double amount;
    private double paidAmount;
    private PaymentType paymentType; 
    private BillStatus status; 

    public Bill(String id, String patientId, String service, double amount) {
        this.id = id;
        this.patientId = patientId;
        this.service = service;
        this.amount = amount;
        this.paidAmount = 0;
        this.paymentType = PaymentType.CASH;
        this.status = BillStatus.PENDING;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getService() { return service; }
    public double getAmount() { return amount; }
    public double getPaidAmount() { return paidAmount; }
    public PaymentType getPaymentType() { return paymentType; }
    public BillStatus getStatus() { return status; }

    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }
    public void setStatus(BillStatus status) { this.status = status; }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
}
