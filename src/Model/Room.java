package Model;

import Helper.Contanct;
import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Room {

    private int id;
    private int otel_id;
    private Hotel hotel;
    private int lodgings_id;
    private Lodgings lodgings;
    private int season_id;
    private Season season;
    private int price_adult;
    private int price_child;
    private String features;
    private String name;
    private int stock;
    private int bed_number;
    private int sqr_meter;

    // Constructor: Yeni bir Room nesnesi oluşturur
    public Room(int id, int otel_id, int lodgings_id, int season_id, String features, String name, int stock, int bed_number, int sqr_meter, int price_adult, int price_child) {
        this.id = id;
        this.otel_id = otel_id;
        this.hotel = Hotel.getFetch(otel_id); // Otel bilgilerini getirir
        this.lodgings_id = lodgings_id;
        this.lodgings = Lodgings.getFetch(lodgings_id); // Konaklama bilgilerini getirir
        this.season_id = season_id;
        this.season = Season.getFetchById(season_id); // Sezon bilgilerini getirir
        this.price_adult = price_adult;
        this.price_child = price_child;
        this.features = features;
        this.name = name;
        this.stock = stock;
        this.bed_number = bed_number;
        this.sqr_meter = sqr_meter;
    }

    // Belirli bir odayı silen metod
    public static boolean delete(int i) {
        String query = "DELETE FROM room WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, i);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    // Yeni bir oda ekleyen metod
    public static boolean add(int otel_id, int lodgings_id, int season_id, String name, String stock, String bed_number, String sqr_meter, String room_type, String features, String price_adult, String price_child) {
        String query = "INSERT INTO room (otel_id, lodgings_id, season_id, name, stock, bed_number, sqr_meter, room_type, features, price_adult, price_child) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Room room = Room.getFetch(otel_id, Integer.parseInt(bed_number));
        if (room != null) {
            Helper.showMessage("Bu oda zaten ekli", "Hata", 2);
            return false;
        }

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Sorgu parametrelerini ayarlar
            statement.setInt(1, otel_id);
            statement.setInt(2, lodgings_id);
            statement.setInt(3, season_id);
            statement.setString(4, name);
            statement.setInt(5, Integer.parseInt(stock));
            statement.setInt(6, Integer.parseInt(bed_number));
            statement.setInt(7, Integer.parseInt(sqr_meter));
            statement.setString(8, room_type);
            statement.setString(9, features);
            statement.setInt(10, Integer.parseInt(price_adult));
            statement.setInt(11, Integer.parseInt(price_child));
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    // Belirli bir otel ve yatak numarasına göre oda bilgilerini çeken metod
    private static Room getFetch(int otel_id, int bed_number) {
        String query = "SELECT * FROM room WHERE otel_id = ? AND bed_number = ?";
        Room room = null;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, otel_id);
            statement.setInt(2, bed_number);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    room = new Room(
                            resultSet.getInt("id"),
                            resultSet.getInt("otel_id"),
                            resultSet.getInt("lodgings_id"),
                            resultSet.getInt("season_id"),
                            resultSet.getString("features"),
                            resultSet.getString("name"),
                            resultSet.getInt("stock"),
                            resultSet.getInt("bed_number"),
                            resultSet.getInt("sqr_meter"),
                            resultSet.getInt("price_adult"),
                            resultSet.getInt("price_child")
                    );
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return room;
    }

    // Belirli bir oda ID'sine göre oda bilgilerini çeken metod
    public static Room getFetch(int roomId) {
        String query = "SELECT * FROM room WHERE id = ?";
        Room room = null;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    room = new Room(
                            resultSet.getInt("id"),
                            resultSet.getInt("otel_id"),
                            resultSet.getInt("lodgings_id"),
                            resultSet.getInt("season_id"),
                            resultSet.getString("features"),
                            resultSet.getString("name"),
                            resultSet.getInt("stock"),
                            resultSet.getInt("bed_number"),
                            resultSet.getInt("sqr_meter"),
                            resultSet.getInt("price_adult"),
                            resultSet.getInt("price_child")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
        }
        return room;
    }

    // Belirli kriterlere göre oda arayan metod
    public static ArrayList<Room> search(String startDateStr, String endDateStr, String city, String searchMixValue) {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT r.* FROM room r " +
                "JOIN season s ON r.season_id = s.id " +
                "JOIN otel h ON r.otel_id = h.id " +
                "WHERE ? BETWEEN s.start_date AND s.end_date " +
                "AND ? BETWEEN s.start_date AND s.end_date " +
                "AND h.city  = '" + city + "'" +
                "AND h.name  = '" + searchMixValue + "'";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
            java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getInt("lodgings_id"),
                        resultSet.getInt("season_id"),
                        resultSet.getString("features"),
                        resultSet.getString("name"),
                        resultSet.getInt("stock"),
                        resultSet.getInt("bed_number"),
                        resultSet.getInt("sqr_meter"),
                        resultSet.getInt("price_adult"),
                        resultSet.getInt("price_child")
                );
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    // Oda stok miktarını güncelleyen metod
    public static boolean update(int roomNo, int i) {
        String query = "UPDATE room SET stock = ? WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, i);
            statement.setInt(2, roomNo);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    // Getter ve setter metodları
    public int getPrice_adult() {
        return price_adult;
    }

    public void setPrice_adult(int price_adult) {
        this.price_adult = price_adult;
    }

    public int getPrice_child() {
        return price_child;
    }

    public void setPrice_child(int price_child) {
        this.price_child = price_child;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Lodgings getLodgings() {
        return lodgings;
    }

    public void setLodgings(Lodgings lodgings) {
        this.lodgings = lodgings;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    // Tüm odaları listeleyen metod
    public static ArrayList<Room> getList() {
        ArrayList<Room> roomArrayList = new ArrayList<>();
        String query = Contanct.LIST_QUERY("room");

        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getInt("lodgings_id"),
                        resultSet.getInt("season_id"),
                        resultSet.getString("features"),
                        resultSet.getString("name"),
                        resultSet.getInt("stock"),
                        resultSet.getInt("bed_number"),
                        resultSet.getInt("sqr_meter"),
                        resultSet.getInt("price_adult"),
                        resultSet.getInt("price_child")
                );
                roomArrayList.add(room);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + throwables.getMessage());
        }
        return roomArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public void setOtel_id(int otel_id) {
        this.otel_id = otel_id;
    }

    public int getLodgings_id() {
        return lodgings_id;
    }

    public void setLodgings_id(int lodgings_id) {
        this.lodgings_id = lodgings_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public String getSeason_name() {
        return season.getName();
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBed_number() {
        return bed_number;
    }

    public void setBed_number(int bed_number) {
        this.bed_number = bed_number;
    }

    public int getSqr_meter() {
        return sqr_meter;
    }

    public void setSqr_meter(int sqr_meter) {
        this.sqr_meter = sqr_meter;
    }
}
