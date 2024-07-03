package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.*;
import java.util.ArrayList;

public class Reservation {
    private int id;
    private int otel_id;
    private int employee_id;
    private String customer_name;
    private String customer_id;
    private Date start_date;
    private Date end_date;
    private int price;

    // Constructor: Yeni bir Reservation nesnesi oluşturur
    public Reservation(int id, int otel_id, int employee_id, String customer_name, String customer_id, Date start_date, Date end_date, int price) {
        this.id = id;
        this.otel_id = otel_id;
        this.employee_id = employee_id;
        this.customer_name = customer_name;
        this.customer_id = customer_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
    }

    // Tüm rezervasyonları listeleyen metod
    public static ArrayList<Reservation> getList() {
        ArrayList<Reservation> reservationArrayList = new ArrayList<>();
        // SQL sorgusu: Tüm rezervasyonları listelemek için
        String query = "SELECT * FROM reservations";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                // Sonuçları Reservation nesnesine atar ve listeye ekler
                Reservation reservation = new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("customer_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("price")
                );
                reservationArrayList.add(reservation);
            }
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
        }
        return reservationArrayList;
    }

    // Yeni rezervasyon ekleyen metod
    public static boolean add(int otel_id, int employee_id, String customer_name, String customer_id, String start_date, String end_date, int price) {
        // SQL sorgusu: Yeni rezervasyon eklemek için
        String query = "INSERT INTO reservations (otel_id, employee_id, customer_name, customer_id, start_date, end_date, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Sorgu parametrelerini ayarlar
            statement.setInt(1, otel_id);
            statement.setInt(2, employee_id);
            statement.setString(3, customer_name);
            statement.setString(4, customer_id);
            statement.setDate(5, java.sql.Date.valueOf(start_date)); // Dönüştürme yapılıyor
            statement.setDate(6, java.sql.Date.valueOf(end_date)); // Dönüştürme yapılıyor
            statement.setInt(7, price);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            return false;
        }
    }

    // Belirli bir rezervasyonu silen metod
    public static boolean delete(int id) {
        // SQL sorgusu: Rezervasyonu silmek için
        String query = "DELETE FROM reservations WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            return false;
        }
    }

    // Getter metodları
    public int getId() {
        return id;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public int getEmployeeId() {
        return employee_id;
    }

    public Date getStartDate() {
        return start_date;
    }

    public Date getEndDate() {
        return end_date;
    }

    public int getRoom_number() {
        return price;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    // Setter metodları
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
