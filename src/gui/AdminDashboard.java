package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {

    JLabel lblTrains;
    JLabel lblBookings;
    JLabel lblRevenue;
    JLabel lblConfirmed;

    public AdminDashboard() {

        setTitle("Admin Dashboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);

        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        main.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();

        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout());

        main.add(centerPanel, BorderLayout.CENTER);

        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));

        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel trainsCard = createCard("Total Trains", "0");
        JPanel bookingsCard = createCard("Total Bookings", "0");
        JPanel revenueCard = createCard("Revenue", "₹0");
        JPanel confirmedCard = createCard("Confirmed Tickets", "0");

        lblTrains = (JLabel) trainsCard.getComponent(3);
        lblBookings = (JLabel) bookingsCard.getComponent(3);
        lblRevenue = (JLabel) revenueCard.getComponent(3);
        lblConfirmed = (JLabel) confirmedCard.getComponent(3);

        statsPanel.add(trainsCard);
        statsPanel.add(bookingsCard);
        statsPanel.add(revenueCard);
        statsPanel.add(confirmedCard);

        centerPanel.add(statsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnAddTrain = createButton("Add Train");
        JButton btnRemoveTrain = createButton("Remove Train");
        JButton btnViewBookings = createButton("View Bookings");
        JButton btnLogout = createButton("Logout");

        buttonPanel.add(btnAddTrain);
        buttonPanel.add(btnRemoveTrain);
        buttonPanel.add(btnViewBookings);
        buttonPanel.add(btnLogout);

        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadStatistics();

        btnAddTrain.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new AddTrainPage().setVisible(true);

                dispose();
            }
        });

        btnRemoveTrain.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new RemoveTrainPage().setVisible(true);

                dispose();
            }
        });

        btnViewBookings.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new AdminViewBookings().setVisible(true);

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

    JPanel createCard(String title, String value) {

        JPanel card = new JPanel();

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255));

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 210, 255), 2, true),
                        BorderFactory.createEmptyBorder(25, 25, 25, 25)
                )
        );

        JLabel lblTitle = new JLabel(title);

        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(60, 60, 60));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValue = new JLabel(value);

        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblValue.setForeground(new Color(0, 102, 255));
        lblValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(lblTitle);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(lblValue);
        card.add(Box.createVerticalGlue());

        card.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {

                card.setBackground(new Color(225, 240, 255));

                card.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(0, 102, 255), 3, true),
                                BorderFactory.createEmptyBorder(25, 25, 25, 25)
                        )
                );
            }

            public void mouseExited(MouseEvent e) {

                card.setBackground(Color.WHITE);

                card.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(180, 210, 255), 2, true),
                                BorderFactory.createEmptyBorder(25, 25, 25, 25)
                        )
                );
            }
        });

        return card;
    }

    JButton createButton(String text) {

        JButton btn = new JButton(text);

        btn.setPreferredSize(new Dimension(170, 45));
        btn.setBackground(new Color(37, 99, 235));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);

        return btn;
    }

    void loadStatistics() {

        try {

            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps1 =
                    con.prepareStatement("SELECT COUNT(*) FROM trains");

            java.sql.ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {

                lblTrains.setText(rs1.getString(1));
            }

            java.sql.PreparedStatement ps2 =
                    con.prepareStatement("SELECT COUNT(*) FROM bookings");

            java.sql.ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {

                lblBookings.setText(rs2.getString(1));
            }

            java.sql.PreparedStatement ps3 =
                    con.prepareStatement("SELECT COUNT(*) FROM bookings WHERE status='Confirmed'");

            java.sql.ResultSet rs3 = ps3.executeQuery();

            if (rs3.next()) {

                lblConfirmed.setText(rs3.getString(1));
            }

            java.sql.PreparedStatement ps4 =
                    con.prepareStatement("SELECT SUM(price) FROM trains");

            java.sql.ResultSet rs4 = ps4.executeQuery();

            if (rs4.next()) {

                int revenue = rs4.getInt(1);

                lblRevenue.setText("₹" + revenue);
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }
}