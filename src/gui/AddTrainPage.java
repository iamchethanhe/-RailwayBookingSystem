package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddTrainPage extends JFrame {

    public AddTrainPage() {

        setTitle("Add Train");

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
                new Dimension(420, 420)
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
                        "Add Train",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        gbc.gridy = 0;

        card.add(title, gbc);

        // 🔹 TRAIN NAME
        JLabel lblName =
                new JLabel("Train Name");

        gbc.gridy = 1;

        card.add(lblName, gbc);

        JTextField txtName =
                new JTextField();

        gbc.gridy = 2;

        card.add(txtName, gbc);

        // 🔹 SOURCE
        JLabel lblSource =
                new JLabel("Source");

        gbc.gridy = 3;

        card.add(lblSource, gbc);

        JTextField txtSource =
                new JTextField();

        gbc.gridy = 4;

        card.add(txtSource, gbc);

        // 🔹 DESTINATION
        JLabel lblDest =
                new JLabel("Destination");

        gbc.gridy = 5;

        card.add(lblDest, gbc);

        JTextField txtDest =
                new JTextField();

        gbc.gridy = 6;

        card.add(txtDest, gbc);

        // 🔹 PRICE
        JLabel lblPrice =
                new JLabel("Price");

        gbc.gridy = 7;

        card.add(lblPrice, gbc);

        JTextField txtPrice =
                new JTextField();

        gbc.gridy = 8;

        card.add(txtPrice, gbc);

        // 🔹 TRAIN TIME
        JLabel lblTime =
                new JLabel("Train Time");

        gbc.gridy = 9;

        card.add(lblTime, gbc);

        JTextField txtTime =
                new JTextField();

        gbc.gridy = 10;

        card.add(txtTime, gbc);

        // 🔹 TOTAL SEATS
        JLabel lblSeats =
                new JLabel("Total Seats");

        gbc.gridy = 11;

        card.add(lblSeats, gbc);

        JTextField txtSeats =
                new JTextField();

        gbc.gridy = 12;

        card.add(txtSeats, gbc);

        // 🔵 ADD BUTTON
        JButton btnAdd =
                new JButton("Add Train");

        btnAdd.setBackground(
                new Color(37, 99, 235)
        );

        btnAdd.setForeground(Color.WHITE);

        btnAdd.setFocusPainted(false);

        btnAdd.setOpaque(true);

        btnAdd.setContentAreaFilled(true);

        btnAdd.setBorderPainted(false);

        gbc.gridy = 13;

        card.add(btnAdd, gbc);

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

        // 🔥 ADD ACTION
        btnAdd.addActionListener(
                new ActionListener() {

                    public void actionPerformed(
                            ActionEvent e
                    ) {

                        try {

                            String name =
                                    txtName.getText();

                            String source =
                                    txtSource.getText();

                            String dest =
                                    txtDest.getText();

                            String priceText =
                                    txtPrice.getText();

                            String time =
                                    txtTime.getText();

                            String seatsText =
                                    txtSeats.getText();

                            if (
                                    name.equals("")
                                            || source.equals("")
                                            || dest.equals("")
                                            || priceText.equals("")
                                            || time.equals("")
                                            || seatsText.equals("")
                            ) {

                                JOptionPane.showMessageDialog(
                                        null,
                                        "Fill all fields"
                                );

                                return;
                            }

                            int price =
                                    Integer.parseInt(
                                            priceText
                                    );

                            int seats =
                                    Integer.parseInt(
                                            seatsText
                                    );

                            java.sql.Connection con =
                                    DBConnection.getConnection();

                            java.sql.PreparedStatement ps =
                                    con.prepareStatement(

                                            "INSERT INTO trains(train_name, source, destination, price, train_time, total_seats, available_seats) VALUES (?, ?, ?, ?, ?, ?, ?)"
                                    );

                            ps.setString(1, name);

                            ps.setString(2, source);

                            ps.setString(3, dest);

                            ps.setInt(4, price);

                            ps.setString(5, time);

                            // ✅ TOTAL SEATS
                            ps.setInt(6, seats);

                            // ✅ AVAILABLE SEATS
                            ps.setInt(7, seats);

                            ps.executeUpdate();

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Train Added Successfully"
                            );

                            txtName.setText("");

                            txtSource.setText("");

                            txtDest.setText("");

                            txtPrice.setText("");

                            txtTime.setText("");

                            txtSeats.setText("");

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

                        new AdminDashboard()
                                .setVisible(true);

                        dispose();
                    }
                }
        );
    }
}