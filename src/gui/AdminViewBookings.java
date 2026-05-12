package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminViewBookings extends JFrame {

    JPanel listPanel;

    public AdminViewBookings() {

        setTitle("All Bookings");
        setSize(900, 600);
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

        JLabel title = new JLabel("All Bookings", SwingConstants.CENTER);

        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        main.add(title, BorderLayout.NORTH);

        listPanel = new JPanel();

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(listPanel);

        scroll.setBorder(null);

        main.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        bottom.setBackground(new Color(230, 240, 250));

        JButton btnBack = new JButton("Back");

        btnBack.setBackground(new Color(37, 99, 235));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setOpaque(true);
        btnBack.setContentAreaFilled(true);
        btnBack.setBorderPainted(false);
        btnBack.setPreferredSize(new Dimension(140, 40));

        bottom.add(btnBack);

        main.add(bottom, BorderLayout.SOUTH);

        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new AdminDashboard().setVisible(true);

                dispose();
            }
        });

        loadBookings();
    }

    void loadBookings() {

        try {

            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM bookings WHERE status='Pending'");

            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                addBookingCard(
                        rs.getString("pnr"),
                        rs.getString("train_name"),
                        rs.getString("username"),
                        rs.getString("category"),
                        rs.getString("journey_date"),
                        rs.getString("status")
                );
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    void addBookingCard(
            String pnr,
            String train,
            String user,
            String category,
            String date,
            String status
    ) {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(820, 180));
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(210, 220, 255), 2, true),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                )
        );

        JPanel left = new JPanel();

        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel lblTrain = new JLabel(train);

        lblTrain.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel lblPNR = new JLabel("PNR: " + pnr);
        JLabel lblUser = new JLabel("User: " + user);
        JLabel lblCategory = new JLabel("Category: " + category);
        JLabel lblDate = new JLabel("Date: " + date);
        JLabel lblStatus = new JLabel("Status: " + status);

        lblStatus.setForeground(new Color(234, 179, 8));
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 16));

        left.add(lblTrain);
        left.add(Box.createRigidArea(new Dimension(0, 5)));
        left.add(lblPNR);
        left.add(Box.createRigidArea(new Dimension(0, 5)));
        left.add(lblUser);
        left.add(Box.createRigidArea(new Dimension(0, 5)));
        left.add(lblCategory);
        left.add(Box.createRigidArea(new Dimension(0, 5)));
        left.add(lblDate);
        left.add(Box.createRigidArea(new Dimension(0, 5)));
        left.add(lblStatus);

        JPanel right = new JPanel();

        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JButton btnConfirm = new JButton("Confirm");

        btnConfirm.setBackground(new Color(37, 99, 235));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFocusPainted(false);
        btnConfirm.setOpaque(true);
        btnConfirm.setContentAreaFilled(true);
        btnConfirm.setBorderPainted(false);
        btnConfirm.setPreferredSize(new Dimension(140, 40));
        btnConfirm.setMaximumSize(new Dimension(140, 40));

        JButton btnCancel = new JButton("Cancel");

        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setOpaque(true);
        btnCancel.setContentAreaFilled(true);
        btnCancel.setBorderPainted(false);
        btnCancel.setPreferredSize(new Dimension(140, 40));
        btnCancel.setMaximumSize(new Dimension(140, 40));

        right.add(btnConfirm);
        right.add(Box.createRigidArea(new Dimension(0, 18)));
        right.add(btnCancel);

        btnConfirm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    java.sql.Connection con = DBConnection.getConnection();

                    java.sql.PreparedStatement ps =
                            con.prepareStatement("UPDATE bookings SET status='Confirmed' WHERE pnr=?");

                    ps.setString(1, pnr);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Ticket Confirmed");

                    listPanel.remove(card);

                    listPanel.revalidate();

                    listPanel.repaint();

                } catch (Exception ex) {

                    System.out.println(ex);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    java.sql.Connection con = DBConnection.getConnection();

                    java.sql.PreparedStatement ps =
                            con.prepareStatement("UPDATE bookings SET status='Cancelled' WHERE pnr=?");

                    ps.setString(1, pnr);

                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Ticket Cancelled");

                    listPanel.remove(card);

                    listPanel.revalidate();

                    listPanel.repaint();

                } catch (Exception ex) {

                    System.out.println(ex);
                }
            }
        });

        card.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {

                card.setBackground(new Color(225, 240, 255));
            }

            public void mouseExited(MouseEvent e) {

                card.setBackground(Color.WHITE);
            }
        });

        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);

        listPanel.add(card);
        listPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    }
}