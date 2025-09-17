package decorator;

public class SecureDataManager {
    
    public static DataHandler createSecurePatientHandler() {
        return new LoggedDataHandler(
            new EncryptedDataHandler(
                new ValidatedDataHandler(
                    new BasicDataHandler("Patient Record")
                )
            )
        );
    }
    
    public static DataHandler createSecureBillingHandler() {
        return new LoggedDataHandler(
            new ValidatedDataHandler(
                new BasicDataHandler("Billing Record")
            )
        );
    }
}