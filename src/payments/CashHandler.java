package payments;
import model.*;

public class CashHandler extends PaymentHandler {
    @Override
    public void handle(Bill bill, double cash, double insurance) {
        if(cash >= bill.getAmount()) {
            bill.setPaidAmount(bill.getAmount());
            bill.setPaymentType(PaymentType.CASH);
            bill.setStatus(BillStatus.PAID);
        } else if (cash > 0) {
            bill.setPaidAmount(cash);
            bill.setPaymentType(PaymentType.MIXED);
            bill.setStatus(BillStatus.PARTIAL);
            if(next != null) next.handle(bill, 0, insurance);
        } else {
            if(next != null) next.handle(bill, 0, insurance);
        }
    }
}
