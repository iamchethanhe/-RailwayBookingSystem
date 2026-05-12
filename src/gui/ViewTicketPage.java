package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;

public class ViewTicketPage extends JFrame {

    JPanel listPanel;

    public ViewTicketPage() {

        setTitle("My Tickets");
        setSize(800, 500);
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
                "My Tickets",
                SwingConstants.CENTER
        );

        title.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                22
        ));

        title.setForeground(Color.WHITE);

        title.setBorder(BorderFactory.createEmptyBorder(
                15, 0, 15, 0
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

        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        bottom.setPreferredSize(new Dimension(800, 70));

        bottom.setBackground(new Color(230, 240, 250));

        JButton btnBack = new JButton("Back");

        btnBack.setPreferredSize(new Dimension(120, 35));

        btnBack.setBackground(new Color(37, 99, 235));

        btnBack.setForeground(Color.WHITE);

        btnBack.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));

        btnBack.setFocusPainted(false);
        btnBack.setOpaque(true);
        btnBack.setContentAreaFilled(true);
        btnBack.setBorderPainted(false);

        bottom.add(btnBack);

        main.add(bottom, BorderLayout.SOUTH);

        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new Dashboard().setVisible(true);

                dispose();
            }
        });

        loadTickets();
    }

    void loadTickets() {

        try {

            java.sql.Connection con = DBConnection.getConnection();

            java.sql.PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM bookings WHERE username=?"
                    );

            ps.setString(1, Dashboard.user);

            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                addTicketCard(
                        rs.getString("pnr"),
                        rs.getString("train_name"),
                        rs.getString("category"),
                        rs.getString("journey_date"),
                        rs.getString("status"),
                        rs.getString("seat_numbers")
                );
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    void addTicketCard(
            String pnr,
            String train,
            String category,
            String date,
            String status,
            String seatNumbers
    ) {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(730, 250));

        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 220)
                ),
                BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        ));

        JPanel left = new JPanel();

        left.setLayout(new BoxLayout(
                left,
                BoxLayout.Y_AXIS
        ));

        left.setOpaque(false);

        JLabel lblTrain = new JLabel(train);

        lblTrain.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                18
        ));

        JLabel lblPNR = new JLabel("PNR: " + pnr);

        JLabel lblCategory =
                new JLabel("Category: " + category);

        JLabel lblDate = new JLabel("Date: " + date);

        JLabel lblSeats =
                new JLabel("Seats: " + seatNumbers);

        JLabel lblStatus =
                new JLabel("Status: " + status);

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
        left.add(Box.createRigidArea(new Dimension(0, 5)));

        left.add(lblPNR);
        left.add(Box.createRigidArea(new Dimension(0, 5)));

        left.add(lblCategory);
        left.add(Box.createRigidArea(new Dimension(0, 5)));

        left.add(lblDate);
        left.add(Box.createRigidArea(new Dimension(0, 5)));

        left.add(lblSeats);
        left.add(Box.createRigidArea(new Dimension(0, 5)));

        left.add(lblStatus);

        JPanel right = new JPanel();

        right.setLayout(new BoxLayout(
                right,
                BoxLayout.Y_AXIS
        ));

        right.setOpaque(false);

        JButton btnDownload =
                new JButton("Download Ticket");

        btnDownload.setPreferredSize(
                new Dimension(160, 40)
        );

        btnDownload.setMaximumSize(
                new Dimension(160, 40)
        );

        btnDownload.setBackground(
                new Color(37, 99, 235)
        );

        btnDownload.setForeground(Color.WHITE);

        btnDownload.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));

        btnDownload.setFocusPainted(false);
        btnDownload.setOpaque(true);
        btnDownload.setContentAreaFilled(true);
        btnDownload.setBorderPainted(false);

        if (!status.equals("Confirmed")) {

            btnDownload.setEnabled(false);

            btnDownload.setBackground(
                    new Color(180, 180, 180)
            );
        }

        right.add(btnDownload);

        btnDownload.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {

                    String fileName =
                            "ticket_" + pnr + ".txt";

                    FileWriter fw =
                            new FileWriter(fileName);

                    fw.write(
                            "========== RAILWAY TICKET ==========\n\n"
                    );

                    fw.write("PNR: " + pnr + "\n");

                    fw.write(
                            "Passenger: "
                                    + Dashboard.user
                                    + "\n"
                    );

                    fw.write("Train: " + train + "\n");

                    fw.write(
                            "Category: "
                                    + category
                                    + "\n"
                    );

                    fw.write(
                            "Journey Date: "
                                    + date
                                    + "\n"
                    );

                    fw.write(
                            "Seats: "
                                    + seatNumbers
                                    + "\n"
                    );

                    fw.write(
                            "Status: "
                                    + status
                                    + "\n\n"
                    );

                    fw.write(
                            "==================================="
                    );

                    fw.close();

                    JOptionPane.showMessageDialog(
                            null,
                            "Ticket Downloaded\n\nSaved as:\n"
                                    + fileName
                    );

                } catch (Exception ex) {

                    System.out.println(ex);
                }
            }
        });

        card.add(left, BorderLayout.WEST);

        card.add(right, BorderLayout.EAST);

        listPanel.add(card);

        listPanel.add(Box.createRigidArea(
                new Dimension(0, 12)
        ));
    }
}