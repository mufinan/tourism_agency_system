
# Turizm Acentası Sistemi

## Genel Bakış
Turizm Acentası Sistemi, bir turizm acentasının çeşitli yönlerini yönetmek için tasarlanmış Java tabanlı bir uygulamadır. Sistem, kullanıcılar, çalışanlar, rezervasyonlar, oteller, odalar ve daha fazlasını yönetme işlevsellikleri içerir. Verilerin depolanması için PostgreSQL veritabanı kullanır.

## Proje Yapısı
- **src/**
  - **postgresql-42.7.3.jar**: PostgreSQL JDBC sürücüsü.
  - **tourism_agency_system.sql**: Veritabanı kurulumu için SQL dosyası.
  - **Helper/**: Yardımcı ve yardımcı sınıfları içerir.
    - `Contanct.java`
    - `DBConnector.java`
    - `Helper.java`
  - **Images/**: Görsel varlıkları içerir.
    - `loginLogo.jpeg`
    - `tourism login.png`
  - **Model/**: Çeşitli varlıkları temsil eden veri modellerini içerir.
    - `Admin.java`
    - `Employee.java`
    - `Feature.java`
    - `Hotel.java`
    - `Lodgings.java`
    - `Reservation.java`
    - `Room.java`
    - `Season.java`
    - `User.java`
  - **View/**: Grafik kullanıcı arayüzü (GUI) bileşenlerini içerir.
    - `AdminGUI.form`
    - `AdminGUI.java`
    - `EmployeeGUI.form`
    - `EmployeeGUI.java`
    - `FeaturesGUI.form`
    - `FeaturesGUI.java`
    - `LoginGUI.form`
    - `LoginGUI.java`
    - `ReservationGUI.form`
    - `ReservationGUI.java`
    - `RoomFeaturesGUI.form`
    - `RoomFeaturesGUI.java`

## Kurulum ve Kullanım
1. **Veritabanı Kurulumu**: `tourism_agency_system.sql` dosyasını kullanarak PostgreSQL veritabanını oluşturun ve gerekli tabloları oluşturun.
2. **Projeyi Çalıştırma**: 
   - `postgresql-42.7.3.jar` dosyasını projeye ekleyin.
   - Projeyi bir Java IDE'sinde açın ve gerekli bağımlılıkları ekleyin.
   - Veritabanı bağlantı ayarlarını `DBConnector.java` dosyasında yapılandırın.
   - Uygulamayı çalıştırın ve GUI üzerinden işlevleri kullanmaya başlayın.

## Önemli Notlar
Bu projede odalara ait stok miktarları rezervasyon eklendikçe veya silindikçe artıp/azaltmaktadır. İlgili artış/azalış veritabanına yansımaktadır. Fakat arayüzde rezervasyon işlemi sonrasında ilgili değişiklik görülmemekte, ancak uygulamadan çıkış yapılıp tekrar girildiğinde güncellenmiş stok bilgileri görüntülenebilmektedir.

## Lisans
Bu proje MIT lisansı altında lisanslanmıştır.
