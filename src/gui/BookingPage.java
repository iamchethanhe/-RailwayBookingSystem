package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BookingPage extends JFrame {

    public BookingPage(String train, String from, String to, int basePrice, String date) {

        setTitle("Book Ticket");
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

        // ⚪ CARD PANEL
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(420, 370));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        // 🔹 TITLE
        JLabel title = new JLabel("Book Your Tickets", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridy = 0;
        card.add(title, gbc);

        // 🔹 TRAIN DETAILS
        JLabel lblTrain = new JLabel("Train: " + train);
        JLabel lblRoute = new JLabel("Route: " + from + " → " + to);
        JLabel lblDate = new JLabel("Date: " + date);

        gbc.gridy = 1;
        card.add(lblTrain, gbc);

        gbc.gridy = 2;
        card.add(lblRoute, gbc);

        gbc.gridy = 3;
        card.add(lblDate, gbc);

        // 🔹 CATEGORY
        JLabel lblCategory = new JLabel("Category");
        gbc.gridy = 4;
        card.add(lblCategory, gbc);

        JComboBox<String> cbCategory = new JComboBox<>(new String[]{"AC", "Non-AC", "Sleeper"});
        gbc.gridy = 5;
        card.add(cbCategory, gbc);

        // 🔹 TIMING
        JLabel lblTime = new JLabel("Timing");
        gbc.gridy = 6;
        card.add(lblTime, gbc);

        JComboBox<String> cbTime = new JComboBox<>(new String[]{
                "05:00 AM", "08:00 AM", "12:00 PM", "04:00 PM", "09:00 PM"
        });
        gbc.gridy = 7;
        card.add(cbTime, gbc);

        // 🔹 TICKETS
        JLabel lblTickets = new JLabel("Tickets");
        gbc.gridy = 8;
        card.add(lblTickets, gbc);

        JTextField txtTickets = new JTextField();
        gbc.gridy = 9;
        card.add(txtTickets, gbc);

        // 🔹 TOTAL
        JLabel lblTotal = new JLabel("Total Price: ₹0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 10;
        card.add(lblTotal, gbc);

        // 🔵 BOOK BUTTON
        JButton btnBook = new JButton("Book Now");
        btnBook.setBackground(new Color(37, 99, 235));
        btnBook.setForeground(Color.WHITE);
        btnBook.setFocusPainted(false);
        btnBook.setOpaque(true);
        btnBook.setContentAreaFilled(true);
        btnBook.setBorderPainted(false);

        gbc.gridy = 11;
        card.add(btnBook, gbc);

        // 🔥 WRAPPER
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        wrapper.add(card);
        wrapper.add(Box.createRigidArea(new Dimension(0, 10)));

        // 🔙 BACK BUTTON
        JButton btnBack = new JButton("Back");
        btnBack.setMaximumSize(new Dimension(200, 35));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(btnBack);

        main.add(wrapper);

        // 🔥 PRICE UPDATE
        ActionListener updatePrice = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int tickets = Integer.parseInt(txtTickets.getText());
                    String category = cbCategory.getSelectedItem().toString();

                    int extra = 0;
                    if (category.equals("AC")) extra = 500;
                    else if (category.equals("Non-AC")) extra = 200;
                    else extra = 100;

                    int total = (basePrice + extra) * tickets;

                    lblTotal.setText("Total Price: ₹" + total);

                } catch (Exception ex) {
                    lblTotal.setText("Total Price: ₹0");
                }
            }
        };

        cbCategory.addActionListener(updatePrice);

        txtTickets.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                updatePrice.actionPerformed(null);
            }
        });

        // 🔥 BOOK ACTION (FIXED)
        btnBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {

                    if (txtTickets.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Enter tickets");
                        return;
                    }

                    int tickets = Integer.parseInt(txtTickets.getText());

                    String category = cbCategory.getSelectedItem().toString();
                    String time = cbTime.getSelectedItem().toString();

                    int extra = 0;
                    if (category.equals("AC")) extra = 500;
                    else if (category.equals("Non-AC")) extra = 200;
                    else extra = 100;

                    int total = (basePrice + extra) * tickets;

                    String username = Dashboard.user;

                    java.sql.Connection con = DBConnection.getConnection();

                    java.sql.PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO bookings(username, train_name, category, journey_date) VALUES (?, ?, ?, ?)"
                    );

                    ps.setString(1, username);
                    ps.setString(2, train);
                    ps.setString(3, category + " (" + time + ")");
                    ps.setString(4, date);

                    ps.executeUpdate();

                    System.out.println("Inserted for: " + username);

                    JOptionPane.showMessageDialog(null,
                            "Booking Confirmed!\nTotal: ₹" + total);

                    new ViewTicketPage().setVisible(true);
                    dispose();

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        // 🔙 BACK ACTION
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchTrainPage().setVisible(true);
                dispose();
            }
        });
    }
}