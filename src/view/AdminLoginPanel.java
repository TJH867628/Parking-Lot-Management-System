package view;

import javax.swing.*;
import java.awt.*;
import controller.ParkingController;

public class AdminLoginPanel extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AdminLoginPanel() {
        setTitle("Admin Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel()); // filler cell
        panel.add(loginButton);

        add(panel);

        // Action listener for login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticate(username, password)) {
                // Close login window
                dispose();

                // ✅ Directly open AdminDashboardPanel in a new JFrame
                SwingUtilities.invokeLater(() -> {
                    JFrame adminFrame = new JFrame("Admin Dashboard");
                    adminFrame.setSize(1000, 700);
                    adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    adminFrame.setLocationRelativeTo(null);

                    adminFrame.setContentPane(new AdminDashboardPanel(new ParkingController()));
                    adminFrame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        });
    }

    private boolean authenticate(String username, String password) {
        // ✅ Accept admin / 123
        return "admin".equals(username) && "123".equals(password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminLoginPanel().setVisible(true);
        });
    }
}
