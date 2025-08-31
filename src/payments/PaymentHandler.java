package payments;

import model.Bill;

public abstract class PaymentHandler {

    protected PaymentHandler next;

    public void setNext(PaymentHandler next) {
        this.next = next;
    }

    public abstract void handle(Bill bill, double cash, double insurance);
    
    
}
