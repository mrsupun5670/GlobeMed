package decorator;

import javax.swing.JOptionPane;

public class CompressedDataHandler extends DataHandlerDecorator {
    
    public CompressedDataHandler(DataHandler handler) {
        super(handler);
    }
    
    @Override
    public String read(String data) {
        JOptionPane.showMessageDialog(null, 
            "📦 Decompressing data before read...",
            "Compression Layer", JOptionPane.INFORMATION_MESSAGE);
        
        String decompressedData = decompress(data);
        return super.read(decompressedData);
    }
    
    @Override
    public void write(String data) {
        JOptionPane.showMessageDialog(null, 
            "📦 Compressing data before write...",
            "Compression Layer", JOptionPane.INFORMATION_MESSAGE);
        
        String compressedData = compress(data);
        super.write("COMPRESSED:" + compressedData);
    }
    
    @Override
    public void delete(String identifier) {
        JOptionPane.showMessageDialog(null, 
            "📦 Compression layer processing delete...",
            "Compression Layer", JOptionPane.INFORMATION_MESSAGE);
        
        super.delete(identifier);
    }
    
    @Override
    public void update(String identifier, String newData) {
        JOptionPane.showMessageDialog(null, 
            "📦 Compressing new data for update...",
            "Compression Layer", JOptionPane.INFORMATION_MESSAGE);
        
        String compressedData = compress(newData);
        super.update(identifier, "COMPRESSED:" + compressedData);
    }
    
    private String compress(String data) {
        StringBuilder compressed = new StringBuilder();
        compressed.append("GZIP_SIMULATED_");
        
        for (int i = 0; i < data.length(); i += 3) {
            int end = Math.min(i + 3, data.length());
            String chunk = data.substring(i, end);
            compressed.append("[").append(chunk.length()).append(":").append(chunk).append("]");
        }
        
        return compressed.toString();
    }
    
    private String decompress(String compressedData) {
        if (!compressedData.startsWith("COMPRESSED:")) {
            return compressedData;
        }
        
        String data = compressedData.substring(11);
        
        if (data.startsWith("GZIP_SIMULATED_")) {
            StringBuilder decompressed = new StringBuilder();
            data = data.substring(15);
            
            int i = 0;
            while (i < data.length()) {
                if (data.charAt(i) == '[') {
                    int colonIndex = data.indexOf(':', i);
                    int closeIndex = data.indexOf(']', colonIndex);
                    
                    if (colonIndex > 0 && closeIndex > 0) {
                        String content = data.substring(colonIndex + 1, closeIndex);
                        decompressed.append(content);
                        i = closeIndex + 1;
                    } else {
                        break;
                    }
                } else {
                    i++;
                }
            }
            
            return decompressed.toString();
        }
        
        return data;
    }
}