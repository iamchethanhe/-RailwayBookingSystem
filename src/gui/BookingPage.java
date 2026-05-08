package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BookingPage extends JFrame {

    JCheckBox seat1;
    JCheckBox seat2;
    JCheckBox seat3;
    JCheckBox seat4;
    JCheckBox seat5;
    JCheckBox seat6;
    JCheckBox seat7;
    JCheckBox seat8;

    public BookingPage(String train, String from, String to, int basePrice, String date) {

        setTitle("Book Ticket");

        setSize(800, 650);

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

        // ⚪ CARD
        JPanel card = new JPanel(new GridBagLayout());

        card.setPreferredSize(
                new Dimension(450, 540)
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
                new Insets(6, 0, 6, 0);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;

        gbc.weightx = 1;

        // 🔹 TITLE
        JLabel title =
                new JLabel(
                        "Book Your Tickets",
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

        // 🔹 TRAIN DETAILS
        JLabel lblTrain =
                new JLabel(
                        "Train: " + train
                );

        JLabel lblRoute =
                new JLabel(
                        "Route: " + from + " → " + to
                );

        JLabel lblDate =
                new JLabel(
                        "Date: " + date
                );

        JLabel lblSeats =
                new JLabel(
                        "Available Seats: Loading..."
                );

        gbc.gridy = 1;
        card.add(lblTrain, gbc);

        gbc.gridy = 2;
        card.add(lblRoute, gbc);

        gbc.gridy = 3;
        card.add(lblDate, gbc);

        gbc.gridy = 4;
        card.add(lblSeats, gbc);

        // 🔹 CATEGORY
        JLabel lblCategory =
                new JLabel("Category");

        gbc.gridy = 5;

        card.add(lblCategory, gbc);

        JComboBox<String> cbCategory =
                new JComboBox<>(
                        new String[]{
                                "AC",
                                "Non-AC",
                                "Sleeper"
                        }
                );

        gbc.gridy = 6;

        card.add(cbCategory, gbc);

        // 🔹 TIMING
        JLabel lblTime =
                new JLabel("Timing");

        gbc.gridy = 7;

        card.add(lblTime, gbc);

        JComboBox<String> cbTime =
                new JComboBox<>(
                        new String[]{
                                "05:00 AM",
                                "08:00 AM",
                                "12:00 PM",
                                "04:00 PM",
                                "09:00 PM"
                        }
                );

        gbc.gridy = 8;

        card.add(cbTime, gbc);

        // 🔹 TICKETS
        JLabel lblTickets =
                new JLabel("Tickets");

        gbc.gridy = 9;

        card.add(lblTickets, gbc);

        JTextField txtTickets =
                new JTextField();

        gbc.gridy = 10;

        card.add(txtTickets, gbc);

        // 🔹 SELECT SEATS
        JLabel lblSelectSeats =
                new JLabel("Select Seats");

        gbc.gridy = 11;

        card.add(lblSelectSeats, gbc);

        JPanel seatPanel =
                new JPanel(
                        new GridLayout(2, 4, 10, 10)
                );

        seatPanel.setBackground(Color.WHITE);

        seat1 = new JCheckBox("A1");
        seat2 = new JCheckBox("A2");
        seat3 = new JCheckBox("A3");
        seat4 = new JCheckBox("A4");

        seat5 = new JCheckBox("B1");
        seat6 = new JCheckBox("B2");
        seat7 = new JCheckBox("B3");
        seat8 = new JCheckBox("B4");

        seatPanel.add(seat1);
        seatPanel.add(seat2);
        seatPanel.add(seat3);
        seatPanel.add(seat4);

        seatPanel.add(seat5);
        seatPanel.add(seat6);
        seatPanel.add(seat7);
        seatPanel.add(seat8);

        gbc.gridy = 12;

        card.add(seatPanel, gbc);

        // 🔹 TOTAL
        JLabel lblTotal =
                new JLabel("Total Price: ₹0");

        lblTotal.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridy = 13;

        card.add(lblTotal, gbc);

        // 🔵 PAYMENT BUTTON
        JButton btnBook =
                new JButton("Proceed To Payment");

        btnBook.setBackground(
                new Color(37, 99, 235)
        );

        btnBook.setForeground(Color.WHITE);

        btnBook.setFocusPainted(false);

        btnBook.setOpaque(true);

        btnBook.setContentAreaFilled(true);

        btnBook.setBorderPainted(false);

        gbc.gridy = 14;

        card.add(btnBook, gbc);

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

        // ✅ LOAD AVAILABLE SEATS
        try {

            java.sql.Connection con =
                    DBConnection.getConnection();

            java.sql.PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT available_seats FROM trains WHERE train_name=?"
                    );

            ps.setString(1, train);

            java.sql.ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                lblSeats.setText(
                        "Available Seats: "
                                + rs.getInt("available_seats")
                );
            }

        } catch (Exception ex) {

            System.out.println(ex);
        }

        // 🔥 PRICE UPDATE
        ActionListener updatePrice =
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        try {

                            int tickets =
                                    Integer.parseInt(
                                            txtTickets.getText()
                                    );

                            String category =
                                    cbCategory
                                            .getSelectedItem()
                                            .toString();

                            int extra = 0;

                            if (category.equals("AC"))
                                extra = 500;

                            else if (category.equals("Non-AC"))
                                extra = 200;

                            else
                                extra = 100;

                            int total =
                                    (basePrice + extra)
                                            * tickets;

                            lblTotal.setText(
                                    "Total Price: ₹" + total
                            );

                        } catch (Exception ex) {

                            lblTotal.setText(
                                    "Total Price: ₹0"
                            );
                        }
                    }
                };

        cbCategory.addActionListener(updatePrice);

        txtTickets.addKeyListener(
                new KeyAdapter() {

                    public void keyReleased(KeyEvent e) {

                        updatePrice.actionPerformed(null);
                    }
                }
        );

        // 🔥 PAYMENT PAGE
        btnBook.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        try {

                            if (txtTickets.getText().equals("")) {

                                JOptionPane.showMessageDialog(
                                        null,
                                        "Enter tickets"
                                );

                                return;
                            }

                            int tickets =
                                    Integer.parseInt(
                                            txtTickets.getText()
                                    );

                            // ✅ COUNT SELECTED SEATS
                            int selectedSeats = 0;

                            String seatNumbers = "";

                            JCheckBox seats[] = {
                                    seat1,
                                    seat2,
                                    seat3,
                                    seat4,
                                    seat5,
                                    seat6,
                                    seat7,
                                    seat8
                            };

                            for (int i = 0; i < seats.length; i++) {

                                if (seats[i].isSelected()) {

                                    selectedSeats++;

                                    seatNumbers +=
                                            seats[i].getText() + ",";
                                }
                            }

                            // ❌ VALIDATION
                            if (selectedSeats != tickets) {

                                JOptionPane.showMessageDialog(
                                        null,
                                        "Select exactly "
                                                + tickets
                                                + " seats"
                                );

                                return;
                            }

                            String category =
                                    cbCategory
                                            .getSelectedItem()
                                            .toString();

                            String time =
                                    cbTime
                                            .getSelectedItem()
                                            .toString();

                            int extra = 0;

                            if (category.equals("AC"))
                                extra = 500;

                            else if (category.equals("Non-AC"))
                                extra = 200;

                            else
                                extra = 100;

                            int total =
                                    (basePrice + extra)
                                            * tickets;

                            // ✅ OPEN PAYMENT PAGE
                            new PaymentPage(
                                    train,
                                    from,
                                    to,
                                    basePrice,
                                    date,
                                    category,
                                    time,
                                    tickets,
                                    total,
                                    seatNumbers
                            ).setVisible(true);

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

                    public void actionPerformed(ActionEvent e) {

                        new SearchTrainPage()
                                .setVisible(true);

                        dispose();
                    }
                }
        );
    }
}