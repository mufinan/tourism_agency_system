package Model;

import Helper.Contanct;
import Helper.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Hotel {
    private int id;
    private String name;
    private String region;
    private String city;
    private String address;
    private String email;
    private String phone;
    private int star;
    private String features;

    // Constructor: Yeni bir Hotel nesnesi oluşturur
    public Hotel(int id, String name, String region, String city, String address, String email, String phone, int star, String features) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.city = city;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.star = star;
        this.features = features;
    }

    // Tüm otelleri listeleyen metod
    public static ArrayList<Hotel> getList() {
        ArrayList<Hotel> hotelArrayList = new ArrayList<>();
        // SQL sorgusu: Tüm otelleri listelemek için
        String query = Contanct.LIST_QUERY("otel");

        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Sonuçları Hotel nesnesine atar ve listeye ekler
                Hotel hotel = new Hotel(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("region"),
                        resultSet.getString("city"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getInt("star"),
                        resultSet.getString("features")
                );
                hotelArrayList.add(hotel);
            }
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
        }
        return hotelArrayList;
    }

    // Yeni otel ekleyen metod
    public static boolean add(String name, String city, String region, String address, String email, String phone, int star, String features) {
        // SQL sorgusu: Yeni otel eklemek için
        String query = "INSERT INTO otel (name, city, region, address, email, phone, star, features) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Email ile otelin zaten var olup olmadığını kontrol eder
        Hotel existingHotel = getFetchOtelByEmail(email);
        if (existingHotel != null) {
            Helper.Helper.showMessage("Bu otel zaten var", "Hata", 2);
            return false;
        }

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Sorgu parametrelerini ayarlar
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, region);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phone);
            preparedStatement.setInt(7, star);
            preparedStatement.setString(8, features);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
            return false;
        }
    }

    // Email ile otel bilgilerini çeken metod
    private static Hotel getFetchOtelByEmail(String email) {
        // SQL sorgusu: Email ile oteli çekmek için
        String query = "SELECT * FROM otel WHERE email = ?";
        Hotel hotel = null;

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Sonuçları Hotel nesnesine atar
                    hotel = new Hotel(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("region"),
                            resultSet.getString("city"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getInt("star"),
                            resultSet.getString("features")
                    );
                }
            }
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
        }
        return hotel;
    }

    // Belirli bir oteli silen metod
    public static boolean delete(int otel_id) {
        // SQL sorgusu: Oteli silmek için
        String query = Contanct.DELETE_QUERY("otel", otel_id);
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
            return false;
        }
    }

    // ID ile otel bilgilerini çeken metod
    public static Hotel getFetch(int id) {
        // SQL sorgusu: ID ile oteli çekmek için
        String query = "SELECT * FROM otel WHERE id = ?";
        Hotel hotel = null;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Sonuçları Hotel nesnesine atar
                    hotel = new Hotel(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("region"),
                            resultSet.getString("city"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getInt("star"),
                            resultSet.getString("features")
                    );
                } else {
                    System.err.println("Hotel not found for ID: " + id);
                }
            }
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
        }
        return hotel;
    }

    // Belirli kriterlere göre otel arayan metod
    public static ArrayList<Hotel> search(String search, String searchValue) {
        String query = "";
        // Arama kriterlerine göre SQL sorgusu oluşturur
        if (searchValue.equals("Otel")) {
            query = "SELECT * FROM otel WHERE name = ?";
        } else if (searchValue.equals("Sehir")) {
            query = "SELECT * FROM otel WHERE city = ?";
        } else if (searchValue.equals("Bolge")) {
            query = "SELECT * FROM otel WHERE region = ?";
        }

        ArrayList<Hotel> hotelArrayList = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, search);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Sonuçları Hotel nesnesine atar ve listeye ekler
                    Hotel hotel = new Hotel(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("region"),
                            resultSet.getString("city"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getInt("star"),
                            resultSet.getString("features")
                    );
                    hotelArrayList.add(hotel);
                }
            }
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
        }
        return hotelArrayList;
    }

    // Otel ismi ile otel bilgilerini çeken metod
    public static Hotel getFetchByName(String name) {
        // SQL sorgusu: Otel ismi ile oteli çekmek için
        String query = "SELECT * FROM otel WHERE name = ?";
        Hotel hotel = null;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Sonuçları Hotel nesnesine atar
                    hotel = new Hotel(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("region"),
                            resultSet.getString("city"),
                            resultSet.getString("address"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getInt("star"),
                            resultSet.getString("features")
                    );
                } else {
                    System.err.println("Hotel not found with name: " + name);
                }
            }
        } catch (SQLException e) {
            // Hata durumunda hatayı yazdırır
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
        }
        return hotel;
    }

    // Getter ve setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
