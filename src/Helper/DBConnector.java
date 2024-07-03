package Helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
    // Veritabanı bağlantısını tutmak için kullanılan nesne
    private Connection connection = null;

    // Veritabanı bağlantısını oluşturan metod
    public Connection connection() {
        try {
            // Veritabanı bağlantısını oluşturur
            this.connection = DriverManager.getConnection(Contanct.DB_URL, Contanct.DB_USERNAME, Contanct.DB_PASSWORD);
        } catch (Exception e) {
            // Bir hata oluşursa, hatayı konsola yazdırır
            System.out.println(e);
        }
        // Bağlantı nesnesini geri döner
        return this.connection;
    }

    // Statik metod: Veritabanı bağlantısını alır
    public static Connection getConnection() {
        // DBConnector nesnesi oluşturur
        DBConnector connector = new DBConnector();
        // connection() metodunu çağırarak bağlantıyı geri döner
        return connector.connection();
    }
}
