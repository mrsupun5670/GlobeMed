package decorator;

public interface DataHandler {
    String read(String data);
    void write(String data);
    void delete(String identifier);
    void update(String identifier, String newData);
}