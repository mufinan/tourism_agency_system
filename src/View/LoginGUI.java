package View;

import Helper.Helper;
import Model.Admin;
import Model.Employee;
import Model.User;

import javax.swing.*;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JPanel pnl_bottom;
    private JPanel pnl_top;

    // Constructor to initialize the LoginGUI
    public LoginGUI() {
        add(wrapper);
        setSize(600, 600);
        setTitle("Login");
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        // Action listener for the login button
        btn_login.addActionListener(e -> {
            // Check if username or password fields are empty
            if (Helper.isEmpty(fld_username) || Helper.isEmpty(fld_password)) {
                Helper.showMessage("Kullanıcı adı veya şifre boş olamaz!", "UYARI", JOptionPane.WARNING_MESSAGE);
            } else {
                // Get user credentials and validate them
                User user = User.getCredentials(fld_username.getText(), fld_password.getText());
                if (user == null) {
                    Helper.showMessage("Kullanıcı adı veya şifre hatalı!", "UYARI", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Switch case to open the appropriate GUI based on user type
                    switch (user.getType()) {
                        case "admin":
                            dispose();
                            new AdminGUI((Admin) user);
                            break;
                        case "employee":
                            dispose();
                            new EmployeeGUI((Employee) user);
                            break;
                        default:
                            Helper.showMessage("Kullanıcı tipi hatalı!", "UYARI", JOptionPane.WARNING_MESSAGE);
                            break;
                    }
                }
            }
        });
    }

    // Main method to set the look and feel and create the login GUI
    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
