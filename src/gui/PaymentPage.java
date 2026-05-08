package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaymentPage extends JFrame {

    public PaymentPage(
            String train,
            String from,
            String to,
            int basePrice,
            String date,
            String category,
            String time,
            int tickets,
            int total,
            String seatNumbers
    ) {

        setTitle("Payment");

        setSize(800, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔵 BACKGROUND
        JPanel main = new JPanel(new GridBagLayout()) {

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

        // ⚪ CARD PANEL
        JPanel card = new JPanel(new GridBagLayout());

        card.setPreferredSize(
                new Dimension(420, 440)
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 0, 8, 0);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;

        gbc.weightx = 1;

        // 🔹 TITLE
        JLabel title =
                new JLabel(
                        "Payment",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        gbc.gridy = 0;

        card.add(title, gbc);

        // 🔹 TRAIN
        JLabel lblTrain =
                new JLabel(
                        "Train: " + train
                );

        gbc.gridy = 1;

        card.add(lblTrain, gbc);

        // 🔹 CATEGORY
        JLabel lblCategory =
                new JLabel(
                        "Category: " + category
                );

        gbc.gridy = 2;

        card.add(lblCategory, gbc);

        // 🔹 TICKETS
        JLabel lblTickets =
                new JLabel(
                        "Tickets: " + tickets
                );

        gbc.gridy = 3;

        card.add(lblTickets, gbc);

        // 🔹 SEATS
        JLabel lblSeats =
                new JLabel(
                        "Seats: " + seatNumbers
                );

        gbc.gridy = 4;

        card.add(lblSeats, gbc);

        // 🔹 TOTAL
        JLabel lblTotal =
                new JLabel(
                        "Total Amount: ₹" + total
                );

        lblTotal.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        gbc.gridy = 5;

        card.add(lblTotal, gbc);

        // 🔹 PAYMENT METHOD
        JLabel lblMethod =
                new JLabel("Payment Method");

        gbc.gridy = 6;

        card.add(lblMethod, gbc);

        JComboBox<String> cbMethod =
                new JComboBox<>(
                        new String[]{
                                "UPI",
                                "Credit Card",
                                "Debit Card",
                                "Cash"
                        }
                );

        gbc.gridy = 7;

        card.add(cbMethod, gbc);

        // 🔹 PAYMENT DETAILS
        JLabel lblDetails =
                new JLabel("Payment Details");

        gbc.gridy = 8;

        card.add(lblDetails, gbc);

        JTextField txtDetails =
                new JTextField();

        gbc.gridy = 9;

        card.add(txtDetails, gbc);

        // 🔵 PAY BUTTON
        JButton btnPay =
                new JButton("Pay Now");

        btnPay.setBackground(
                new Color(37, 99, 235)
        );

        btnPay.setForeground(Color.WHITE);

        btnPay.setFocusPainted(false);

        btnPay.setOpaque(true);

        btnPay.setContentAreaFilled(true);

        btnPay.setBorderPainted(false);

        gbc.gridy = 10;

        card.add(btnPay, gbc);

        // 🔥 WRAPPER
        JPanel wrapper = new JPanel();

        wrapper.setLayout(
                new BoxLayout(
                        wrapper,
                        BoxLayout.Y_AXIS
                )
        );

        wrapper.setOpaque(false);

        wrapper.add(card);

        wrapper.add(
                Box.createRigidArea(
                        new Dimension(0, 10)
                )
        );

        // 🔙 BACK BUTTON
        JButton btnBack =
                new JButton("Back");

        btnBack.setMaximumSize(
                new Dimension(200, 35)
        );

        btnBack.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        wrapper.add(btnBack);

        main.add(wrapper);

        // 🔥 PAY ACTION
        btnPay.addActionListener(
                new ActionListener() {

                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        try {

                            if (txtDetails.getText().equals("")) {

                                JOptionPane.showMessageDialog(
                                        null,
                                        "Enter payment details"
                                );

                                return;
                            }

                            String username =
                                    Dashboard.user;

                            String pnr =
                                    "PNR" +
                                    (int)(Math.random() * 1000000000);

                            java.sql.Connection con =
                                    DBConnection.getConnection();

                            // ✅ CHECK SEATS
                            java.sql.PreparedStatement seatPs =
                                    con.prepareStatement(

                                            "SELECT available_seats FROM trains WHERE train_name=?"
                                    );

                            seatPs.setString(1, train);

                            java.sql.ResultSet seatRs =
                                    seatPs.executeQuery();

                            int availableSeats = 0;

                            if (seatRs.next()) {

                                availableSeats =
                                        seatRs.getInt(
                                                "available_seats"
                                        );
                            }

                            // ❌ NOT ENOUGH SEATS
                            if (tickets > availableSeats) {

                                JOptionPane.showMessageDialog(
                                        null,
                                        "Only "
                                                + availableSeats
                                                + " seats available"
                                );

                                return;
                            }

                            // ✅ INSERT BOOKING
                            java.sql.PreparedStatement ps =
                                    con.prepareStatement(

                                            "INSERT INTO bookings(username, train_name, category, journey_date, status, pnr, tickets, seat_numbers) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                                    );

                            ps.setString(1, username);

                            ps.setString(2, train);

                            ps.setString(
                                    3,
                                    category + " (" + time + ")"
                            );

                            ps.setString(4, date);

                            ps.setString(5, "Pending");

                            ps.setString(6, pnr);

                            ps.setInt(7, tickets);

                            // ✅ SAVE SEATS
                            ps.setString(8, seatNumbers);

                            ps.executeUpdate();

                            // ✅ UPDATE AVAILABLE SEATS
                            java.sql.PreparedStatement updateSeatPs =
                                    con.prepareStatement(

                                            "UPDATE trains SET available_seats = available_seats - ? WHERE train_name=?"
                                    );

                            updateSeatPs.setInt(
                                    1,
                                    tickets
                            );

                            updateSeatPs.setString(
                                    2,
                                    train
                            );

                            updateSeatPs.executeUpdate();

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Payment Successful!\n\nPNR: "
                                            + pnr
                            );

                            new ViewTicketPage()
                                    .setVisible(true);

                            dispose();

                        } catch (Exception ex) {

                            System.out.println(ex);
                        }
                    }
                }
        );

        // 🔙 BACK ACTION
        btnBack.addActionListener(
                new ActionListener() {

                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        new BookingPage(
                                train,
                                from,
                                to,
                                basePrice,
                                date
                        ).setVisible(true);

                        dispose();
                    }
                }
        );
    }
}