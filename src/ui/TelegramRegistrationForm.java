package ui;

import telegram.TelegramRegistrationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelegramRegistrationForm extends JFrame {

    private static final Color NAVY = new Color(15, 23, 42);
    private static final Color BLUE = new Color(37, 99, 235);
    private static final Color GREEN = new Color(22, 163, 74);
    private static final Color RED = new Color(220, 38, 38);
    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color LIGHT_BG = new Color(248, 250, 252);
    private static final Color BORDER = new Color(226, 232, 240);

    private final TelegramRegistrationService registrationService;
    private final JLabel statusLabel;

    public TelegramRegistrationForm(
            TelegramRegistrationService registrationService) {

        this.registrationService = registrationService;

        // =====================================================
        // WINDOW
        // =====================================================

        setTitle("CampusSlot - Telegram Registration");
        setSize(560, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // =====================================================
        // MAIN BACKGROUND
        // =====================================================

        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(LIGHT_BG);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(NAVY);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        22, 30, 20, 30));

        JLabel titleLabel = new JLabel(
                "🤖  Telegram Staff Registration");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(
                "Connect your staff account to CampusSlot notifications");

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(
                new Color(203, 213, 225));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(7));
        headerPanel.add(subtitleLabel);

        backgroundPanel.add(headerPanel, BorderLayout.NORTH);

        // =====================================================
        // CONTENT
        // =====================================================

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(
                new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(LIGHT_BG);
        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        22, 35, 22, 35));

        // =====================================================
        // BOT DESCRIPTION
        // =====================================================

        JLabel botLabel = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Scan the QR code below to connect with the<br>"
                        + "CampusSlot Telegram bot."
                        + "</div></html>",
                SwingConstants.CENTER);

        botLabel.setFont(
                new Font("Arial", Font.PLAIN, 14));
        botLabel.setForeground(TEXT);
        botLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(botLabel);
        contentPanel.add(Box.createVerticalStrut(15));

        // =====================================================
        // QR CARD
        // =====================================================

        JPanel qrCard = new JPanel(new GridBagLayout());
        qrCard.setBackground(Color.WHITE);
        qrCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER, 1),
                        BorderFactory.createEmptyBorder(
                                10, 10, 10, 10)));

        qrCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrCard.setMaximumSize(new Dimension(250, 250));

        java.io.File qrFile =
                new java.io.File("QR_CODE_PATH");

        ImageIcon qrIcon =
                new ImageIcon(qrFile.getAbsolutePath());

        Image qrImage =
                qrIcon.getImage().getScaledInstance(
                        220,
                        220,
                        Image.SCALE_SMOOTH);

        qrIcon = new ImageIcon(qrImage);

        JLabel qrLabel = new JLabel(qrIcon);

        qrCard.add(qrLabel);

        contentPanel.add(qrCard);
        contentPanel.add(Box.createVerticalStrut(18));

        // =====================================================
        // INSTRUCTIONS CARD
        // =====================================================

        JPanel instructionsCard = new JPanel();
        instructionsCard.setLayout(
                new BoxLayout(
                        instructionsCard,
                        BoxLayout.Y_AXIS));

        instructionsCard.setBackground(Color.WHITE);
        instructionsCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER, 1),
                        BorderFactory.createEmptyBorder(
                                14, 20, 14, 20)));

        instructionsCard.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsCard.setMaximumSize(
                new Dimension(480, 150));

        JLabel instructionsTitle =
                new JLabel("Registration Steps");

        instructionsTitle.setFont(
                new Font("Arial", Font.BOLD, 15));
        instructionsTitle.setForeground(TEXT);
        instructionsTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        instructionsCard.add(instructionsTitle);
        instructionsCard.add(Box.createVerticalStrut(9));

        addStep(
                instructionsCard,
                "1",
                "Scan the Telegram bot QR code");

        addStep(
                instructionsCard,
                "2",
                "Open the bot");

        addStep(
                instructionsCard,
                "3",
                "Press START");

        addStep(
                instructionsCard,
                "4",
                "Enter your Staff Name");

        contentPanel.add(instructionsCard);
        contentPanel.add(Box.createVerticalStrut(15));

        // =====================================================
        // STATUS CARD
        // =====================================================

        JPanel statusPanel = new JPanel(new FlowLayout(
                FlowLayout.CENTER,
                8,
                7));

        statusPanel.setBackground(
                new Color(240, 253, 244));

        statusPanel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(187, 247, 208)));

        statusPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusPanel.setMaximumSize(
                new Dimension(480, 38));

        statusLabel = new JLabel(
                "●  Registration listener is running");

        statusLabel.setFont(
                new Font("Arial", Font.BOLD, 13));
        statusLabel.setForeground(GREEN);

        statusPanel.add(statusLabel);

        contentPanel.add(statusPanel);
        contentPanel.add(Box.createVerticalStrut(15));

        // =====================================================
        // CLOSE BUTTON
        // =====================================================

        JButton closeButton =
                createButton(
                        "Close Registration",
                        RED);

        closeButton.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        closeButton.addActionListener(
                e -> closeRegistration());

        contentPanel.add(closeButton);

        backgroundPanel.add(
                contentPanel,
                BorderLayout.CENTER);

        add(backgroundPanel);

        // =====================================================
        // WINDOW CLOSE X
        // =====================================================

        addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(
                            WindowEvent e) {

                        closeRegistration();
                    }
                });

        // =====================================================
        // START LISTENER WHEN WINDOW OPENS
        // =====================================================

        registrationService.start();

        setVisible(true);
    }

    // =========================================================
    // STEP ROW
    // =========================================================

    private void addStep(
            JPanel parent,
            String number,
            String text) {

        JPanel row = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        8,
                        2));

        row.setBackground(Color.WHITE);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel numberLabel =
                new JLabel(number);

        numberLabel.setHorizontalAlignment(
                SwingConstants.CENTER);

        numberLabel.setFont(
                new Font("Arial", Font.BOLD, 12));
        numberLabel.setForeground(Color.WHITE);

        numberLabel.setOpaque(true);
        numberLabel.setBackground(BLUE);

        numberLabel.setPreferredSize(
                new Dimension(22, 22));

        JLabel textLabel =
                new JLabel(text);

        textLabel.setFont(
                new Font("Arial", Font.PLAIN, 13));
        textLabel.setForeground(TEXT);

        row.add(numberLabel);
        row.add(textLabel);

        parent.add(row);
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color background) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 14));

        button.setForeground(Color.WHITE);
        button.setBackground(background);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        button.setPreferredSize(
                new Dimension(190, 42));

        button.setMaximumSize(
                new Dimension(190, 42));

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                background.darker());
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(background);
                    }
                });

        return button;
    }

    // =========================================================
    // CLOSE REGISTRATION
    // =========================================================

    private void closeRegistration() {

        registrationService.stop();

        statusLabel.setText(
                "●  Registration listener stopped");

        statusLabel.setForeground(RED);

        dispose();
    }
}