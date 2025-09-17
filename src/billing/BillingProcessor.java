package billing;

import model.Bill;
import javax.swing.JOptionPane;

public class BillingProcessor {
    private BillHandler handlerChain;
    
    public BillingProcessor() {
        setupHandlerChain();
    }
    
    private void setupHandlerChain() {
        BillHandler cashHandler = new CashHandler();
        BillHandler insuranceHandler = new InsuranceHandler();
        BillHandler creditCardHandler = new CreditCardHandler();
        
        cashHandler.setNext(insuranceHandler).setNext(creditCardHandler);
        
        this.handlerChain = cashHandler;
    }
    
    public boolean processBill(Bill bill) {
        if (bill == null) {
            JOptionPane.showMessageDialog(null, 
                "Invalid bill information!",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        JOptionPane.showMessageDialog(null, 
            "Starting bill processing for: " + bill.getPatientName() + 
            "\nTotal Amount: Rs." + bill.getTotalAmount(),
            "Bill Processing", JOptionPane.INFORMATION_MESSAGE);
        
        boolean result = handlerChain.handle(bill);
        
        if (bill.getStatus() == model.BillStatus.PAID) {
            JOptionPane.showMessageDialog(null, 
                "Bill processing completed successfully!\n" +
                "Patient: " + bill.getPatientName() + "\n" +
                "Total Paid: Rs." + bill.getTotalAmount(),
                "Payment Complete", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Bill processing completed with remaining balance.\n" +
                "Patient: " + bill.getPatientName() + "\n" +
                "Status: " + bill.getStatus() + "\n" +
                "Remaining: Rs." + bill.getRemainingAmount(),
                "Payment Status", JOptionPane.WARNING_MESSAGE);
        }
        
        return result;
    }
    
    public void setCustomHandlerChain(BillHandler customChain) {
        this.handlerChain = customChain;
    }
}