package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterPage extends JFrame {

    public RegisterPage() {

        setTitle("Register");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔵 SAME GRADIENT BACKGROUND AS LOGIN
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

        // ⚪ CARD (same size as login)
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(360, 340));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        main.add(card);

        // 🔹 TITLE
        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 USERNAME
        JLabel lblUser = new JLabel("Username", SwingConstants.CENTER);
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtUser = new JTextField();
        txtUser.setMaximumSize(new Dimension(260, 35));
        txtUser.setBackground(new Color(240, 240, 240));
        txtUser.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txtUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 PASSWORD
        JLabel lblPass = new JLabel("Password", SwingConstants.CENTER);
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(260, 35));
        txtPass.setBackground(new Color(240, 240, 240));
        txtPass.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 CONFIRM PASSWORD
        JLabel lblConfirm = new JLabel("Confirm Password", SwingConstants.CENTER);
        lblConfirm.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField txtConfirm = new JPasswordField();
        txtConfirm.setMaximumSize(new Dimension(260, 35));
        txtConfirm.setBackground(new Color(240, 240, 240));
        txtConfirm.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txtConfirm.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔵 REGISTER BUTTON (same style as login)
        JButton btnRegister = new JButton("Register");
        btnRegister.setMaximumSize(new Dimension(260, 40));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnRegister.setBackground(new Color(37, 99, 235));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setFocusPainted(false);
        btnRegister.setOpaque(true);
        btnRegister.setContentAreaFilled(true);
        btnRegister.setBorderPainted(false);

        // 🔙 BACK TEXT (same style as login bottom)
        JLabel back = new JLabel("Back to Login", SwingConstants.CENTER);
        back.setForeground(Color.GRAY);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 📦 ADD COMPONENTS
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        card.add(lblUser);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(txtUser);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(lblPass);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(txtPass);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(lblConfirm);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(txtConfirm);

        card.add(Box.createRigidArea(new Dimension(0, 25)));

        card.add(btnRegister);

        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(back);

        // 🔥 YOUR ORIGINAL LOGIC (UNCHANGED)
        btnRegister.addActionListener(e -> {

            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            String confirm = new String(txtConfirm.getPassword());

            if (user.equals("") || pass.equals("")) {
                JOptionPane.showMessageDialog(null, "Fill all fields");
            } else if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(null, "Password not matching");
            } else {
                try {
                    java.sql.Connection con = DBConnection.getConnection();

                    java.sql.PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO users(username, password) VALUES (?, ?)"
                    );

                    ps.setString(1, user);
                    ps.setString(2, pass);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Registered Successfully");

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        // 🔙 BACK CLICK
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }
}