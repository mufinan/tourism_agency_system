package Model;

import Helper.DBConnector;

import java.sql.Statement;

public class Employee extends User {
    // Kullanıcı adı ile oda bilgilerini çeker
    public static Room getFetch(String userName) {
        Room room = null;
        // Belirtilen kullanıcı adına göre kullanıcıyı çekmek için SQL sorgusu
        String query = "SELECT * FROM tourism_agency_user WHERE username = " + "'" + userName + "'";
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            statement.executeQuery(query);
            return room; // Oda bilgilerini döner (şu anda boş)
        } catch (Exception e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            return null; // Hata durumunda null döner
        }
    }
}
