package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewTrainPage extends JFrame {

    JPanel listPanel;

    public ViewTrainPage(String from, String to, String date) {

        setTitle("Available Trains");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔵 GRADIENT BACKGROUND
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

        // 🔹 TITLE
        JLabel title = new JLabel("Available Trains", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        main.add(title, BorderLayout.NORTH);

        // 📜 LIST PANEL (WHITE BACKGROUND)
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

        // 🔙 BACK BUTTON PANEL
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(230, 240, 250));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton btnBack = new JButton("Back");
        btnBack.setPreferredSize(new Dimension(120, 35));
        btnBack.setBackground(new Color(37, 99, 235));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setFocusPainted(false);
        btnBack.setOpaque(true);
        btnBack.setContentAreaFilled(true);
        btnBack.setBorderPainted(false);

        bottomPanel.add(btnBack);
        main.add(bottomPanel, BorderLayout.SOUTH);

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchTrainPage().setVisible(true);
                dispose();
            }
        });

        // 🔥 LOAD DATA
        loadTrains(from, to, date);
    }

    // 🔥 LOAD FROM DATABASE
    void loadTrains(String from, String to, String date) {

        try {
            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM trains WHERE LOWER(source)=LOWER(?) AND LOWER(destination)=LOWER(?)"
            );

            ps.setString(1, from);
            ps.setString(2, to);

            java.sql.ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                String name = rs.getString("train_name");
                String source = rs.getString("source");
                String dest = rs.getString("destination");
                int price = rs.getInt("price");

                String times[] = {
                        "05:00 AM", "08:00 AM", "12:00 PM",
                        "04:00 PM", "09:00 PM"
                };

                for (int i = 0; i < times.length; i++) {
                    addTrainCard(name, source, dest, price, times[i], date);
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "No trains found");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 🚆 TRAIN CARD UI
    void addTrainCard(String name, String source, String dest, int price, String time, String date) {

        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(750, 120));

        // ✅ WHITE CLEAN CARD
        card.setBackground(Color.WHITE);

        // ✅ LIGHT BORDER
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // 🔹 LEFT
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);

        JLabel lblName = new JLabel(name);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblRoute = new JLabel(source + " → " + dest);

        left.add(lblName);
        left.add(lblRoute);

        // 🔹 RIGHT
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);

        JLabel lblTime = new JLabel("Time: " + time);
        JLabel lblDate = new JLabel("Date: " + date);

        right.add(lblTime);
        right.add(lblDate);

        // 🔹 BOTTOM
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JPanel leftBtns = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        leftBtns.setOpaque(false);

        JButton btnAC = new JButton("AC ₹" + (price + 500));
        JButton btnNonAC = new JButton("Non-AC ₹" + (price + 200));
        JButton btnSleeper = new JButton("Sleeper ₹" + (price + 100));

        leftBtns.add(btnAC);
        leftBtns.add(btnNonAC);
        leftBtns.add(btnSleeper);

        // 🔵 BOOK BUTTON
        JButton btnBook = new JButton("Book");
        btnBook.setPreferredSize(new Dimension(100, 35));
        btnBook.setBackground(new Color(37, 99, 235));
        btnBook.setForeground(Color.WHITE);
        btnBook.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBook.setFocusPainted(false);
        btnBook.setOpaque(true);
        btnBook.setContentAreaFilled(true);
        btnBook.setBorderPainted(false);

        btnBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BookingPage(name, source, dest, price, date).setVisible(true);
                dispose();
            }
        });

        bottom.add(leftBtns, BorderLayout.WEST);
        bottom.add(btnBook, BorderLayout.EAST);

        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);
        card.add(bottom, BorderLayout.SOUTH);

        listPanel.add(card);

        // spacing
        listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}