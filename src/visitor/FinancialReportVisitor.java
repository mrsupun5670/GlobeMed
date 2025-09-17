package visitor;

import model.Patient;
import model.Appointment;
import model.Bill;
import model.BillStatus;
import java.util.HashMap;
import java.util.Map;

public class FinancialReportVisitor implements MedicalDataVisitor {
    private double totalRevenue;
    private double totalPending;
    private double totalPaid;
    private int totalBills;
    private Map<String, Double> revenueByService;
    private Map<BillStatus, Integer> billsByStatus;
    
    public FinancialReportVisitor() {
        reset();
    }
    
    @Override
    public void visitPatient(Patient patient) {
        
    }
    
    @Override
    public void visitAppointment(Appointment appointment) {
        
    }
    
    @Override
    public void visitBill(Bill bill) {
        totalBills++;
        totalRevenue += bill.getTotalAmount();
        totalPaid += bill.getPaidAmount();
        totalPending += (bill.getTotalAmount() - bill.getPaidAmount());
        
        String service = bill.getService();
        revenueByService.put(service, 
            revenueByService.getOrDefault(service, 0.0) + bill.getTotalAmount());
        
        BillStatus status = bill.getStatus();
        billsByStatus.put(status, 
            billsByStatus.getOrDefault(status, 0) + 1);
    }
    
    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append("FINANCIAL SUMMARY REPORT\n");
        report.append("Generated on: ").append(java.time.LocalDateTime.now()).append("\n");
        report.append("=".repeat(40)).append("\n\n");
        
        report.append("OVERVIEW:\n");
        report.append("Total Bills: ").append(totalBills).append("\n");
        report.append("Total Revenue: Rs.").append(String.format("%.2f", totalRevenue)).append("\n");
        report.append("Total Paid: Rs.").append(String.format("%.2f", totalPaid)).append("\n");
        report.append("Total Pending: Rs.").append(String.format("%.2f", totalPending)).append("\n");
        
        double collectionRate = totalRevenue > 0 ? (totalPaid / totalRevenue) * 100 : 0;
        report.append("Collection Rate: ").append(String.format("%.1f", collectionRate)).append("%\n\n");
        
        report.append("REVENUE BY SERVICE:\n");
        for (Map.Entry<String, Double> entry : revenueByService.entrySet()) {
            report.append("  ").append(entry.getKey()).append(": Rs.")
                  .append(String.format("%.2f", entry.getValue())).append("\n");
        }
        report.append("\n");
        
        report.append("BILLS BY STATUS:\n");
        for (Map.Entry<BillStatus, Integer> entry : billsByStatus.entrySet()) {
            report.append("  ").append(entry.getKey()).append(": ")
                  .append(entry.getValue()).append(" bills\n");
        }
        
        return report.toString();
    }
    
    @Override
    public void reset() {
        totalRevenue = 0;
        totalPending = 0;
        totalPaid = 0;
        totalBills = 0;
        revenueByService = new HashMap<>();
        billsByStatus = new HashMap<>();
    }
}