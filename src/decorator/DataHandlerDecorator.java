package decorator;

public abstract class DataHandlerDecorator implements DataHandler {
    protected DataHandler wrappedHandler;
    
    public DataHandlerDecorator(DataHandler handler) {
        this.wrappedHandler = handler;
    }
    
    @Override
    public String read(String data) {
        return wrappedHandler.read(data);
    }
    
    @Override
    public void write(String data) {
        wrappedHandler.write(data);
    }
    
    @Override
    public void delete(String identifier) {
        wrappedHandler.delete(identifier);
    }
    
    @Override
    public void update(String identifier, String newData) {
        wrappedHandler.update(identifier, newData);
    }
}