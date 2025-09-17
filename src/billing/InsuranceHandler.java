package billing;

import model.Bill;
import model.BillStatus;
import javax.swing.JOptionPane;

public class InsuranceHandler extends BillHandler {
    
    @Override
    public boolean handle(Bill bill) {
        if (bill.getPaymentMethod().equalsIgnoreCase("INSURANCE") || 
            bill.getRemainingAmount() > 0) {
            
            String insuranceCompany = bill.getInsuranceCompany();
            String policyNumber = bill.getPolicyNumber();
            double coveragePercentage = bill.getCoveragePercentage();
            
            JOptionPane.showMessageDialog(null, 
                "Processing insurance claim...\n" +
                "Company: " + insuranceCompany + "\n" +
                "Policy: " + policyNumber + "\n" +
                "Coverage: " + coveragePercentage + "%",
                "Insurance Processing", JOptionPane.INFORMATION_MESSAGE);
            
            if (verifyInsurancePolicy(policyNumber)) {
                double amountToCover = bill.getRemainingAmount() > 0 ? 
                    bill.getRemainingAmount() : bill.getTotalAmount();
                    
                double coveredAmount = amountToCover * (coveragePercentage / 100.0);
                double remainingAfterInsurance = amountToCover - coveredAmount;
                
                bill.setInsuranceCoveredAmount(coveredAmount);
                bill.setRemainingAmount(remainingAfterInsurance);
                
                if (remainingAfterInsurance <= 0) {
                    bill.setStatus(BillStatus.PAID);
                    JOptionPane.showMessageDialog(null, 
                        "Insurance claim approved! Fully covered: Rs." + coveredAmount,
                        "Claim Approved", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    bill.setStatus(BillStatus.PARTIAL);
                    JOptionPane.showMessageDialog(null, 
                        "Insurance covered: Rs." + coveredAmount + 
                        "\nRemaining balance: Rs." + remainingAfterInsurance,
                        "Partial Coverage", JOptionPane.WARNING_MESSAGE);
                }
                
                return processNext(bill);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Insurance claim denied! Policy not valid or expired.",
                    "Claim Denied", JOptionPane.ERROR_MESSAGE);
                
                return processNext(bill);
            }
        }
        
        return processNext(bill);
    }
    
    private boolean verifyInsurancePolicy(String policyNumber) {
        return policyNumber != null && !policyNumber.trim().isEmpty() && 
               policyNumber.length() >= 6;
    }
}