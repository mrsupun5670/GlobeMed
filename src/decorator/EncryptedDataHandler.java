package decorator;

import javax.swing.JOptionPane;
import java.util.Base64;

public class EncryptedDataHandler extends DataHandlerDecorator {
    
    public EncryptedDataHandler(DataHandler handler) {
        super(handler);
    }
    
    @Override
    public String read(String data) {
        String decryptedData = decrypt(data);
        return super.read(decryptedData);
    }
    
    @Override
    public void write(String data) {
        String encryptedData = encrypt(data);
        super.write("ENCRYPTED:" + encryptedData);
    }
    
    @Override
    public void delete(String identifier) {
        super.delete(identifier);
    }
    
    @Override
    public void update(String identifier, String newData) {
        String encryptedData = encrypt(newData);
        super.update(identifier, "ENCRYPTED:" + encryptedData);
    }
    
    private String encrypt(String data) {
        try {
            return Base64.getEncoder().encodeToString(data.getBytes());
        } catch (Exception e) {
            return data;
        }
    }
    
    private String decrypt(String encryptedData) {
        try {
            if (encryptedData.startsWith("ENCRYPTED:")) {
                String encodedData = encryptedData.substring(10);
                return new String(Base64.getDecoder().decode(encodedData));
            }
            return encryptedData;
        } catch (Exception e) {
            return encryptedData;
        }
    }
}