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
        card.setPreferredSize(new Dimension(450, 380));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        main.add(card);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🔹 TITLE
        JLabel title = new JLabel("Add Train", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        gbc.gridwidth = 1;

        // 🔹 TEXT FIELDS
        JTextField txtName = new JTextField(15);
        JTextField txtSource = new JTextField(15);
        JTextField txtDest = new JTextField(15);
        JTextField txtPrice = new JTextField(15);
        JTextField txtTime = new JTextField(15);

        // 👉 Helper method
        addRow(card, gbc, 1, "Train Name", txtName);
        addRow(card, gbc, 2, "Source", txtSource);
        addRow(card, gbc, 3, "Destination", txtDest);
        addRow(card, gbc, 4, "Price", txtPrice);
        addRow(card, gbc, 5, "Train Time", txtTime);

        // 🔵 ADD BUTTON
        JButton btnAdd = new JButton("Add Train");
        styleButton(btnAdd);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        card.add(btnAdd, gbc);

        // 🔙 BACK BUTTON
        JButton btnBack = new JButton("Back");

        gbc.gridy = 7;
        card.add(btnBack, gbc);

        // 🔥 LOGIC
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String name = txtName.getText();
                String source = txtSource.getText();
                String dest = txtDest.getText();
                String priceText = txtPrice.getText();
                String time = txtTime.getText();

                if (name.equals("") || source.equals("") || dest.equals("") ||
                        priceText.equals("") || time.equals("")) {

                    JOptionPane.showMessageDialog(null, "Fill all fields");
                } else {
                    try {
                        int price = Integer.parseInt(priceText);

                        java.sql.Connection con = DBConnection.getConnection();

                        java.sql.PreparedStatement ps = con.prepareStatement(
                                "INSERT INTO trains(train_name, source, destination, price, train_time) VALUES (?, ?, ?, ?, ?)"
                        );

                        ps.setString(1, name);
                        ps.setString(2, source);
                        ps.setString(3, dest);
                        ps.setInt(4, price);
                        ps.setString(5, time);

                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Train Added Successfully");

                        txtName.setText("");
                        txtSource.setText("");
                        txtDest.setText("");
                        txtPrice.setText("");
                        txtTime.setText("");

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminDashboard().setVisible(true);
                dispose();
            }
        });
    }

    // 🔧 Helper for rows
    private void addRow(JPanel panel, GridBagConstraints gbc, int y,
                        String labelText, JTextField field) {

        gbc.gridy = y;

        // Label
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(new JLabel(labelText), gbc);

        // Field
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        field.setPreferredSize(new Dimension(200, 30));
        panel.add(field, gbc);
    }

    // 🔧 Button styling
    private void styleButton(JButton btn) {
        btn.setBackground(new Color(37, 99, 235));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
    }
}