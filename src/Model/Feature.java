package Model;

import Helper.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Feature {
    private int id;
    private String features;
    private String type;

    // Constructor: Yeni bir Feature nesnesi oluşturur
    public Feature(int id, String features, String type) {
        this.id = id;
        this.features = features;
        this.type = type;
    }

    // Belirli bir tipe göre Feature listesini döner
    public static ArrayList<Feature> getList(String type) {
        // Belirtilen tipe göre tüm özellikleri çekmek için SQL sorgusu
        String query = "SELECT * FROM features WHERE type = '" + type + "'";
        ArrayList<Feature> featureArrayList = new ArrayList<>();
        Feature featureObject;
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // Sonuçları Feature nesnesine atar ve listeye ekler
                featureObject = new Feature(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getString("name")
                );
                featureArrayList.add(featureObject);
            }
            return featureArrayList;
        } catch (SQLException throwables) {
            // Hata durumunda hatayı yazdırır
            throwables.printStackTrace();
        }
        return null;
    }

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
