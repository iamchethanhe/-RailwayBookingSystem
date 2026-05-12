package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchTrainPage extends JFrame {

    public SearchTrainPage() {

        setTitle("Search Train");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel() {

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

        main.setLayout(new GridBagLayout());

        add(main);

        JPanel card = new JPanel();

        card.setPreferredSize(new Dimension(360, 300));
        card.setBackground(Color.WHITE);

        card.setLayout(new BoxLayout(
                card,
                BoxLayout.Y_AXIS
        ));

        card.setBorder(BorderFactory.createEmptyBorder(
                30, 40, 30, 40
        ));

        main.add(card);

        JLabel title = new JLabel("Search Trains");

        title.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                22
        ));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel fromLabel = new JLabel(
                "From",
                SwingConstants.CENTER
        );

        fromLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtFrom = new JTextField();

        txtFrom.setMaximumSize(new Dimension(260, 35));

        txtFrom.setBackground(new Color(240, 240, 240));

        txtFrom.setBorder(BorderFactory.createEmptyBorder(
                5, 10, 5, 10
        ));

        txtFrom.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel toLabel = new JLabel(
                "To",
                SwingConstants.CENTER
        );

        toLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtTo = new JTextField();

        txtTo.setMaximumSize(new Dimension(260, 35));

        txtTo.setBackground(new Color(240, 240, 240));

        txtTo.setBorder(BorderFactory.createEmptyBorder(
                5, 10, 5, 10
        ));

        txtTo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnSearch = new JButton("Search");

        styleButton(btnSearch);

        JButton btnBack = new JButton("Back");

        btnBack.setMaximumSize(new Dimension(260, 35));

        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);

        card.add(Box.createRigidArea(
                new Dimension(0, 20)
        ));

        card.add(fromLabel);

        card.add(Box.createRigidArea(
                new Dimension(0, 5)
        ));

        card.add(txtFrom);

        card.add(Box.createRigidArea(
                new Dimension(0, 15)
        ));

        card.add(toLabel);

        card.add(Box.createRigidArea(
                new Dimension(0, 5)
        ));

        card.add(txtTo);

        card.add(Box.createRigidArea(
                new Dimension(0, 25)
        ));

        card.add(btnSearch);

        card.add(Box.createRigidArea(
                new Dimension(0, 10)
        ));

        card.add(btnBack);

        btnSearch.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String from = txtFrom.getText();

                String to = txtTo.getText();

                if (from.isEmpty() || to.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Enter From & To"
                    );

                } else {

                    new ViewTrainPage(
                            from,
                            to,
                            "Today"
                    ).setVisible(true);

                    dispose();
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new Dashboard().setVisible(true);

                dispose();
            }
        });
    }

    private void styleButton(JButton btn) {

        btn.setMaximumSize(new Dimension(260, 40));

        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setBackground(new Color(37, 99, 235));

        btn.setForeground(Color.WHITE);

        btn.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));

        btn.setFocusPainted(false);

        btn.setOpaque(true);

        btn.setContentAreaFilled(true);

        btn.setBorderPainted(false);
    }
}