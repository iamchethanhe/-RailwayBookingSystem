package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RemoveTrainPage extends JFrame {

    JPanel listPanel;

    public RemoveTrainPage() {

        setTitle("Remove Trains");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout()) {

            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(58, 123, 213),
                        getWidth(), getHeight(),
                        new Color(0, 210, 255)
                );

                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        add(main);

        JLabel title = new JLabel(
                "Remove Trains",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        title.setBorder(BorderFactory.createEmptyBorder(
                20, 0, 20, 0
        ));

        main.add(title, BorderLayout.NORTH);

        listPanel = new JPanel();

        listPanel.setLayout(new BoxLayout(
                listPanel,
                BoxLayout.Y_AXIS
        ));

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

        btnBack.setPreferredSize(new Dimension(120, 35));

        bottom.add(btnBack);

        main.add(bottom, BorderLayout.SOUTH);

        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new AdminDashboard().setVisible(true);
                dispose();
            }
        });

        loadTrains();
    }

    void loadTrains() {

        try {

            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM trains");

            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                addTrainCard(
                        rs.getInt("id"),
                        rs.getString("train_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("price")
                );
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    void addTrainCard(
            int id,
            String train,
            String from,
            String to,
            int price
    ) {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(760, 110));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 220, 255),
                        2,
                        true
                ),
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        ));

        JPanel left = new JPanel();

        left.setOpaque(false);

        left.setLayout(new BoxLayout(
                left,
                BoxLayout.Y_AXIS
        ));

        JLabel lblTrain = new JLabel(train);

        lblTrain.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                18
        ));

        JLabel lblRoute = new JLabel(from + " → " + to);

        JLabel lblPrice = new JLabel("Price: ₹" + price);

        left.add(lblTrain);
        left.add(Box.createRigidArea(new Dimension(0, 5)));
        left.add(lblRoute);
        left.add(Box.createRigidArea(new Dimension(0, 5)));
        left.add(lblPrice);

        JButton btnDelete = new JButton("Delete");

        btnDelete.setPreferredSize(new Dimension(120, 40));

        btnDelete.setBackground(new Color(239, 68, 68));
        btnDelete.setForeground(Color.WHITE);

        btnDelete.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));

        btnDelete.setFocusPainted(false);
        btnDelete.setOpaque(true);
        btnDelete.setContentAreaFilled(true);
        btnDelete.setBorderPainted(false);

        card.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {

                card.setBackground(new Color(225, 240, 255));
            }

            public void mouseExited(MouseEvent e) {

                card.setBackground(Color.WHITE);
            }
        });

        btnDelete.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Delete this train?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {

                    try {

                        java.sql.Connection con =
                                DBConnection.getConnection();

                        java.sql.PreparedStatement ps =
                                con.prepareStatement(
                                        "DELETE FROM trains WHERE id=?"
                                );

                        ps.setInt(1, id);

                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                "Train Removed Successfully"
                        );

                        dispose();

                        new RemoveTrainPage().setVisible(true);

                    } catch (Exception ex) {

                        System.out.println(ex);
                    }
                }
            }
        });

        card.add(left, BorderLayout.WEST);
        card.add(btnDelete, BorderLayout.EAST);

        listPanel.add(card);
        listPanel.add(Box.createRigidArea(new Dimension(0, 12)));
    }
}