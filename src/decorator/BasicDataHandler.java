package decorator;

import javax.swing.JOptionPane;

public class BasicDataHandler implements DataHandler {
    private String handlerType;
    
    public BasicDataHandler(String handlerType) {
        this.handlerType = handlerType;
    }
    
    @Override
    public String read(String data) {
        JOptionPane.showMessageDialog(null, 
            "Basic " + handlerType + " Data Read: " + data,
            "Data Access", JOptionPane.INFORMATION_MESSAGE);
        return data;
    }
    
    @Override
    public void write(String data) {
        JOptionPane.showMessageDialog(null, 
            "Basic " + handlerType + " Data Written: " + data,
            "Data Write", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void delete(String identifier) {
        JOptionPane.showMessageDialog(null, 
            "Basic " + handlerType + " Data Deleted: " + identifier,
            "Data Delete", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void update(String identifier, String newData) {
        JOptionPane.showMessageDialog(null, 
            "Basic " + handlerType + " Data Updated: " + identifier + " -> " + newData,
            "Data Update", JOptionPane.INFORMATION_MESSAGE);
    }
}