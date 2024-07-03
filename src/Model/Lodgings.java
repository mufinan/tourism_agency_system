package Model;

import Helper.Contanct;
import Helper.DBConnector;
import Helper.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Lodgings {
    private int id;
    private int otel_id;
    private String type;

    // Constructor to initialize Lodgings object
    public Lodgings(int id, int otel_id, String type) {
        this.id = id;
        this.otel_id = otel_id;
        this.type = type;
    }

    // Method to get a list of lodgings based on the hotel ID
    public static ArrayList<Lodgings> getList(int otel_id) {
        ArrayList<Lodgings> lodgingsArrayList = new ArrayList<>();
        try {
            // Create a statement to execute the query
            Statement statement = DBConnector.getConnection().createStatement();
            // Execute the query to list lodgings with the given hotel ID
            statement.execute(Contanct.LIST_QUERY_PARAMETRE("lodgings", "otel_id", otel_id));
            ResultSet resultSet = statement.getResultSet();
            // Loop through the result set and create Lodgings objects
            while (resultSet.next()) {
                Lodgings lodgings = new Lodgings(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getString("type")
                );
                lodgingsArrayList.add(lodgings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return lodgingsArrayList;
        }
    }

    // Method to add a new lodging
    public static boolean add(String otel_id, String feature) {
        // SQL query to insert a new lodging
        String query = "INSERT INTO lodgings (otel_id, type) VALUES (" +
                otel_id + ", '" +
                feature + "')";

        // Check if the lodging already exists
        Lodgings lodgings = Lodgings.getFetchLodgings(otel_id, feature);
        if (lodgings != null) {
            Helper.showMessage("Bu özellik zaten ekli", "Hata", 2);
            return false;
        }
        try {
            // Execute the insert query
            Statement statement = DBConnector.getConnection().createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch a specific lodging based on hotel ID and type
    private static Lodgings getFetchLodgings(String otelId, String feature) {
        String query = "SELECT * FROM lodgings WHERE otel_id = " + otelId + " AND type = '" + feature + "'";
        Lodgings lodgings = null;
        try {
            // Execute the query to fetch the lodging
            Statement statement = DBConnector.getConnection().createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            // Create a Lodgings object if the result set has a result
            while (resultSet.next()) {
                lodgings = new Lodgings(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getString("type")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lodgings;
    }

    // Method to delete a lodging based on its ID
    public static boolean delete(int lodging_id) {
        // SQL query to delete the lodging
        String query = Contanct.DELETE_QUERY("lodgings", lodging_id);
        try {
            // Execute the delete query
            Statement statement = DBConnector.getConnection().createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch a specific lodging based on its ID
    public static Lodgings getFetch(int lodgingsId) {
        String query = Contanct.FETCH_QUERY("lodgings", lodgingsId);
        Lodgings lodgings = null;
        try {
            // Execute the query to fetch the lodging
            Statement statement = DBConnector.getConnection().createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            // Create a Lodgings object if the result set has a result
            while (resultSet.next()) {
                lodgings = new Lodgings(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getString("type")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lodgings;
    }

    // Overloaded method to fetch a list of lodgings based on the type
    public static ArrayList<Lodgings> getFetch(String lodgingsType) {
        String query = Contanct.FETCH_QUERY("lodgings", "type", lodgingsType);
        Lodgings lodgings = null;
        ArrayList<Lodgings> lodgingsArrayList = new ArrayList<>();
        try {
            // Execute the query to fetch the lodgings
            Statement statement = DBConnector.getConnection().createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            // Loop through the result set and create Lodgings objects
            while (resultSet.next()) {
                lodgings = new Lodgings(
                        resultSet.getInt("id"),
                        resultSet.getInt("otel_id"),
                        resultSet.getString("type")
                );
                lodgingsArrayList.add(lodgings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lodgingsArrayList;
    }

    // Method to fetch a specific lodging based on its type and hotel ID
    public static Lodgings getFetchByType(String type, int hotelId) {
        String query = "SELECT * FROM lodgings WHERE type = ? AND otel_id = ?";
        Lodgings lodgings = null;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, type);
            statement.setInt(2, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    lodgings = new Lodgings(
                            resultSet.getInt("id"),
                            resultSet.getInt("otel_id"),
                            resultSet.getString("type")
                    );
                } else {
                    System.err.println("Lodgings not found with type: " + type + " for hotel ID: " + hotelId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Veritabanı sorgusu sırasında bir hata oluştu: " + e.getMessage());
        }
        return lodgings;
    }

    // Getter and setter methods for Lodgings class properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOtel_id() {
        return otel_id;
    }

    public int getOtel_Name() {
        return otel_id;
    }

    public void setOtel_id(int otel_id) {
        this.otel_id = otel_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
