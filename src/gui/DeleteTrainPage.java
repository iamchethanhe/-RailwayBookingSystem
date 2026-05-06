package gui;

import javax.swing.*;
import java.awt.*;

public class DeleteTrainPage extends JFrame {

    JPanel listPanel;

    public DeleteTrainPage() {

        setTitle("Delete Train");
        setSize(800, 500);
        setLocationRelativeTo(null);

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

        JLabel title = new JLabel("Delete Trains", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        main.add(title, BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        main.add(scroll, BorderLayout.CENTER);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(37, 99, 235));
        btnBack.setForeground(Color.WHITE);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(btnBack);

        main.add(bottom, BorderLayout.SOUTH);

        btnBack.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });

        loadTrains();
    }

    void loadTrains() {

        try {
            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps = con.prepareStatement("SELECT * FROM trains");
            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String name = rs.getString("train_name");
                String source = rs.getString("source");
                String dest = rs.getString("destination");

                addCard(name, source, dest);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void addCard(String name, String source, String dest) {

        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(700, 100));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lbl = new JLabel("<html><b>" + name + "</b><br>" + source + " → " + dest + "</html>");

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);

        btnDelete.addActionListener(e -> {
            deleteTrain(name);
        });

        card.add(lbl, BorderLayout.WEST);
        card.add(btnDelete, BorderLayout.EAST);

        listPanel.add(card);
        listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    void deleteTrain(String name) {

        try {
            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM trains WHERE train_name=?"
            );

            ps.setString(1, name);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Train Deleted");

            new DeleteTrainPage().setVisible(true);
            dispose();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}