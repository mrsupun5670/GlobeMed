package payments;
import model.*;

public class InsuranceHandler extends PaymentHandler {
    @Override
    public void handle(Bill bill, double cash, double insurance) {
        double remaining = bill.getAmount() - bill.getPaidAmount();
        if(insurance >= remaining) {
            bill.setPaidAmount(bill.getAmount());
            if(bill.getPaymentType() == PaymentType.MIXED) bill.setPaymentType(PaymentType.MIXED);
            else bill.setPaymentType(PaymentType.INSURANCE);
            bill.setStatus(BillStatus.PAID);
        } else if (insurance > 0) {
            bill.setPaidAmount(bill.getPaidAmount() + insurance);
            bill.setPaymentType(PaymentType.MIXED);
            bill.setStatus(BillStatus.PARTIAL);
        }
    }
}
