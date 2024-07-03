package View;

import Helper.Helper;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.Date;

public class ReservationGUI extends JFrame {
    private JPanel wrapper;  // Ana panel
    private JTable tbl_reservation_list;  // Rezervasyon listesini gösteren tablo
    private JTextField fld_reservation_id;  // Rezervasyon ID alanı
    private JButton btn_reservation_delete;  // Rezervasyon silme butonu
    private JTextField fld_room_no;  // Oda numarası alanı
    private JTextField fld_user_name;  // Kullanıcı adı alanı
    private JButton btn_reservation_add;  // Rezervasyon ekleme butonu
    private JTextField fld_start_date;  // Başlangıç tarihi alanı
    private JTextField fld_finish_date;  // Bitiş tarihi alanı
    private JTextField fld_person_numbers;  // Kişi sayısı alanı
    private JTextField fld_child_numbers;  // Çocuk sayısı alanı
    private JTextField stay_day;  // Konaklama günü sayısı alanı
    private JTextField customer_name;  // Müşteri adı alanı
    private JTextField customer_id;  // Müşteri kimlik numarası alanı
    private JTable tbl_room_list_new;  // Oda listesini gösteren yeni tablo

    private DefaultTableModel model_reservation_list;  // Rezervasyon listesi tablosu modeli
    private Object[] row_reservation_list;  // Rezervasyon listesinin bir satırı
    private DefaultTableModel model_room_list;  // Oda listesi tablosu modeli

    // Constructor (Yapıcı metot)
    public ReservationGUI(Room room, Employee employee) {
        add(wrapper);  // Ana paneli ekle
        setSize(800, 800);  // Pencere boyutunu ayarla
        setTitle("Rezervasyon");  // Pencere başlığını ayarla
        setLocationRelativeTo(null);  // Ekranda ortada açılması için
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Pencere kapatıldığında sadece bu pencereyi kapat
        setResizable(true);  // Pencerenin yeniden boyutlandırılabilir olmasını ayarla
        setVisible(true);  // Pencereyi görünür yap

        // Alanlara başlangıç değerlerini ata
        fld_user_name.setText(employee.getName());
        fld_room_no.setText(String.valueOf(room.getId()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fld_start_date.setText(sdf.format(room.getSeason().getStart_date()));
        fld_finish_date.setText(sdf.format(room.getSeason().getEnd_date()));

        // Rezervasyon listesi tablosu modelini oluştur
        model_reservation_list = new DefaultTableModel();
        Object[] col_reservation_list = {"Otel ID", "Çalışan ID", "Müşteri Adı", "Müşteri Kimlik No", "Başlangıç Tarihi", "Bitiş Tarihi", "Toplam Fiyat"};
        model_reservation_list.setColumnIdentifiers(col_reservation_list);
        row_reservation_list = new Object[col_reservation_list.length];
        tbl_reservation_list.setModel(model_reservation_list);  // Tabloya modeli ata
        loadTableReservationList();  // Rezervasyon listesini yükle

        // Rezervasyon listesindeki satır seçimi dinleyicisi
        tbl_reservation_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                int selected_row = tbl_reservation_list.getSelectedRow();
                fld_reservation_id.setText(model_reservation_list.getValueAt(selected_row, 0).toString());
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        // Rezervasyon silme butonuna tıklama olayı
        btn_reservation_delete.addActionListener(e -> {
            if (fld_reservation_id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Rezervasyon ID boş olamaz!");
            } else {
                int reservation_id = Integer.parseInt(fld_reservation_id.getText());
                if (Helper.confirm("Rezervasyonu silmek istediğinizden emin misiniz?", "UYARI", 2)) {
                    if (Reservation.delete(reservation_id)) {
                        JOptionPane.showMessageDialog(null, "Rezervasyon silindi!");
                        if (Room.update(room.getId(), room.getStock() + 1)) {
                            JOptionPane.showMessageDialog(null, "Oda stoğu güncellendi!", "UYARI", 2);
                            loadTableReservationList();  // Rezervasyon listesini yeniden yükle
                        } else {
                            JOptionPane.showMessageDialog(null, "Oda stoğu güncellenemedi!", "UYARI", 2);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Rezervasyon silinemedi!");
                    }
                }
            }
            loadRoomModel();  // Oda modelini yeniden yükle
        });

        // Rezervasyon ekleme butonuna tıklama olayı
        btn_reservation_add.addActionListener(e -> {
            if (fld_room_no.getText().isEmpty() && fld_user_name.getText().isEmpty() && fld_start_date.getText().isEmpty() && fld_finish_date.getText().isEmpty() && fld_person_numbers.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tüm alanlar dolu olmalıdır!");
            } else {
                int room_no = Integer.parseInt(fld_room_no.getText());
                String user_name = fld_user_name.getText();
                String start_date = fld_start_date.getText();
                String finish_date = fld_finish_date.getText();

                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = sdf.parse(fld_start_date.getText());
                    endDate = sdf.parse(fld_finish_date.getText());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

                if (Helper.confirm("Rezervasyonu ücretini ödemek istediğinizden emin misiniz?", "UYARI", 2)) {
                    int people = Integer.parseInt(fld_person_numbers.getText());
                    int children = Integer.parseInt(fld_child_numbers.getText());
                    int stay_day_num = Integer.parseInt(stay_day.getText());
                    int priceAdult = people * stay_day_num * room.getPrice_adult();
                    int priceChild = children * stay_day_num * room.getPrice_child();
                    int price = priceAdult + priceChild;
                    if(room.getSeason_name().equals("Yaz Sezonu")){
                        price = price * 2;
                    }
                    System.out.println("getSeason_name" + room.getSeason_name());
                    if (Helper.confirm("Ödenmesi gereken ücret: " + price + " TL", "UYARI", 2)) {
                        if (Reservation.add(room.getOtel_id(), employee.getId(), customer_name.getText(), customer_id.getText(), start_date, finish_date, price)) {
                            int stock = room.getStock() - 1;
                            if (Room.update(room_no, stock)) {
                                JOptionPane.showMessageDialog(null, "Oda stoğu güncellendi!", "UYARI", 2);
                                loadTableReservationList();  // Rezervasyon listesini yeniden yükle
                            } else {
                                JOptionPane.showMessageDialog(null, "Oda stoğu güncellenemedi!", "UYARI", 2);
                            }
                            JOptionPane.showMessageDialog(null, "Rezervasyon eklendi!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Rezervasyon eklenemedi!");
                        }
                    }
                }
            }
            loadRoomModel();  // Oda modelini yeniden yükle
        });
    }

    // Oda modelini yükleme metodu
    private void loadRoomModel() {
        if (tbl_room_list_new == null) {
            //throw new NullPointerException("tbl_room_list_new is null");
        }

        DefaultTableModel model_room_clear = (DefaultTableModel) tbl_room_list_new.getModel();
        if (model_room_clear == null) {
            throw new NullPointerException("tbl_room_list_new.getModel() returned null");
        }
        model_room_clear.setRowCount(0);

        Object[] row_room_list = new Object[11]; // Uygun boyutta başlatın

        ArrayList<Room> roomArrayList = Room.getList();
        if (roomArrayList == null) {
            throw new NullPointerException("Room.getList() returned null");
        }

        if (!roomArrayList.isEmpty()) {
            for (Room obj : roomArrayList) {
                int i = 0;
                row_room_list[i++] = obj.getId();

                Hotel hotel = obj.getHotel();
                if (hotel == null) {
                    System.err.println("Hotel not found for Room ID: " + obj.getId());
                    continue; // Bu nesneyi atla ve bir sonraki nesneye geç
                }
                row_room_list[i++] = hotel.getName();

                Lodgings lodgings = obj.getLodgings();
                if (lodgings == null) {
                    System.err.println("Lodgings not found for Room ID: " + obj.getId());
                    continue; // Bu nesneyi atla ve bir sonraki nesneye geç
                }
                row_room_list[i++] = lodgings.getType();

                Season season = obj.getSeason();
                if (season == null) {
                    System.err.println("Season not found for Room ID: " + obj.getId());
                    continue; // Bu nesneyi atla ve bir sonraki nesneye geç
                }
                row_room_list[i++] = season.getName();

                row_room_list[i++] = obj.getName();
                row_room_list[i++] = obj.getFeatures();
                row_room_list[i++] = obj.getBed_number();
                row_room_list[i++] = obj.getSqr_meter();
                row_room_list[i++] = obj.getStock();
                row_room_list[i++] = obj.getPrice_adult() + " TL";
                row_room_list[i++] = obj.getPrice_child() + " TL";

                model_room_clear.addRow(row_room_list);
            }
        } else {
            System.out.println("Oda listesi boş.");
        }
    }

    // Rezervasyon listesini yükleme metodu
    private void loadTableReservationList() {
        model_reservation_list.setRowCount(0);  // Tabloyu temizle
        for (Reservation reservation : Reservation.getList()) {  // Rezervasyon listesini al
            row_reservation_list[0] = reservation.getId();
            row_reservation_list[1] = reservation.getEmployeeId();
            row_reservation_list[2] = reservation.getCustomer_name();
            row_reservation_list[3] = reservation.getCustomer_id();
            row_reservation_list[4] = reservation.getStartDate();
            row_reservation_list[5] = reservation.getEndDate();
            row_reservation_list[6] = reservation.getPrice();
            model_reservation_list.addRow(row_reservation_list);  // Satırı tabloya ekle
        }
    }
}
