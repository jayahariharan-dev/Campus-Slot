package ui;

import dao.BookingDAO;
import telegram.BookingNotificationScheduler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingForm extends JFrame {

    private JComboBox<String> floorComboBox;
    private JComboBox<String> smartboardComboBox;
    private JComboBox<String> dateComboBox;

    private JTextField staffNameField;
    private JTextField purposeField;

    private JPanel periodPanel;
    private JButton[] periodButtons;

    private LocalDate[] availableDates;

    private int selectedPeriodId = -1;
    private String selectedPeriodName = "";

    private final String[] periodNames = {
            "P1", "P2", "P3", "P4",
            "P5", "P6", "P7", "P8"
    };

    private final String[] periodTimes = {
            "08:50 AM - 09:40 AM",
            "09:40 AM - 10:30 AM",
            "10:45 AM - 11:35 AM",
            "11:35 AM - 12:25 PM",
            "01:10 PM - 02:00 PM",
            "02:00 PM - 02:50 PM",
            "03:00 PM - 03:50 PM",
            "03:50 PM - 04:40 PM"
    };

    private static BookingNotificationScheduler notificationScheduler;

    // =========================================================
    // THEME COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(242, 246, 250);

    private static final Color HEADER =
            new Color(20, 38, 63);

    private static final Color CARD =
            Color.WHITE;

    private static final Color TEXT =
            new Color(30, 42, 56);

    private static final Color AVAILABLE =
            new Color(35, 150, 100);

    private static final Color BOOKED =
            new Color(205, 75, 75);

    private static final Color BOOK_BUTTON =
            new Color(0, 130, 170);


    public static void setNotificationScheduler(
            BookingNotificationScheduler scheduler) {

        notificationScheduler = scheduler;
    }


    public BookingForm() {

        setTitle(
                "CampusSlot - Book a Smartboard"
        );

        setSize(
                900,
                760
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
                        "Book a Smartboard"
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
                        "Choose a smartboard, date and available time slot"
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
                        "SMARTBOARD BOOKING"
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
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel =
                new JPanel();

        mainPanel.setLayout(
                new BoxLayout(
                        mainPanel,
                        BoxLayout.Y_AXIS
                )
        );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        40,
                        25,
                        40
                )
        );


        // =====================================================
        // SELECTION CARD
        // =====================================================

        JPanel selectionCard =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                15,
                                15
                        )
                );

        selectionCard.setBackground(
                CARD
        );

        selectionCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        227,
                                        234
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                20,
                                25,
                                20,
                                25
                        )
                )
        );


        // FLOOR

        selectionCard.add(
                createFieldLabel(
                        "Select Floor"
                )
        );

        floorComboBox =
                new JComboBox<>();

        floorComboBox.addItem("1st Floor");
        floorComboBox.addItem("2nd Floor");
        floorComboBox.addItem("3rd Floor");
        floorComboBox.addItem("4th Floor");

        styleComboBox(
                floorComboBox
        );

        selectionCard.add(
                floorComboBox
        );


        // SMARTBOARD

        selectionCard.add(
                createFieldLabel(
                        "Select Smartboard"
                )
        );

        smartboardComboBox =
                new JComboBox<>();

        styleComboBox(
                smartboardComboBox
        );

        selectionCard.add(
                smartboardComboBox
        );


        // DATE

        selectionCard.add(
                createFieldLabel(
                        "Booking Date"
                )
        );

        dateComboBox =
                new JComboBox<>();

        LocalDate today =
                LocalDate.now();

        availableDates =
                new LocalDate[] {
                        today,
                        today.plusDays(1),
                        today.plusDays(2)
                };

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy"
                );


        dateComboBox.addItem(
                "Today - "
                        + availableDates[0]
                        .format(formatter)
        );

        dateComboBox.addItem(
                "Tomorrow - "
                        + availableDates[1]
                        .format(formatter)
        );

        dateComboBox.addItem(
                "Day After Tomorrow - "
                        + availableDates[2]
                        .format(formatter)
        );

        styleComboBox(
                dateComboBox
        );

        selectionCard.add(
                dateComboBox
        );


        mainPanel.add(
                selectionCard
        );


        mainPanel.add(
                Box.createVerticalStrut(
                        22
                )
        );


        // =====================================================
        // TIME SLOT HEADER
        // =====================================================

        JPanel slotHeader =
                new JPanel(
                        new BorderLayout()
                );

        slotHeader.setOpaque(false);

        slotHeader.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );


        JLabel periodLabel =
                new JLabel(
                        "Select a Time Slot"
                );

        periodLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        periodLabel.setForeground(
                TEXT
        );


        JPanel legendPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                12,
                                0
                        )
                );

        legendPanel.setOpaque(false);


        JLabel availableLabel =
                new JLabel(
                        "● Available"
                );

        availableLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        availableLabel.setForeground(
                AVAILABLE
        );


        JLabel bookedLabel =
                new JLabel(
                        "● Booked"
                );

        bookedLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        bookedLabel.setForeground(
                BOOKED
        );


        legendPanel.add(
                availableLabel
        );

        legendPanel.add(
                bookedLabel
        );


        slotHeader.add(
                periodLabel,
                BorderLayout.WEST
        );

        slotHeader.add(
                legendPanel,
                BorderLayout.EAST
        );


        mainPanel.add(
                slotHeader
        );


        mainPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );


        // =====================================================
        // PERIOD BUTTONS
        // =====================================================

        periodPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                12,
                                12
                        )
                );

        periodPanel.setOpaque(false);

        periodButtons =
                new JButton[8];


        for (int i = 0; i < 8; i++) {

            JButton button =
                    createPeriodButton();

            final int index = i;

            button.addActionListener(
                    e -> selectPeriod(index)
            );

            periodButtons[i] =
                    button;

            periodPanel.add(
                    button
            );
        }


        mainPanel.add(
                periodPanel
        );


        mainPanel.add(
                Box.createVerticalStrut(
                        22
                )
        );


        // =====================================================
        // STAFF + PURPOSE CARD
        // =====================================================

        JPanel detailsCard =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                15,
                                12
                        )
                );

        detailsCard.setBackground(
                CARD
        );

        detailsCard.setBorder(
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
                                25,
                                18,
                                25
                        )
                )
        );


        detailsCard.add(
                createFieldLabel(
                        "Staff Name *"
                )
        );

        staffNameField =
                createTextField();

        detailsCard.add(
                staffNameField
        );


        detailsCard.add(
                createFieldLabel(
                        "Purpose *"
                )
        );

        purposeField =
                createTextField();

        detailsCard.add(
                purposeField
        );


        mainPanel.add(
                detailsCard
        );


        // =====================================================
        // SCROLL PANE
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        mainPanel
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );

        scrollPane.getViewport()
                .setBackground(
                        BACKGROUND
                );


        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOOK BUTTON
        // =====================================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                35,
                                15
                        )
                );

        bottomPanel.setBackground(
                BACKGROUND
        );


        JButton bookButton =
                new JButton(
                        "Book Selected Smartboard"
                );

        bookButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        bookButton.setForeground(
                Color.WHITE
        );

        bookButton.setBackground(
                BOOK_BUTTON
        );

        bookButton.setFocusPainted(
                false
        );

        bookButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        bookButton.setPreferredSize(
                new Dimension(
                        230,
                        45
                )
        );

        bookButton.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );


        addButtonHoverEffect(
                bookButton,
                BOOK_BUTTON
        );


        bottomPanel.add(
                bookButton
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // FLOOR CHANGE
        // =====================================================

        floorComboBox.addActionListener(
                e -> {

                    loadSmartboardsByFloor();

                    refreshSlots();
                }
        );


        // =====================================================
        // SMARTBOARD CHANGE
        // =====================================================

        smartboardComboBox.addActionListener(
                e -> refreshSlots()
        );


        // =====================================================
        // DATE CHANGE
        // =====================================================

        dateComboBox.addActionListener(
                e -> refreshSlots()
        );


        // =====================================================
        // BOOK ACTION
        // =====================================================

        bookButton.addActionListener(
                e -> bookSmartboard()
        );


        // =====================================================
        // INITIAL LOAD
        // =====================================================

        loadSmartboardsByFloor();

        refreshSlots();

        setVisible(true);
    }


    // =========================================================
    // FIELD LABEL
    // =========================================================

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(
                TEXT
        );

        return label;
    }


    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        field.setPreferredSize(
                new Dimension(
                        250,
                        38
                )
        );

        return field;
    }


    // =========================================================
    // COMBO BOX STYLE
    // =========================================================

    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        comboBox.setPreferredSize(
                new Dimension(
                        250,
                        38
                )
        );

        comboBox.setBackground(
                Color.WHITE
        );

        comboBox.setForeground(
                TEXT
        );
    }


    // =========================================================
    // PERIOD BUTTON
    // =========================================================

    private JButton createPeriodButton() {

        JButton button =
                new JButton();

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createLineBorder(
                        AVAILABLE,
                        2
                )
        );

        button.setPreferredSize(
                new Dimension(
                        250,
                        72
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }


    // =========================================================
    // BUTTON HOVER EFFECT
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
    // LOAD SMARTBOARDS FOR SELECTED FLOOR
    // =========================================================

    private void loadSmartboardsByFloor() {

        smartboardComboBox.removeAllItems();

        String selectedFloor =
                (String) floorComboBox
                        .getSelectedItem();

        int floor = 1;

        if (selectedFloor.startsWith("2")) {

            floor = 2;

        } else if (selectedFloor.startsWith("3")) {

            floor = 3;

        } else if (selectedFloor.startsWith("4")) {

            floor = 4;
        }

        BookingDAO bookingDAO =
                new BookingDAO();

        List<String> smartboards =
                bookingDAO.getSmartboardsByFloor(
                        floor
                );

        for (String smartboard :
                smartboards) {

            smartboardComboBox.addItem(
                    smartboard
            );
        }
    }


    // =========================================================
    // REFRESH SLOT AVAILABILITY
    // =========================================================

    private void refreshSlots() {

        selectedPeriodId = -1;

        selectedPeriodName = "";

        if (smartboardComboBox.getItemCount()
                == 0) {

            return;
        }

        String resourceName =
                (String) smartboardComboBox
                        .getSelectedItem();

        int selectedDateIndex =
                dateComboBox.getSelectedIndex();

        LocalDate bookingDate =
                availableDates[
                        selectedDateIndex
                ];

        BookingDAO bookingDAO =
                new BookingDAO();

        int floor =
                floorComboBox
                        .getSelectedIndex()
                        + 1;

        int resourceId =
                bookingDAO
                        .getResourceIdByNameAndFloor(
                                resourceName,
                                floor
                        );


        for (int i = 0; i < 8; i++) {

            int periodId =
                    bookingDAO
                            .getPeriodIdByName(
                                    periodNames[i]
                            );

            boolean booked =
                    bookingDAO.isSlotBooked(
                            resourceId,
                            periodId,
                            bookingDate
                    );


            if (booked) {

                periodButtons[i].setText(
                        "<html>"
                                + "<center>"
                                + "<b>"
                                + periodNames[i]
                                + "</b>"
                                + "<br>"
                                + periodTimes[i]
                                + "<br>"
                                + "<b>BOOKED</b>"
                                + "</center>"
                                + "</html>"
                );

                periodButtons[i].setBackground(
                        BOOKED
                );

                periodButtons[i].setBorder(
                        BorderFactory.createLineBorder(
                                BOOKED.darker(),
                                2
                        )
                );

            } else {

                periodButtons[i].setText(
                        "<html>"
                                + "<center>"
                                + "<b>"
                                + periodNames[i]
                                + "</b>"
                                + "<br>"
                                + periodTimes[i]
                                + "<br>"
                                + "<b>AVAILABLE</b>"
                                + "</center>"
                                + "</html>"
                );

                periodButtons[i].setBackground(
                        AVAILABLE
                );

                periodButtons[i].setBorder(
                        BorderFactory.createLineBorder(
                                AVAILABLE.darker(),
                                2
                        )
                );
            }
        }
    }


    // =========================================================
    // SELECT PERIOD
    // =========================================================

    private void selectPeriod(
            int index
    ) {

        if (smartboardComboBox.getItemCount()
                == 0) {

            return;
        }

        String resourceName =
                (String) smartboardComboBox
                        .getSelectedItem();

        int selectedDateIndex =
                dateComboBox.getSelectedIndex();

        LocalDate bookingDate =
                availableDates[
                        selectedDateIndex
                ];

        BookingDAO bookingDAO =
                new BookingDAO();

        int floor =
                floorComboBox
                        .getSelectedIndex()
                        + 1;

        int resourceId =
                bookingDAO
                        .getResourceIdByNameAndFloor(
                                resourceName,
                                floor
                        );

        int periodId =
                bookingDAO
                        .getPeriodIdByName(
                                periodNames[index]
                        );

        boolean booked =
                bookingDAO.isSlotBooked(
                        resourceId,
                        periodId,
                        bookingDate
                );


        // =====================================================
        // BOOKED SLOT
        // =====================================================

        if (booked) {

            String staffName =
                    JOptionPane.showInputDialog(
                            this,
                            "This slot is already booked.\n\n"
                                    + "Enter your name to be notified "
                                    + "if this slot becomes available:"
                    );


            if (staffName == null) {

                return;
            }


            staffName =
                    staffName.trim();


            boolean alreadyWaiting =
                    bookingDAO.isAlreadyInWaitlist(
                            resourceId,
                            periodId,
                            staffName,
                            bookingDate
                    );


            if (alreadyWaiting) {

                JOptionPane.showMessageDialog(
                        this,
                        "You are already on the notification waitlist for this slot!"
                );

                return;
            }


            if (staffName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Staff name is required!"
                );

                return;
            }


            boolean added =
                    bookingDAO.addToWaitlist(
                            resourceId,
                            periodId,
                            staffName,
                            bookingDate
                    );


            if (added) {

                JOptionPane.showMessageDialog(
                        this,
                        "You have been added to the notification waitlist!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add notification request."
                );
            }

            return;
        }


        // =====================================================
        // AVAILABLE SLOT SELECTED
        // =====================================================

        selectedPeriodId =
                periodId;

        selectedPeriodName =
                periodNames[index];


        // RESET AVAILABLE BUTTON BORDERS

        for (int i = 0; i < 8; i++) {

            int currentPeriodId =
                    bookingDAO.getPeriodIdByName(
                            periodNames[i]
                    );

            boolean currentBooked =
                    bookingDAO.isSlotBooked(
                            resourceId,
                            currentPeriodId,
                            bookingDate
                    );

            if (!currentBooked) {

                periodButtons[i].setBorder(
                        BorderFactory.createLineBorder(
                                AVAILABLE.darker(),
                                2
                        )
                );
            }
        }


        // HIGHLIGHT SELECTED BUTTON

        periodButtons[index].setBorder(
                BorderFactory.createLineBorder(
                        Color.WHITE,
                        4
                )
        );
    }


    // =========================================================
    // CREATE BOOKING
    // =========================================================

    private void bookSmartboard() {

        String staffName =
                staffNameField
                        .getText()
                        .trim();

        String purpose =
                purposeField
                        .getText()
                        .trim();


        // REQUIRED FIELD VALIDATION

        if (staffName.isEmpty()
                || purpose.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Staff Name and Purpose are required!"
            );

            return;
        }


        // PERIOD MUST BE SELECTED

        if (selectedPeriodId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an available time slot!"
            );

            return;
        }


        int selectedDateIndex =
                dateComboBox.getSelectedIndex();

        LocalDate bookingDate =
                availableDates[
                        selectedDateIndex
                ];

        String resourceName =
                (String) smartboardComboBox
                        .getSelectedItem();

        BookingDAO bookingDAO =
                new BookingDAO();

        int floor =
                floorComboBox
                        .getSelectedIndex()
                        + 1;

        int resourceId =
                bookingDAO
                        .getResourceIdByNameAndFloor(
                                resourceName,
                                floor
                        );


        // FINAL DOUBLE CHECK

        boolean alreadyBooked =
                bookingDAO.isSlotBooked(
                        resourceId,
                        selectedPeriodId,
                        bookingDate
                );


        if (alreadyBooked) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sorry! This slot was just booked."
            );

            refreshSlots();

            return;
        }


        // CREATE BOOKING

        boolean booked =
                bookingDAO.createBooking(
                        resourceId,
                        selectedPeriodId,
                        staffName,
                        bookingDate,
                        purpose
                );


        if (booked) {

            JOptionPane.showMessageDialog(
                    this,
                    "Smartboard booked successfully!"
                            + "\n"
                            + floorComboBox
                            .getSelectedItem()
                            + " - "
                            + resourceName
                            + " - "
                            + selectedPeriodName
            );


            // =================================================
            // NOTIFICATION SCHEDULER
            // =================================================

            if (notificationScheduler != null) {

                notificationScheduler
                        .testBookingNotification(
                                resourceId,
                                selectedPeriodId,
                                staffName,
                                bookingDate,
                                purpose,
                                resourceName,
                                floor,
                                selectedPeriodName
                        );
            }


            staffNameField.setText("");

            purposeField.setText("");

            refreshSlots();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking failed!"
            );
        }
    }
}