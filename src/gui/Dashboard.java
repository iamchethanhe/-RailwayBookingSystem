package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {

    public static String user;

    public Dashboard() {

        setTitle("Dashboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel() {

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

        main.setLayout(new GridBagLayout());

        add(main);

        JPanel card = new JPanel();

        card.setPreferredSize(new Dimension(360, 300));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        main.add(card);

        JLabel title = new JLabel("Welcome, " + user);

        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension btnSize = new Dimension(260, 40);

        JButton btnSearch = new JButton("Search Trains");
        JButton btnTickets = new JButton("My Tickets");
        JButton btnLogout = new JButton("Logout");

        styleButton(btnSearch, btnSize);
        styleButton(btnTickets, btnSize);
        styleButton(btnLogout, btnSize);

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        card.add(btnSearch);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(btnTickets);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(btnLogout);

        btnSearch.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new SearchTrainPage().setVisible(true);

                dispose();
            }
        });

        btnTickets.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new ViewTicketPage().setVisible(true);

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

    private void styleButton(JButton btn, Dimension size) {

        btn.setMaximumSize(size);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setBackground(new Color(37, 99, 235));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
    }
}