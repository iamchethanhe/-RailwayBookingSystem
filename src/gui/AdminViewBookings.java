package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminViewBookings extends JFrame {

    JPanel listPanel;

    public AdminViewBookings() {

        setTitle("All Bookings");

        setSize(800, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔵 BACKGROUND
        JPanel main = new JPanel(new BorderLayout()) {

            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0,
                        0,
                        new Color(58, 123, 213),
                        getWidth(),
                        getHeight(),
                        new Color(0, 210, 255)
                );

                g2.setPaint(gp);

                g2.fillRect(
                        0,
                        0,
                        getWidth(),
                        getHeight()
                );
            }
        };

        add(main);

        // 🔹 TITLE
        JLabel title =
                new JLabel(
                        "All Bookings",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        title.setForeground(Color.WHITE);

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        0,
                        15,
                        0
                )
        );

        main.add(title, BorderLayout.NORTH);

        // 📜 LIST PANEL
        listPanel = new JPanel();

        listPanel.setLayout(
                new BoxLayout(
                        listPanel,
                        BoxLayout.Y_AXIS
                )
        );

        listPanel.setBackground(Color.WHITE);

        JScrollPane scroll =
                new JScrollPane(listPanel);

        scroll.setBorder(null);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

        // 🔙 BOTTOM PANEL
        JPanel bottom = new JPanel();

        bottom.setPreferredSize(
                new Dimension(800, 70)
        );

        bottom.setBackground(
                new Color(230, 240, 250)
        );

        JButton btnBack =
                new JButton("Back");

        btnBack.setPreferredSize(
                new Dimension(120, 35)
        );

        btnBack.setBackground(
                new Color(37, 99, 235)
        );

        btnBack.setForeground(Color.WHITE);

        btnBack.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        btnBack.setFocusPainted(false);

        btnBack.setOpaque(true);

        btnBack.setContentAreaFilled(true);

        btnBack.setBorderPainted(false);

        bottom.add(btnBack);

        main.add(bottom, BorderLayout.SOUTH);

        // 🔙 BACK ACTION
        btnBack.addActionListener(
                new ActionListener() {

                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        new AdminDashboard()
                                .setVisible(true);

                        dispose();
                    }
                }
        );

        loadBookings();
    }

    // 🔥 LOAD BOOKINGS
    void loadBookings() {

        try {

            java.sql.Connection con =
                    DBConnection.getConnection();

            java.sql.PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM bookings"
                    );

            java.sql.ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                addBookingCard(
                        rs.getInt("id"),
                        rs.getString("pnr"),
                        rs.getString("username"),
                        rs.getString("train_name"),
                        rs.getString("category"),
                        rs.getString("journey_date"),
                        rs.getString("status")
                );
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    // 🎫 BOOKING CARD
    void addBookingCard(
            int bookingId,
            String pnr,
            String username,
            String train,
            String category,
            String date,
            String status
    ) {

        JPanel card =
                new JPanel(new BorderLayout());

        card.setMaximumSize(
                new Dimension(730, 200)
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 220, 220)
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        // 🔹 LEFT
        JPanel left = new JPanel();

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        left.setOpaque(false);

        JLabel lblTrain =
                new JLabel(train);

        lblTrain.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        JLabel lblPNR =
                new JLabel(
                        "PNR: " + pnr
                );

        JLabel lblUser =
                new JLabel(
                        "User: " + username
                );

        JLabel lblCategory =
                new JLabel(
                        "Category: " + category
                );

        JLabel lblDate =
                new JLabel(
                        "Date: " + date
                );

        JLabel lblStatus =
                new JLabel(
                        "Status: " + status
                );

        // 🎨 STATUS COLORS
        if (status.equals("Confirmed")) {

            lblStatus.setForeground(
                    new Color(34, 197, 94)
            );

        } else if (status.equals("Cancelled")) {

            lblStatus.setForeground(Color.RED);

        } else {

            lblStatus.setForeground(
                    new Color(234, 179, 8)
            );
        }

        left.add(lblTrain);

        left.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        left.add(lblPNR);

        left.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        left.add(lblUser);

        left.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        left.add(lblCategory);

        left.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        left.add(lblDate);

        left.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        left.add(lblStatus);

        // 🔹 RIGHT
        JPanel right = new JPanel();

        right.setLayout(
                new BoxLayout(
                        right,
                        BoxLayout.Y_AXIS
                )
        );

        right.setOpaque(false);

        // ✅ CONFIRM BUTTON
        JButton btnConfirm =
                new JButton("Confirm");

        btnConfirm.setPreferredSize(
                new Dimension(120, 40)
        );

        btnConfirm.setMaximumSize(
                new Dimension(120, 40)
        );

        btnConfirm.setBackground(
                new Color(37, 99, 235)
        );

        btnConfirm.setForeground(Color.WHITE);

        btnConfirm.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        btnConfirm.setFocusPainted(false);

        btnConfirm.setOpaque(true);

        btnConfirm.setContentAreaFilled(true);

        btnConfirm.setBorderPainted(false);

        // ❌ CANCEL BUTTON
        JButton btnCancel =
                new JButton("Cancel");

        btnCancel.setPreferredSize(
                new Dimension(120, 40)
        );

        btnCancel.setMaximumSize(
                new Dimension(120, 40)
        );

        btnCancel.setBackground(
                new Color(220, 38, 38)
        );

        btnCancel.setForeground(Color.WHITE);

        btnCancel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        btnCancel.setFocusPainted(false);

        btnCancel.setOpaque(true);

        btnCancel.setContentAreaFilled(true);

        btnCancel.setBorderPainted(false);

        right.add(btnConfirm);

        right.add(
                Box.createRigidArea(
                        new Dimension(0, 10)
                )
        );

        right.add(btnCancel);

        // ✅ CONFIRM ACTION
        btnConfirm.addActionListener(
                new ActionListener() {

                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        try {

                            java.sql.Connection con =
                                    DBConnection.getConnection();

                            java.sql.PreparedStatement ps =
                                    con.prepareStatement(

                                            "UPDATE bookings SET status=? WHERE id=?"
                                    );

                            ps.setString(
                                    1,
                                    "Confirmed"
                            );

                            ps.setInt(
                                    2,
                                    bookingId
                            );

                            ps.executeUpdate();

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Ticket Confirmed"
                            );

                            dispose();

                            new AdminViewBookings()
                                    .setVisible(true);

                        } catch (Exception ex) {

                            System.out.println(ex);
                        }
                    }
                }
        );

        // ❌ CANCEL ACTION
        btnCancel.addActionListener(
                new ActionListener() {

                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        try {

                            // ❌ ALREADY CANCELLED
                            if (status.equals("Cancelled")) {

                                JOptionPane.showMessageDialog(
                                        null,
                                        "Already Cancelled"
                                );

                                return;
                            }

                            java.sql.Connection con =
                                    DBConnection.getConnection();

                            // ✅ GET TICKETS
                            java.sql.PreparedStatement seatPs =
                                    con.prepareStatement(

                                            "SELECT train_name, tickets FROM bookings WHERE id=?"
                                    );

                            seatPs.setInt(
                                    1,
                                    bookingId
                            );

                            java.sql.ResultSet seatRs =
                                    seatPs.executeQuery();

                            String trainName = "";

                            int bookedTickets = 0;

                            if (seatRs.next()) {

                                trainName =
                                        seatRs.getString(
                                                "train_name"
                                        );

                                bookedTickets =
                                        seatRs.getInt(
                                                "tickets"
                                        );
                            }

                            // ✅ RESTORE SEATS
                            java.sql.PreparedStatement restorePs =
                                    con.prepareStatement(

                                            "UPDATE trains SET available_seats = available_seats + ? WHERE train_name=?"
                                    );

                            restorePs.setInt(
                                    1,
                                    bookedTickets
                            );

                            restorePs.setString(
                                    2,
                                    trainName
                            );

                            restorePs.executeUpdate();

                            // ✅ UPDATE STATUS
                            java.sql.PreparedStatement ps =
                                    con.prepareStatement(

                                            "UPDATE bookings SET status=? WHERE id=?"
                                    );

                            ps.setString(
                                    1,
                                    "Cancelled"
                            );

                            ps.setInt(
                                    2,
                                    bookingId
                            );

                            ps.executeUpdate();

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Ticket Cancelled"
                            );

                            dispose();

                            new AdminViewBookings()
                                    .setVisible(true);

                        } catch (Exception ex) {

                            System.out.println(ex);
                        }
                    }
                }
        );

        card.add(left, BorderLayout.WEST);

        card.add(right, BorderLayout.EAST);

        listPanel.add(card);

        listPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 12)
                )
        );
    }
}