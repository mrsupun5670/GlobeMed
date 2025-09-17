package billing;

import model.Bill;
import model.BillStatus;
import javax.swing.JOptionPane;

public class CreditCardHandler extends BillHandler {
    
    @Override
    public boolean handle(Bill bill) {
        if (bill.getPaymentMethod().equalsIgnoreCase("CREDIT_CARD") || 
            bill.getRemainingAmount() > 0) {
            
            double amountToCharge = bill.getRemainingAmount() > 0 ? 
                bill.getRemainingAmount() : bill.getTotalAmount();
            
            String cardNumber = bill.getCreditCardNumber();
            
            JOptionPane.showMessageDialog(null, 
                "Processing credit card payment...\n" +
                "Card: ****" + cardNumber.substring(cardNumber.length() - 4) + "\n" +
                "Amount: Rs." + amountToCharge,
                "Credit Card Processing", JOptionPane.INFORMATION_MESSAGE);
            
            if (processCreditCardTransaction(cardNumber, amountToCharge)) {
                bill.setRemainingAmount(0);
                bill.setStatus(BillStatus.PAID);
                
                JOptionPane.showMessageDialog(null, 
                    "Credit card payment successful!\nAmount charged: Rs." + amountToCharge,
                    "Payment Approved", JOptionPane.INFORMATION_MESSAGE);
                
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Credit card transaction failed! Please try another payment method.",
                    "Payment Failed", JOptionPane.ERROR_MESSAGE);
                
                return processNext(bill);
            }
        }
        
        return processNext(bill);
    }
    
    private boolean processCreditCardTransaction(String cardNumber, double amount) {
        return cardNumber != null && cardNumber.length() == 16 && amount > 0;
    }
}