package gui;

import javax.swing.*;
import java.awt.*;

public class ViewTicketPage extends JFrame {

    JPanel listPanel;

    public ViewTicketPage() {

        setTitle("My Tickets");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔵 Gradient Background
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

        main.setLayout(new BorderLayout());
        add(main);

        // 🔹 Title
        JLabel title = new JLabel("My Tickets", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        main.add(title, BorderLayout.NORTH);

        // 📜 Ticket List Panel
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

        // 🔙 Bottom Panel
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);

        JButton btnBack = new JButton("Back");
        btnBack.setPreferredSize(new Dimension(120, 35));
        btnBack.setBackground(new Color(37, 99, 235));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setFocusPainted(false);
        btnBack.setOpaque(true);
        btnBack.setContentAreaFilled(true);
        btnBack.setBorderPainted(false);

        bottom.add(btnBack);
        main.add(bottom, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            new Dashboard().setVisible(true);
            dispose();
        });

        // 🔥 Load Tickets
        loadTickets();
    }

    // 🔥 Load from DB
    void loadTickets() {

        try {
            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM bookings WHERE username=?"
            );

            ps.setString(1, Dashboard.user);

            java.sql.ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                String user = rs.getString("username");
                String train = rs.getString("train_name");
                String category = rs.getString("category");
                String date = rs.getString("journey_date");

                addTicketCard(user, train, category, date);
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "No tickets found");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 🎫 Ticket Card (LIKE TRAIN CARD)
    void addTicketCard(String user, String train, String category, String date) {

        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(720, 110));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // LEFT SIDE
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);

        JLabel lblTrain = new JLabel(train);
        lblTrain.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblUser = new JLabel("User: " + user);

        left.add(lblTrain);
        left.add(lblUser);

        // RIGHT SIDE
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);

        JLabel lblCategory = new JLabel("Category: " + category);
        JLabel lblDate = new JLabel("Date: " + date);

        right.add(lblCategory);
        right.add(lblDate);

        // BOTTOM
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);

        JLabel status = new JLabel("Status: Confirmed");
        status.setForeground(new Color(34, 197, 94)); // green

        bottom.add(status);

        // ADD ALL
        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);
        card.add(bottom, BorderLayout.SOUTH);

        listPanel.add(card);
        listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}