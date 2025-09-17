package billing;

import model.Bill;

public abstract class BillHandler {
    protected BillHandler nextHandler;
    
    public BillHandler setNext(BillHandler handler) {
        this.nextHandler = handler;
        return handler;
    }
    
    public abstract boolean handle(Bill bill);
    
    protected boolean processNext(Bill bill) {
        if (nextHandler != null) {
            return nextHandler.handle(bill);
        }
        return true;
    }
}