package ui;

import telegram.TelegramRegistrationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dashboard extends JFrame {

        private final TelegramRegistrationService telegramRegistrationService = new TelegramRegistrationService();

        // =========================================================
        // COLORS
        // =========================================================

        private static final Color BACKGROUND = new Color(242, 246, 250);

        private static final Color HEADER = new Color(20, 38, 63);

        private static final Color CARD = Color.WHITE;

        private static final Color TEXT = new Color(30, 42, 56);

        private static final Color SUBTEXT = new Color(105, 118, 132);

        public Dashboard() {

                setTitle(
                                "CampusSlot - Smartboard Booking System");

                setSize(
                                900,
                                600);

                setDefaultCloseOperation(
                                JFrame.EXIT_ON_CLOSE);

                setLocationRelativeTo(null);

                setLayout(
                                new BorderLayout());

                getContentPane().setBackground(
                                BACKGROUND);

                // =====================================================
                // HEADER
                // =====================================================

                JPanel headerPanel = new JPanel(
                                new BorderLayout());

                headerPanel.setBackground(
                                HEADER);

                headerPanel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                25,
                                                40,
                                                25,
                                                40));

                // LEFT SIDE - TITLE

                JPanel titlePanel = new JPanel();

                titlePanel.setLayout(
                                new BoxLayout(
                                                titlePanel,
                                                BoxLayout.Y_AXIS));

                titlePanel.setOpaque(false);

                JLabel titleLabel = new JLabel(
                                "CAMPUSSLOT");

                titleLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                32));

                titleLabel.setForeground(
                                Color.WHITE);

                JLabel subtitleLabel = new JLabel(
                                "College Smartboard Booking System");

                subtitleLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                14));

                subtitleLabel.setForeground(
                                new Color(
                                                190,
                                                210,
                                                225));

                subtitleLabel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                5,
                                                2,
                                                0,
                                                0));

                titlePanel.add(
                                titleLabel);

                titlePanel.add(
                                subtitleLabel);

                headerPanel.add(
                                titlePanel,
                                BorderLayout.WEST);

                // RIGHT SIDE

                JLabel systemLabel = new JLabel(
                                "SMARTBOARD MANAGEMENT");

                systemLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                12));

                systemLabel.setForeground(
                                new Color(
                                                120,
                                                220,
                                                230));

                headerPanel.add(
                                systemLabel,
                                BorderLayout.EAST);

                add(
                                headerPanel,
                                BorderLayout.NORTH);

                // =====================================================
                // CENTER CONTENT
                // =====================================================

                JPanel mainPanel = new JPanel(
                                new BorderLayout());

                mainPanel.setBackground(
                                BACKGROUND);

                mainPanel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                30,
                                                45,
                                                25,
                                                45));

                // WELCOME TEXT

                JPanel welcomePanel = new JPanel();

                welcomePanel.setLayout(
                                new BoxLayout(
                                                welcomePanel,
                                                BoxLayout.Y_AXIS));

                welcomePanel.setOpaque(false);

                JLabel welcomeLabel = new JLabel(
                                "Welcome to CampusSlot");

                welcomeLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                24));

                welcomeLabel.setForeground(
                                TEXT);

                JLabel instructionLabel = new JLabel(
                                "Select an option below to manage your smartboard bookings.");

                instructionLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                14));

                instructionLabel.setForeground(
                                SUBTEXT);

                instructionLabel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                5,
                                                0,
                                                20,
                                                0));

                welcomePanel.add(
                                welcomeLabel);

                welcomePanel.add(
                                instructionLabel);

                mainPanel.add(
                                welcomePanel,
                                BorderLayout.NORTH);

                // =====================================================
                // MENU CARDS
                // =====================================================

                JPanel cardPanel = new JPanel(
                                new GridLayout(
                                                2,
                                                2,
                                                20,
                                                20));

                cardPanel.setOpaque(false);

                // =====================================================
                // CARD 1 - MANAGE SMARTBOARDS
                // =====================================================

                JPanel manageCard = createCard(
                                "🖥",
                                "Manage Smartboards",
                                "Add or remove smartboards",
                                new Color(0, 130, 170));

                manageCard.addMouseListener(
                                new MouseAdapter() {

                                        @Override
                                        public void mouseClicked(
                                                        MouseEvent e) {

                                                new ResourceForm();
                                        }
                                });

                // =====================================================
                // CARD 2 - BOOK SMARTBOARD
                // =====================================================

                JPanel bookingCard = createCard(
                                "📅",
                                "Book a Smartboard",
                                "Reserve a smartboard for a period",
                                new Color(35, 150, 100));

                bookingCard.addMouseListener(
                                new MouseAdapter() {

                                        @Override
                                        public void mouseClicked(
                                                        MouseEvent e) {

                                                new BookingForm();
                                        }
                                });

                // =====================================================
                // CARD 3 - ALL BOOKINGS
                // =====================================================

                JPanel bookingsCard = createCard(
                                "📋",
                                "All Bookings",
                                "View and manage existing bookings",
                                new Color(120, 85, 180));

                bookingsCard.addMouseListener(
                                new MouseAdapter() {

                                        @Override
                                        public void mouseClicked(
                                                        MouseEvent e) {

                                                new AllBookings();
                                        }
                                });

                // =====================================================
                // CARD 4 - TELEGRAM REGISTRATION
                // =====================================================

                JPanel telegramCard = createCard(
                                "🤖",
                                "Register Staff",
                                "Connect staff with Telegram notifications",
                                new Color(35, 120, 200));

                telegramCard.addMouseListener(
                                new MouseAdapter() {

                                        @Override
                                        public void mouseClicked(
                                                        MouseEvent e) {

                                                new TelegramRegistrationForm(
                                                                telegramRegistrationService);
                                        }
                                });

                cardPanel.add(
                                manageCard);

                cardPanel.add(
                                bookingCard);

                cardPanel.add(
                                bookingsCard);

                cardPanel.add(
                                telegramCard);

                mainPanel.add(
                                cardPanel,
                                BorderLayout.CENTER);

                add(
                                mainPanel,
                                BorderLayout.CENTER);

                // =====================================================
                // FOOTER
                // =====================================================

                JPanel footerPanel = new JPanel(
                                new BorderLayout());

                footerPanel.setBackground(
                                HEADER);

                footerPanel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                12,
                                                30,
                                                12,
                                                30));

                JLabel footerLabel = new JLabel(
                                "CAMPUSSLOT  •  Smartboard Booking & Conflict Detection");

                footerLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                12));

                footerLabel.setForeground(
                                new Color(
                                                190,
                                                205,
                                                220));

                JLabel versionLabel = new JLabel(
                                "College Edition");

                versionLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                11));

                versionLabel.setForeground(
                                new Color(
                                                120,
                                                220,
                                                230));

                footerPanel.add(
                                footerLabel,
                                BorderLayout.WEST);

                footerPanel.add(
                                versionLabel,
                                BorderLayout.EAST);

                add(
                                footerPanel,
                                BorderLayout.SOUTH);

                setVisible(true);
        }

        // =========================================================
        // CREATE MENU CARD
        // =========================================================

        private JPanel createCard(
                        String icon,
                        String title,
                        String description,
                        Color accent) {

                JPanel card = new JPanel(
                                new BorderLayout());

                card.setBackground(
                                CARD);

                card.setBorder(
                                BorderFactory.createCompoundBorder(
                                                BorderFactory.createLineBorder(
                                                                new Color(
                                                                                220,
                                                                                227,
                                                                                234),
                                                                1),
                                                BorderFactory.createEmptyBorder(
                                                                20,
                                                                22,
                                                                20,
                                                                22)));

                // =====================================================
                // ICON
                // =====================================================

                JLabel iconLabel = new JLabel(
                                icon,
                                SwingConstants.CENTER);

                iconLabel.setFont(
                                new Font(
                                                "Segoe UI Emoji",
                                                Font.PLAIN,
                                                34));

                iconLabel.setPreferredSize(
                                new Dimension(
                                                65,
                                                65));

                JPanel iconBox = new JPanel(
                                new BorderLayout());

                iconBox.setBackground(
                                accent);

                iconBox.add(
                                iconLabel,
                                BorderLayout.CENTER);

                card.add(
                                iconBox,
                                BorderLayout.WEST);

                // =====================================================
                // TEXT
                // =====================================================

                JPanel textPanel = new JPanel();

                textPanel.setLayout(
                                new BoxLayout(
                                                textPanel,
                                                BoxLayout.Y_AXIS));

                textPanel.setOpaque(false);

                textPanel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                3,
                                                18,
                                                3,
                                                5));

                JLabel titleLabel = new JLabel(
                                title);

                titleLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.BOLD,
                                                18));

                titleLabel.setForeground(
                                TEXT);

                JLabel descriptionLabel = new JLabel(
                                "<html>"
                                                + description
                                                + "</html>");

                descriptionLabel.setFont(
                                new Font(
                                                "Arial",
                                                Font.PLAIN,
                                                13));

                descriptionLabel.setForeground(
                                SUBTEXT);

                descriptionLabel.setBorder(
                                BorderFactory.createEmptyBorder(
                                                7,
                                                0,
                                                0,
                                                0));

                textPanel.add(
                                titleLabel);

                textPanel.add(
                                descriptionLabel);

                card.add(
                                textPanel,
                                BorderLayout.CENTER);

                // =====================================================
                // HOVER EFFECT
                // =====================================================

                card.addMouseListener(
                                new MouseAdapter() {

                                        @Override
                                        public void mouseEntered(
                                                        MouseEvent e) {

                                                // Strong visible hover background
                                                card.setBackground(
                                                                new Color(
                                                                                235,
                                                                                242,
                                                                                248));

                                                // Accent-colored border
                                                card.setBorder(
                                                                BorderFactory.createCompoundBorder(
                                                                                BorderFactory.createLineBorder(
                                                                                                accent,
                                                                                                2),
                                                                                BorderFactory.createEmptyBorder(
                                                                                                19,
                                                                                                21,
                                                                                                19,
                                                                                                21)));

                                                card.setCursor(
                                                                new Cursor(
                                                                                Cursor.HAND_CURSOR));
                                        }

                                        @Override
                                        public void mouseExited(
                                                        MouseEvent e) {

                                                // Return to normal
                                                card.setBackground(
                                                                CARD);

                                                card.setBorder(
                                                                BorderFactory.createCompoundBorder(
                                                                                BorderFactory.createLineBorder(
                                                                                                new Color(
                                                                                                                220,
                                                                                                                227,
                                                                                                                234),
                                                                                                1),
                                                                                BorderFactory.createEmptyBorder(
                                                                                                20,
                                                                                                22,
                                                                                                20,
                                                                                                22)));

                                                card.setCursor(
                                                                new Cursor(
                                                                                Cursor.DEFAULT_CURSOR));
                                        }
                                });

                return card;
        }
}