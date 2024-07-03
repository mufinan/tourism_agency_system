package Helper;

public class Contanct {
    // Veritabanı adı
    public static final String DB_NAME = "tourism_agency_system";
    // Veritabanı URL'i
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/tourism_agency_system";
    // Veritabanı kullanıcı adı
    public static final String DB_USERNAME = "postgres";
    // Veritabanı parolası
    public static final String DB_PASSWORD = "440616";
    // Kullanıcı sorgusu (kullanıcı adı ve parolaya göre)
    public static final String SELECT_USER = "SELECT * FROM tourism_agency_user WHERE username = ? AND password = ?";

    // Belirtilen tablo adından tüm kayıtları listelemek için sorgu oluşturur
    public static String LIST_QUERY(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    // Belirtilen otel tablosuna yeni bir kayıt eklemek için sorgu oluşturur
    public static String ADD_QUERY(String otel, String[] data) {
        return "INSERT INTO " + otel + " (`name`, `region`, `city`, `address`, `e-mail`, `phone`, `star`, `features`) VALUES ('" + data[0] + "', '" + data[1] + "', '" + data[2] + "', '" + data[3] + "', '" + data[4] + "', '" + data[5] + "', '" + Integer.parseInt(data[6]) + "', '" + data[7] + "')";
    }

    // Belirtilen tablodan belirli bir id'ye sahip kaydı silmek için sorgu oluşturur
    public static String DELETE_QUERY(String table, int id) {
        return "DELETE FROM " + table + " WHERE id = " + id;
    }

    // Belirli bir parametreye ve değere göre tablodan kayıtları listelemek için sorgu oluşturur
    public static String LIST_QUERY_PARAMETRE(String table, String parametre, int value) {
        return "SELECT * FROM " + table + " WHERE " + parametre + " = " + value;
    }

    // Belirli bir id'ye sahip konaklama kaydını çekmek için sorgu oluşturur
    public static String FETCH_QUERY(String lodgings, int lodgingsId) {
        return "SELECT * FROM " + lodgings + " WHERE id = " + lodgingsId;
    }

    // Belirli bir değişkene ve değere sahip kaydı çekmek için sorgu oluşturur
    public static String FETCH_QUERY(String table, String variable, String value) {
        return "SELECT * FROM " + table + " WHERE " + variable + " = '" + value + "'";
    }

    // Belirli bir sezon ve id'ye göre kaydı çekmek için sorgu oluşturur
    public static String SELECT_QUERY_WHERE(String season, int seasonId) {
        return "SELECT * FROM " + season + " WHERE id = " + seasonId;
    }

    // Belirtilen sezon tablosundan tüm kayıtları listelemek için sorgu oluşturur
    public static String SELECT_QUERY(String season) {
        return "SELECT * FROM " + season;
    }

    // Yeni bir sezon kaydı eklemek için sorgu oluşturur
    public static String INSERT_QUERY(String season, int id, String string, String text, String text1) {
        return "INSERT INTO " + season + " ( `otel_id`, `start_date`, `end_date`, `name`) VALUES ('" + id + "', '" + string + "', '" + text + "', '" + text1 + "')";
    }

    // Yeni bir oda kaydı eklemek için sorgu oluşturur (overloaded method - aşırı yüklenmiş metod)
    public static String INSERT_QUERY(String room, int id, String string, String string1, String string2, String string3, String text, String text1, String text2, String text3, String text4, String text5) {
        return "INSERT INTO " + room + " ( `otel_id`, `lodgings_id`, `season_id`, `features`, `name`, `stock`, `bed_number`, `sqr_meter`, `price_adult`, `price_child`) VALUES ('" + id + "', '" + string + "', '" + string1 + "', '" + string2 + "', '" + string3 + "', '" + text + "', '" + text1 + "', '" + text2 + "', '" + text3 + "', '" + text4 + "', '" + text5 + "')";
    }

    // Belirli bir otel id'sine göre sezondan kaydı silmek için sorgu oluşturur
    public static String DELETE_QUERY_WHERE(String season, String otelId, int hotelId) {
        return "DELETE FROM " + season + " WHERE " + otelId + " = " + hotelId;
    }

    // Yeni bir oda kaydı eklemek için sorgu oluşturur (overloaded method - aşırı yüklenmiş metod)
    public static String INSERT_QUERY(String room, int id, int id1, int id2, String text, String string, String text1, String text2, String text3, String string1, String text4, String text5) {
        return "INSERT INTO " + room + " ( `otel_id`, `lodgings_id`, `season_id`, `features`, `name`, `stock`, `bed_number`, `sqr_meter`, `price_adult`, `price_child`) VALUES ('" + id + "', '" + id1 + "', '" + id2 + "', '" + text + "', '" + string + "', '" + text1 + "', '" + text2 + "', '" + text3 + "', '" + string1 + "', '" + text4 + "', '" + text5 + "')";
    }
}
