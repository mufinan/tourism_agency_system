package Model;

import Helper.Contanct;
import Helper.DBConnector;
import Helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Season {
    private int id;
    private String name;
    private int otel_id;
    private Hotel hotel;
    private Date start_date;
    private Date end_date;

    // Constructor to initialize Season object
    public Season(int id, int otel_id, Date start_date, Date end_date, String name) {
        this.id = id;
        this.otel_id = otel_id;
        this.hotel = Hotel.getFetch(otel_id); // Fetching the hotel object associated with the season
        this.start_date = start_date;
        this.end_date = end_date;
        this.name = name;
    }

    // Method to get a list of all seasons
    public static ArrayList<Season> getList() {
        ArrayList<Season> seasonArrayList = new ArrayList<>();
        String query = "SELECT * FROM season";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Season season = new Season(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getString("name")
                );
                seasonArrayList.add(season);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasonArrayList;
    }

    // Method to add a new season
    public static boolean add(int otel_id, Date start_date, Date end_date, String name) {
        String query = "INSERT INTO season (otel_id, start_date, end_date, name) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, otel_id);
            preparedStatement.setDate(2, new java.sql.Date(start_date.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(end_date.getTime()));
            preparedStatement.setString(4, name);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch a specific season based on hotel ID and season name
    private static Season getFetchSeason(int otel_id, String season_name) {
        String query = "SELECT * FROM season WHERE otel_id = ? AND name = ?";
        Season season = null;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, otel_id);
            preparedStatement.setString(2, season_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                season = new Season(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return season;
    }

    // Method to delete a season based on its ID
    public static boolean delete(int i) {
        String query = Contanct.DELETE_QUERY("season", i);
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete all seasons associated with a specific hotel ID
    public static boolean deleteByHotelID(int hotel_id) {
        String query = Contanct.DELETE_QUERY_WHERE("season", "otel_id", hotel_id);
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to search for seasons based on start and end dates
    public static ArrayList<Season> search(Date start_date, Date end_date) {
        String query = "SELECT * FROM season WHERE start_date = ? AND end_date = ?";
        ArrayList<Season> list = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(start_date.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(end_date.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Season season = new Season(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getString("name")
                );
                list.add(season);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Getter and setter methods for Season class properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    // Method to fetch a specific season based on its ID
    public static Season getFetch(int seasonId) {
        String query = Contanct.SELECT_QUERY_WHERE("season", seasonId);
        Season season = null;
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                season = new Season(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return season;
    }

    // Method to fetch a list of seasons based on the season name
    public static ArrayList<Season> getFetch(String seasonName) {
        String query = "SELECT * FROM season WHERE name = ?";
        ArrayList<Season> seasonArrayList = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, seasonName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Season season = new Season(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getString("name")
                );
                seasonArrayList.add(season);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasonArrayList;
    }

    // Method to fetch a specific season based on its ID
    public static Season getFetchById(int id) {
        String query = "SELECT * FROM season WHERE id = ?";
        Season season = null;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    season = new Season(
                            resultSet.getInt("id"),
                            resultSet.getInt("otel_id"),
                            resultSet.getDate("start_date"),
                            resultSet.getDate("end_date"),
                            resultSet.getString("name")
                    );
                } else {
                    System.err.println("Season not found for ID: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
        }
        return season;
    }
}
