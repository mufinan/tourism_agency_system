package View;

import Model.Feature;  // Otel özellikleri modelini içe aktarır

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeaturesGUI extends JFrame {
    private JPanel wrapper;  // Ana panel
    private JTable tbl_availables;  // Mevcut otel özelliklerini gösteren tablo
    private JButton btn_add;  // Özellik ekleme butonu
    private JButton btn_delete;  // Özellik silme butonu
    private JTable tbl_selected;  // Seçilen otel özelliklerini gösteren tablo
    private JPanel pnl_availables;  // Mevcut özellikler paneli
    private JButton btn_submit;  // Özellikleri onaylama butonu
    private DefaultTableModel model_otel_features;  // Mevcut otel özellikleri tablosu modeli
    private Object[] row_hotel_features;  // Otel özelliklerinin bir satırı
    private DefaultTableModel model_otel_selected;  // Seçilen otel özellikleri tablosu modeli

    // Constructor (Yapıcı metot)
    public FeaturesGUI(JTextField fdl_otel_features) {
        add(wrapper);  // Ana paneli ekle
        setSize(800, 500);  // Pencere boyutunu ayarla
        setTitle("Otel Ozellikleri");  // Pencere başlığını ayarla
        setLocationRelativeTo(null);  // Ekranda ortada açılması için
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Pencere kapatıldığında sadece bu pencereyi kapat
        setResizable(true);  // Pencerenin yeniden boyutlandırılabilir olmasını ayarla
        setVisible(true);  // Pencereyi görünür yap

        // Tablolar için modelleri oluştur
        model_otel_features = new DefaultTableModel();
        model_otel_selected = new DefaultTableModel();
        Object[] col_hotel_features = {"ID", "Otel Ozellikleri"};  // Tablo sütun başlıkları
        row_hotel_features = new Object[col_hotel_features.length];
        model_otel_features.setColumnIdentifiers(col_hotel_features);  // Sütun başlıklarını model için ayarla
        model_otel_selected.setColumnIdentifiers(col_hotel_features);  // Sütun başlıklarını model için ayarla
        tbl_availables.setModel(model_otel_features);  // Mevcut özellikler tablosuna modeli ata
        tbl_selected.setModel(model_otel_selected);  // Seçilen özellikler tablosuna modeli ata
        loadAvailables();  // Mevcut özellikleri yükle

        // Özellikleri onaylama butonuna tıklama olayı
        btn_submit.addActionListener(e -> {
            for (int i = 0; i < model_otel_selected.getRowCount(); i++) {
                if (i == 0) {
                    fdl_otel_features.setText(model_otel_selected.getValueAt(i, 1).toString());  // İlk özelliği ayarla
                    continue;
                }
                fdl_otel_features.setText(fdl_otel_features.getText() + ", " + model_otel_selected.getValueAt(i, 1).toString());  // Diğer özellikleri ekle
            }
            dispose();  // Pencereyi kapat
        });

        // Özellik ekleme butonuna tıklama olayı
        btn_add.addActionListener(e -> {
            int selectedRow = tbl_availables.getSelectedRow();  // Seçilen satırı al
            if (selectedRow == -1) {
                return;  // Eğer satır seçilmediyse hiçbir şey yapma
            }
            int id = (int) model_otel_features.getValueAt(selectedRow, 0);  // Seçilen satırdaki ID'yi al
            String features = (String) model_otel_features.getValueAt(selectedRow, 1);  // Seçilen satırdaki özelliği al
            model_otel_features.removeRow(selectedRow);  // Seçilen satırı mevcut özellikler tablosundan kaldır
            Object[] row = {id, features};  // Yeni satırı oluştur
            model_otel_selected.addRow(row);  // Yeni satırı seçilen özellikler tablosuna ekle
        });

        // Özellik silme butonuna tıklama olayı
        btn_delete.addActionListener(e -> {
            int selectedRow = tbl_selected.getSelectedRow();  // Seçilen satırı al
            if (selectedRow == -1) {
                return;  // Eğer satır seçilmediyse hiçbir şey yapma
            }
            int id = (int) model_otel_selected.getValueAt(selectedRow, 0);  // Seçilen satırdaki ID'yi al
            String features = (String) model_otel_selected.getValueAt(selectedRow, 1);  // Seçilen satırdaki özelliği al
            model_otel_selected.removeRow(selectedRow);  // Seçilen satırı seçilen özellikler tablosundan kaldır
            Object[] row = {id, features};  // Yeni satırı oluştur
            model_otel_features.addRow(row);  // Yeni satırı mevcut özellikler tablosuna ekle
        });
    }

    // Mevcut özellikleri yükleme metodu
    private void loadAvailables() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_availables.getModel();  // Mevcut özellikler tablosunun modelini al
        clearModel.setRowCount(0);  // Tablodaki tüm satırları temizle
        int i ;
        for (Feature feature : Feature.getList("tesis ozelligi")) {  // Özellik listesini al
            i = 0;
            row_hotel_features[i++] = feature.getId();  // Özelliğin ID'sini satıra ekle
            row_hotel_features[i++] = feature.getType();  // Özelliğin türünü satıra ekle
            model_otel_features.addRow(row_hotel_features);  // Satırı mevcut özellikler tablosuna ekle
        }
    }
}
