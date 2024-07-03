package Helper;

import javax.swing.*;

public class Helper {
    // Kullanıcıya mesaj göstermek için metod
    public static void showMessage(String message, String title, int type) {
        // Mesaj kutusunun düğmelerini Türkçe yapar
        optionPageTR();
        // Mesaj kutusunu gösterir
        JOptionPane.showMessageDialog(null, message, title, type);
    }

    // Mesaj kutusunun düğme metinlerini Türkçe yapar
    public static void optionPageTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.cancelButtonText", "Iptal");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayir");
        UIManager.put("OptionPane.inputDialogTitle", "Giris");
    }

    // Bir JTextField'in boş olup olmadığını kontrol eder
    public static boolean isEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    // Uygulamanın görünümünü Nimbus olarak ayarlayan metod
    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    // Birden fazla JTextField'in içeriğini temizleyen metod
    public static void clearTextField(JTextField... fldOtelName) {
        for (JTextField textField : fldOtelName) {
            textField.setText("");
        }
    }

    // Kullanıcıdan onay almak için bir onay kutusu gösteren metod
    public static boolean confirm(String message, String title, int type) {
        // Onay kutusunun düğmelerini Türkçe yapar
        optionPageTR();
        // Onay kutusunu gösterir ve kullanıcının seçimini alır
        int select = JOptionPane.showConfirmDialog(null, message, title, type);
        // Kullanıcının seçimini döner (Evet ise true, Hayır veya İptal ise false)
        return select == 0;
    }
}
