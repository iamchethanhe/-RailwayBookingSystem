package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {

    public LoginPage() {

        setTitle("Login");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔵 BACKGROUND
        JPanel main = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(58, 123, 213),
                        getWidth(), getHeight(), new Color(0, 210, 255)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        main.setLayout(new GridBagLayout());
        add(main);

        // ⚪ CARD
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(360, 320));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        main.add(card);

        // 🔹 TITLE
        JLabel title = new JLabel("Sign In", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 USERNAME
        JLabel userLabel = new JLabel("Username", SwingConstants.CENTER);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField txtUser = new JTextField();
        txtUser.setMaximumSize(new Dimension(260, 35));
        txtUser.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txtUser.setBackground(new Color(240, 240, 240));
        txtUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 PASSWORD
        JLabel passLabel = new JLabel("Password", SwingConstants.CENTER);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPasswordField txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(260, 35));
        txtPass.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txtPass.setBackground(new Color(240, 240, 240));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔵 LOGIN BUTTON
        JButton btnLogin = new JButton("Login");
        btnLogin.setMaximumSize(new Dimension(260, 40));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.setBackground(new Color(37, 99, 235));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnLogin.setOpaque(true);
        btnLogin.setContentAreaFilled(true);

        // 🔹 REGISTER TEXT
        JLabel register = new JLabel("Create new account", SwingConstants.CENTER);
        register.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        register.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setForeground(Color.GRAY);

        // 📦 ADD COMPONENTS
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(userLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(txtUser);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(passLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(txtPass);

        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(btnLogin);

        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(register);

        // 🔥 LOGIN LOGIC (UNCHANGED)
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String user = txtUser.getText();
                String pass = new String(txtPass.getPassword());

                if(user.equals("admin") && pass.equals("admin123")) {
                    new AdminDashboard().setVisible(true);
                    dispose();
                } else {
                    try {
                        java.sql.Connection con = DBConnection.getConnection();

                        java.sql.PreparedStatement ps = con.prepareStatement(
                                "SELECT * FROM users WHERE username=? AND password=?"
                        );

                        ps.setString(1, user);
                        ps.setString(2, pass);

                        java.sql.ResultSet rs = ps.executeQuery();

                        if(rs.next()) {
                            Dashboard.user = user;
                            new Dashboard().setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Login");
                        }

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });

        // REGISTER CLICK
        register.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegisterPage().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}