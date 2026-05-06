package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {

        setTitle("Admin Dashboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔵 BACKGROUND
        JPanel main = new JPanel(new GridBagLayout()) {
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

        add(main);

        // ⚪ CARD
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(350, 300));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        main.add(card);

        // 🔹 TITLE
        JLabel title = new JLabel("Admin Panel");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 BUTTON STYLE
        Dimension btnSize = new Dimension(250, 40);

        JButton btnAddTrain = new JButton("Add Train");
        JButton btnViewBookings = new JButton("View Bookings");
        JButton btnDeleteTrain = new JButton("Delete Train");
        JButton btnLogout = new JButton("Logout");

        JButton[] buttons = {btnAddTrain, btnViewBookings, btnDeleteTrain, btnLogout};

        for (JButton btn : buttons) {
            btn.setMaximumSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(new Color(37, 99, 235));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(false);
        }

        // 📦 ADD COMPONENTS
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        card.add(btnAddTrain);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(btnViewBookings);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(btnDeleteTrain);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(btnLogout);

        // 🔥 ACTIONS

        btnAddTrain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddTrainPage().setVisible(true);
                dispose();
            }
        });

        btnViewBookings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminViewBookings().setVisible(true);
                dispose();
            }
        });

        btnDeleteTrain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DeleteTrainPage().setVisible(true);
                dispose();
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }
}