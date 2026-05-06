package gui;

import javax.swing.*;
import java.awt.*;

public class AdminViewBookings extends JFrame {

    JPanel listPanel;

    public AdminViewBookings() {

        setTitle("All Bookings");
        setSize(800, 500);
        setLocationRelativeTo(null);

        // 🔵 BACKGROUND
        JPanel main = new JPanel(new BorderLayout()) {
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

        // 🔹 TITLE
        JLabel title = new JLabel("All Bookings", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        main.add(title, BorderLayout.NORTH);

        // 📜 LIST
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        main.add(scroll, BorderLayout.CENTER);

        // 🔙 BACK
        JButton btnBack = new JButton("Back");
        btnBack.setPreferredSize(new Dimension(120, 35));
        btnBack.setBackground(new Color(37, 99, 235));
        btnBack.setForeground(Color.WHITE);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(btnBack);

        main.add(bottom, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });

        loadBookings();
    }

    void loadBookings() {

        try {
            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM bookings"
            );

            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String user = rs.getString("username");
                String train = rs.getString("train_name");
                String category = rs.getString("category");
                String date = rs.getString("journey_date");

                addCard(user, train, category, date);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void addCard(String user, String train, String category, String date) {

        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(700, 100));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel left = new JLabel("<html><b>" + train + "</b><br>User: " + user + "</html>");
        JLabel right = new JLabel("<html>" + category + "<br>" + date + "</html>");

        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);

        listPanel.add(card);
        listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}