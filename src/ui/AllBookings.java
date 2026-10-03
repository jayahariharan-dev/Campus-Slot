package ui;

import dao.BookingDAO;
import dao.TelegramUserDAO;
import model.CancellationResult;
import telegram.TelegramService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AllBookings extends JFrame {

    private JTable bookingTable;
    private DefaultTableModel tableModel;

    // =========================================================
    // THEME
    // =========================================================

    private static final Color BACKGROUND =
            new Color(242, 246, 250);

    private static final Color HEADER =
            new Color(20, 38, 63);

    private static final Color TEXT =
            new Color(30, 42, 56);

    private static final Color SUBTEXT =
            new Color(105, 118, 132);

    private static final Color CARD =
            Color.WHITE;

    private static final Color DANGER =
            new Color(205, 75, 75);


    public AllBookings() {

        setTitle(
                "CampusSlot - All Bookings"
        );

        setSize(
                1100,
                650
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(
                new BorderLayout()
        );

        getContentPane().setBackground(
                BACKGROUND
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                HEADER
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        35,
                        20,
                        35
                )
        );


        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);


        JLabel title =
                new JLabel(
                        "All Bookings"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        title.setForeground(
                Color.WHITE
        );


        JLabel subtitle =
                new JLabel(
                        "View and manage smartboard reservations"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(
                new Color(
                        190,
                        210,
                        225
                )
        );

        subtitle.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        1,
                        0,
                        0
                )
        );


        titlePanel.add(title);
        titlePanel.add(subtitle);


        JLabel sectionLabel =
                new JLabel(
                        "BOOKING MANAGEMENT"
                );

        sectionLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        sectionLabel.setForeground(
                new Color(
                        120,
                        220,
                        230
                )
        );


        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                sectionLabel,
                BorderLayout.EAST
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLE CARD
        // =====================================================

        JPanel tableCard =
                new JPanel(
                        new BorderLayout()
                );

        tableCard.setBackground(
                CARD
        );

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        227,
                                        234
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );


        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "Booking ID",
                "Floor",
                "Smartboard",
                "Date",
                "Period",
                "Start Time",
                "End Time",
                "Staff Name",
                "Purpose",
                "Status"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        bookingTable =
                new JTable(
                        tableModel
                );


        // TABLE APPEARANCE

        bookingTable.setRowHeight(
                34
        );

        bookingTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        bookingTable.setForeground(
                TEXT
        );

        bookingTable.setBackground(
                Color.WHITE
        );

        bookingTable.setGridColor(
                new Color(
                        225,
                        231,
                        237
                )
        );

        bookingTable.setShowVerticalLines(
                false
        );

        bookingTable.setSelectionBackground(
                new Color(
                        218,
                        235,
                        243
                )
        );

        bookingTable.setSelectionForeground(
                TEXT
        );

        bookingTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );


        // =====================================================
        // TABLE HEADER
        // =====================================================

        JTableHeader tableHeader =
                bookingTable.getTableHeader();

        tableHeader.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        tableHeader.setBackground(
                HEADER
        );

        tableHeader.setForeground(
                Color.WHITE
        );

        tableHeader.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );


        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        int[] widths = {
                90,
                60,
                130,
                105,
                75,
                100,
                100,
                140,
                220,
                90
        };


        for (int i = 0; i < widths.length; i++) {

            bookingTable
                    .getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(
                            widths[i]
                    );
        }


        // =====================================================
        // CENTER IMPORTANT COLUMNS
        // =====================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        bookingTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );

        bookingTable
                .getColumnModel()
                .getColumn(1)
                .setCellRenderer(
                        centerRenderer
                );

        bookingTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        centerRenderer
                );

        bookingTable
                .getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        centerRenderer
                );

        bookingTable
                .getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        centerRenderer
                );

        bookingTable
                .getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        centerRenderer
                );


        // =====================================================
        // STATUS RENDERER
        // =====================================================

        bookingTable
                .getColumnModel()
                .getColumn(9)
                .setCellRenderer(
                        new StatusRenderer()
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        bookingTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                220,
                                227,
                                234
                        )
                )
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );


        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );


        add(
                tableCard,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM ACTION AREA
        // =====================================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setBackground(
                BACKGROUND
        );

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        30,
                        18,
                        30
                )
        );


        JLabel hintLabel =
                new JLabel(
                        "Select a booking from the table to cancel it."
                );

        hintLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        hintLabel.setForeground(
                SUBTEXT
        );


        JButton cancelButton =
                new JButton(
                        "Cancel Selected Booking"
                );

        cancelButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        cancelButton.setForeground(
                Color.WHITE
        );

        cancelButton.setBackground(
                DANGER
        );

        cancelButton.setFocusPainted(
                false
        );

        cancelButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        cancelButton.setPreferredSize(
                new Dimension(
                        215,
                        43
                )
        );

        cancelButton.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );


        addButtonHoverEffect(
                cancelButton,
                DANGER
        );


        bottomPanel.add(
                hintLabel,
                BorderLayout.WEST
        );

        bottomPanel.add(
                cancelButton,
                BorderLayout.EAST
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // CANCEL ACTION
        // =====================================================

        cancelButton.addActionListener(
                e -> cancelSelectedBooking()
        );


        // =====================================================
        // INITIAL LOAD
        // =====================================================

        loadBookings();

        setVisible(true);
    }


    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private static class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );


            setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            12
                    )
            );


            if (!isSelected) {

                setForeground(
                        new Color(
                                35,
                                150,
                                100
                        )
                );

            } else {

                setForeground(
                        TEXT
                );
            }


            return component;
        }
    }


    // =========================================================
    // BUTTON HOVER
    // =========================================================

    private void addButtonHoverEffect(
            JButton button,
            Color normalColor
    ) {

        Color hoverColor =
                normalColor.brighter();


        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                hoverColor
                        );
                    }


                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                normalColor
                        );
                    }
                }
        );
    }


    // =========================================================
    // LOAD BOOKINGS
    // =========================================================

    private void loadBookings() {

        tableModel.setRowCount(
                0
        );

        BookingDAO bookingDAO =
                new BookingDAO();

        List<Object[]> bookings =
                bookingDAO.getAllBookings();


        for (Object[] booking :
                bookings) {

            tableModel.addRow(
                    booking
            );
        }
    }


    // =========================================================
    // CANCEL SELECTED BOOKING
    // =========================================================

    private void cancelSelectedBooking() {

        int selectedRow =
                bookingTable.getSelectedRow();


        // NO ROW SELECTED

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a booking to cancel."
            );

            return;
        }


        // GET BOOKING ID

        int bookingId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        // GET BOOKING DETAILS FOR TELEGRAM MESSAGE

        int floor =
                (int) tableModel.getValueAt(
                        selectedRow,
                        1
                );


        String smartboard =
                (String) tableModel.getValueAt(
                        selectedRow,
                        2
                );


        Object dateValue =
                tableModel.getValueAt(
                        selectedRow,
                        3
                );


        String period =
                (String) tableModel.getValueAt(
                        selectedRow,
                        4
                );


        String startTime =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                5
                        )
                );


        String endTime =
                String.valueOf(
                        tableModel.getValueAt(
                                selectedRow,
                                6
                        )
                );


        LocalDate bookingDate;


        if (dateValue instanceof LocalDate) {

            bookingDate =
                    (LocalDate) dateValue;

        } else {

            bookingDate =
                    LocalDate.parse(
                            dateValue.toString()
                    );
        }


        // =====================================================
        // CONFIRM CANCELLATION
        // =====================================================

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel this booking?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION
                );


        if (choice != JOptionPane.YES_OPTION) {

            return;
        }


        // =====================================================
        // CANCEL BOOKING
        // =====================================================

        BookingDAO bookingDAO =
                new BookingDAO();

        CancellationResult result =
                bookingDAO.cancelBooking(
                        bookingId
                );


        // CHECK IF CANCELLATION FAILED

        if (!result.isCancelled()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to cancel booking."
            );

            return;
        }


        // =====================================================
        // GET WAITING STAFF
        // =====================================================

        List<String> waitingStaff =
                result.getWaitingStaff();


        TelegramUserDAO telegramUserDAO =
                new TelegramUserDAO();

        TelegramService telegramService =
                new TelegramService();


        int notificationSuccessCount =
                0;

        int notificationFailureCount =
                0;


        // =====================================================
        // SEND TELEGRAM NOTIFICATIONS
        // =====================================================

        for (String staff :
                waitingStaff) {

            Long chatId =
                    telegramUserDAO
                            .getChatIdByStaffName(
                                    staff
                            );


            // STAFF HAS NOT REGISTERED TELEGRAM

            if (chatId == null) {

                System.out.println(
                        "No Telegram Chat ID found for: "
                                + staff
                );

                notificationFailureCount++;

                continue;
            }


            String telegramMessage =
                    "🔔 CampusSlot Alert!\n\n"
                            + "A Smartboard is now available.\n\n"
                            + "Floor: "
                            + floor
                            + "\n"
                            + "Smartboard: "
                            + smartboard
                            + "\n"
                            + "Date: "
                            + bookingDate
                            + "\n"
                            + "Period: "
                            + period
                            + "\n"
                            + "Time: "
                            + startTime
                            + " - "
                            + endTime
                            + "\n\n"
                            + "You can now book this slot.";


            boolean sent =
                    telegramService.sendMessage(
                            chatId,
                            telegramMessage
                    );


            // REMOVE ONLY IF NOTIFICATION SUCCEEDS

            if (sent) {

                boolean removed =
                        bookingDAO
                                .removeIndividualFromWaitlist(
                                        result.getResourceId(),
                                        result.getPeriodId(),
                                        staff,
                                        result.getBookingDate()
                                );


                if (removed) {

                    notificationSuccessCount++;

                    System.out.println(
                            "Notification sent and waitlist entry removed for: "
                                    + staff
                    );

                } else {

                    notificationFailureCount++;

                    System.out.println(
                            "Notification was sent, but waitlist entry "
                                    + "could not be removed for: "
                                    + staff
                    );
                }

            } else {

                notificationFailureCount++;

                System.out.println(
                        "Telegram notification failed for: "
                                + staff
                );
            }
        }


        // =====================================================
        // RESULT MESSAGE
        // =====================================================

        String message =
                "Booking cancelled successfully!";


        if (waitingStaff.isEmpty()) {

            message +=
                    "\n\nNo staff are waiting for this slot.";

        } else {

            message +=
                    "\n\nTelegram notification summary:"
                            + "\nSuccessfully notified: "
                            + notificationSuccessCount
                            + "\nFailed / not registered: "
                            + notificationFailureCount;
        }


        JOptionPane.showMessageDialog(
                this,
                message
        );


        // =====================================================
        // RELOAD BOOKINGS TABLE
        // =====================================================

        loadBookings();
    }
}