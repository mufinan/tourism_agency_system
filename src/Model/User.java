package Model;

import Helper.Contanct;  // Veritabanı sorgularını içeren yardımcı sınıfı içe aktarır
import Helper.DBConnector;  // Veritabanı bağlantısını sağlayan yardımcı sınıfı içe aktarır

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private int id;  // Kullanıcının benzersiz kimliği
    private String username;  // Kullanıcının kullanıcı adı
    private String password;  // Kullanıcının şifresi
    private String type;  // Kullanıcı tipi (admin, employee vb.)
    private String name;  // Kullanıcının adı

    // Parametreli yapıcı metot
    public User(int id, String username, String password, String type, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.name = name;
    }

    // Parametresiz yapıcı metot
    public User() {
    }

    // Kullanıcının kimlik bilgilerini almak için statik bir metot
    public static User getCredentials(String username, String password) {
        User user = null;  // Geri döndürülecek kullanıcı nesnesi
        String query = Contanct.SELECT_USER;  // Kullanıcı sorgusunu içeren SQL ifadesi
        try {
            // Veritabanı bağlantısını al ve hazırlıklı bir ifade oluştur
            PreparedStatement pr = DBConnector.getConnection().prepareStatement(query);
            pr.setString(1, username);  // SQL sorgusunun ilk parametresine kullanıcı adını yerleştir
            pr.setString(2, password);  // SQL sorgusunun ikinci parametresine şifreyi yerleştir
            ResultSet rs = pr.executeQuery();  // Sorguyu çalıştır ve sonuçları al
            if (rs.next()) {  // Eğer sorgu sonuç döndürdüyse
                // Kullanıcı tipine göre nesne oluştur
                switch (rs.getString("type")) {
                    case "admin":
                        user = new Admin();  // Admin nesnesi oluştur
                        break;
                    case "employee":
                        user = new Employee();  // Employee nesnesi oluştur
                        break;
                    default:
                        System.out.println("Kullanıcı tipi hatalı!");  // Geçersiz kullanıcı tipi
                        break;
                }
                // Kullanıcı nesnesinin özelliklerini ayarla
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setType(rs.getString("type"));
                user.setName(rs.getString("name"));
            }
            return user;  // Kullanıcı nesnesini geri döndür
        }catch (Exception e){
            System.out.println(e.getMessage());  // Hata mesajını yazdır
            return null;  // Hata durumunda null geri döndür
        }
    }

    // Kullanıcının id'sini almak için getter metodu
    public int getId() {
        return id;
    }

    // Kullanıcının id'sini ayarlamak için setter metodu
    public void setId(int id) {
        this.id = id;
    }

    // Kullanıcının kullanıcı adını almak için getter metodu
    public String getUsername() {
        return username;
    }

    // Kullanıcının kullanıcı adını ayarlamak için setter metodu
    public void setUsername(String username) {
        this.username = username;
    }

    // Kullanıcının şifresini almak için getter metodu
    public String getPassword() {
        return password;
    }

    // Kullanıcının şifresini ayarlamak için setter metodu
    public void setPassword(String password) {
        this.password = password;
    }

    // Kullanıcının tipini almak için getter metodu
    public String getType() {
        return type;
    }

    // Kullanıcının tipini ayarlamak için setter metodu
    public void setType(String type) {
        this.type = type;
    }

    // Kullanıcının adını almak için getter metodu
    public String getName() {
        return name;
    }

    // Kullanıcının adını ayarlamak için setter metodu
    public void setName(String name) {
        this.name = name;
    }
}
