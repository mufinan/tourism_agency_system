package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.Statement;
import java.util.ArrayList;

public class Admin extends User {

    // Yeni bir kullanıcı ekler
    public boolean addUser(String username, String password, String type, String name) {
        // Yeni kullanıcı eklemek için SQL sorgusu
        String query = "INSERT INTO tourism_agency_user (username, password, type, name) VALUES ('" + username + "', '" + password + "', '" + type + "', '" + name + "')";
        // Kullanıcının var olup olmadığını kontrol eder
        User user = Admin.getFetchUser(username);
        if (user != null) {
            // Kullanıcı adı zaten varsa hata mesajı gösterir
            Helper.showMessage("Bu kullanıcı adı zaten kullanılıyor.", "Hata", 2);
            return false;
        }
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            // Hata durumunda hatayı yazdırır
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Veritabanından kullanıcı bilgilerini çeker
    private static User getFetchUser(String username) {
        // Kullanıcıyı çekmek için SQL sorgusu
        String query = "SELECT * FROM tourism_agency_user WHERE username = '" + username + "'";
        User obj = null;
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            var resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                // Sonuçları User nesnesine atar
                obj = new Admin();
                obj.setId(resultSet.getInt("id"));
                obj.setUsername(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
                obj.setName(resultSet.getString("name"));
            }
        } catch (Exception e) {
            // Hata durumunda hatayı yazdırır
            System.out.println(e.getMessage());
        }
        return obj;
    }

    // Kullanıcı listesini döner
    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            String query = "SELECT * FROM tourism_agency_user";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // Sonuçları User nesnesine atar ve listeye ekler
                obj = new Admin();
                obj.setId(resultSet.getInt("id"));
                obj.setUsername(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
                obj.setName(resultSet.getString("name"));
                userList.add(obj);
            }
        } catch (Exception e) {
            // Hata durumunda hatayı yazdırır
            System.out.println(e.getMessage());
        }
        return userList;
    }

    // Admin tipindeki kullanıcı listesini döner
    public ArrayList<User> getUserListAdmin() {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            String query = "SELECT * FROM tourism_agency_user Where type = 'admin'";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // Sonuçları User nesnesine atar ve listeye ekler
                obj = new Admin();
                obj.setId(resultSet.getInt("id"));
                obj.setUsername(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
                obj.setName(resultSet.getString("name"));
                userList.add(obj);
            }
        } catch (Exception e) {
            // Hata durumunda hatayı yazdırır
            System.out.println(e.getMessage());
        }
        return userList;
    }

    // Employee tipindeki kullanıcı listesini döner
    public ArrayList<User> getUserListEmployee() {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            String query = "SELECT * FROM tourism_agency_user Where type = 'employee'";
            var resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // Sonuçları User nesnesine atar ve listeye ekler
                obj = new Admin();
                obj.setId(resultSet.getInt("id"));
                obj.setUsername(resultSet.getString("username"));
                obj.setPassword(resultSet.getString("password"));
                obj.setType(resultSet.getString("type"));
                obj.setName(resultSet.getString("name"));
                userList.add(obj);
            }
        } catch (Exception e) {
            // Hata durumunda hatayı yazdırır
            System.out.println(e.getMessage());
        }
        return userList;
    }

    // Kullanıcı bilgilerini günceller
    public boolean updateUser(String text, String text1, String string, String text2) {
        // Kullanıcıyı güncellemek için SQL sorgusu
        String query = "UPDATE tourism_agency_user SET password = '" + text1 + "', type = '" + string + "', name = '" + text2 + "' WHERE username = '" + text + "'";
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            // Hata durumunda hatayı yazdırır
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Kullanıcıyı siler
    public boolean deleteUser(String text) {
        // Kullanıcıyı silmek için SQL sorgusu
        String query = "DELETE FROM tourism_agency_user WHERE username = '" + text + "'";
        try {
            // Veritabanı bağlantısı oluşturur ve sorguyu çalıştırır
            Statement statement = DBConnector.getConnection().createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            // Hata durumunda hatayı yazdırır
            System.out.println(e.getMessage());
            return false;
        }
    }

}
