package billing;

import model.Bill;
import model.BillStatus;
import javax.swing.JOptionPane;

public class CashHandler extends BillHandler {
    
    @Override
    public boolean handle(Bill bill) {
        if (bill.getPaymentMethod().equalsIgnoreCase("CASH")) {
            double cashAmount = bill.getCashAmount();
            double totalAmount = bill.getTotalAmount();
            
            JOptionPane.showMessageDialog(null, 
                "Processing cash payment of Rs." + cashAmount + 
                " for patient: " + bill.getPatientName(),
                "Cash Payment", JOptionPane.INFORMATION_MESSAGE);
            
            if (cashAmount >= totalAmount) {
                bill.setStatus(BillStatus.PAID);
                bill.setRemainingAmount(0);
                
                if (cashAmount > totalAmount) {
                    double change = cashAmount - totalAmount;
                    JOptionPane.showMessageDialog(null, 
                        "Payment successful! Change due: Rs." + change,
                        "Payment Complete", JOptionPane.INFORMATION_MESSAGE);
                }
                
                return true;
            } else {
                bill.setRemainingAmount(totalAmount - cashAmount);
                bill.setStatus(BillStatus.PARTIAL);
                
                JOptionPane.showMessageDialog(null, 
                    "Partial cash payment processed. Remaining: Rs." + bill.getRemainingAmount(),
                    "Partial Payment", JOptionPane.WARNING_MESSAGE);
                
                return processNext(bill);
            }
        }
        
        return processNext(bill);
    }
}