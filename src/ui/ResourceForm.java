package ui;

import dao.ResourceDAO;
import model.Resource;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ResourceForm extends JFrame {

    private JTable resourceTable;
    private DefaultTableModel tableModel;

    private JTextField nameField;
    private JComboBox<Integer> floorComboBox;

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

    private static final Color SUCCESS =
            new Color(35, 150, 100);

    private static final Color DANGER =
            new Color(205, 75, 75);


    public ResourceForm() {

        setTitle(
                "CampusSlot - Manage Smartboards"
        );

        setSize(
                900,
                600
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
                        "Manage Smartboards"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(
                Color.WHITE
        );


        JLabel subtitle =
                new JLabel(
                        "Add, view and manage campus smartboards"
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
                        "SMARTBOARD MANAGEMENT"
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
        // CENTER AREA
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout()
                );

        centerPanel.setBackground(
                BACKGROUND
        );

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        35,
                        20,
                        35
                )
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
                                15,
                                15,
                                15,
                                15
                        )
                )
        );


        String[] columns = {
                "ID",
                "Smartboard Name",
                "Floor"
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


        resourceTable =
                new JTable(
                        tableModel
                );


        // =====================================================
        // TABLE STYLE
        // =====================================================

        resourceTable.setRowHeight(
                38
        );

        resourceTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        resourceTable.setForeground(
                TEXT
        );

        resourceTable.setBackground(
                Color.WHITE
        );

        resourceTable.setGridColor(
                new Color(
                        230,
                        235,
                        240
                )
        );

        resourceTable.setSelectionBackground(
                new Color(
                        220,
                        238,
                        246
                )
        );

        resourceTable.setSelectionForeground(
                TEXT
        );

        resourceTable.setShowVerticalLines(
                false
        );

        resourceTable.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );


        // =====================================================
        // TABLE HEADER
        // =====================================================

        JTableHeader tableHeader =
                resourceTable.getTableHeader();

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
                        13
                )
        );


        DefaultTableCellRenderer headerRenderer =
                new DefaultTableCellRenderer();

        headerRenderer.setBackground(
                HEADER
        );

        headerRenderer.setForeground(
                Color.WHITE
        );

        headerRenderer.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        headerRenderer.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        10,
                        0,
                        10
                )
        );


        for (int i = 0;
             i < resourceTable.getColumnCount();
             i++) {

            resourceTable
                    .getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(
                            headerRenderer
                    );
        }


        // =====================================================
        // CENTER ALIGN ID + FLOOR
        // =====================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        resourceTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );

        resourceTable
                .getColumnModel()
                .getColumn(2)
                .setCellRenderer(
                        centerRenderer
                );


        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        resourceTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(80);

        resourceTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(450);

        resourceTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(150);


        JScrollPane scrollPane =
                new JScrollPane(
                        resourceTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getViewport().setBackground(
                Color.WHITE
        );


        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );


        centerPanel.add(
                tableCard,
                BorderLayout.CENTER
        );


        add(
                centerPanel,
                BorderLayout.CENTER
        );


// =====================================================
// BOTTOM AREA
// =====================================================

JPanel bottomWrapper =
        new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        10
                )
        );

bottomWrapper.setBackground(
        BACKGROUND
);

bottomWrapper.setBorder(
        BorderFactory.createEmptyBorder(
                0,
                20,
                20,
                20
        )
);


// =====================================================
// SMARTBOARD NAME
// =====================================================

JLabel nameLabel =
        new JLabel(
                "Smartboard Name"
        );

nameLabel.setFont(
        new Font(
                "Arial",
                Font.BOLD,
                13
        )
);

nameLabel.setForeground(
        TEXT
);


nameField =
        new JTextField(
                18
        );

nameField.setFont(
        new Font(
                "Arial",
                Font.PLAIN,
                14
        )
);

nameField.setPreferredSize(
        new Dimension(
                190,
                38
        )
);


// =====================================================
// FLOOR
// =====================================================

JLabel floorLabel =
        new JLabel(
                "Floor"
        );

floorLabel.setFont(
        new Font(
                "Arial",
                Font.BOLD,
                13
        )
);

floorLabel.setForeground(
        TEXT
);


floorComboBox =
        new JComboBox<>();

floorComboBox.addItem(1);
floorComboBox.addItem(2);
floorComboBox.addItem(3);
floorComboBox.addItem(4);

floorComboBox.setFont(
        new Font(
                "Arial",
                Font.BOLD,
                14
        )
);

floorComboBox.setPreferredSize(
        new Dimension(
                70,
                38
        )
);


// =====================================================
// ADD BUTTON
// =====================================================

JButton addButton =
        createButton(
                "Add Smartboard",
                SUCCESS
        );

addButton.setPreferredSize(
        new Dimension(
                150,
                42
        )
);


// =====================================================
// DELETE BUTTON
// =====================================================

JButton deleteButton =
        createButton(
                "Delete Selected",
                DANGER
        );

deleteButton.setPreferredSize(
        new Dimension(
                150,
                42
        )
);


// =====================================================
// ADD COMPONENTS
// =====================================================

bottomWrapper.add(
        nameLabel
);

bottomWrapper.add(
        nameField
);

bottomWrapper.add(
        floorLabel
);

bottomWrapper.add(
        floorComboBox
);

bottomWrapper.add(
        addButton
);

bottomWrapper.add(
        deleteButton
);


add(
        bottomWrapper,
        BorderLayout.SOUTH
);


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        addButton.addActionListener(
                e -> addSmartboard()
        );

        deleteButton.addActionListener(
                e -> deleteSmartboard()
        );


        // =====================================================
        // LOAD DATA
        // =====================================================

        loadResources();


        setVisible(true);
    }


    // =========================================================
    // CREATE STYLED BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                color
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        Color normalColor =
                color;

        Color hoverColor =
                color.brighter();


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


        return button;
    }


    // =========================================================
    // LOAD SMARTBOARDS
    // =========================================================

    private void loadResources() {

        tableModel.setRowCount(0);

        ResourceDAO resourceDAO =
                new ResourceDAO();

        List<Resource> resources =
                resourceDAO.getAllResources();


        for (Resource resource : resources) {

            Object[] row = {
                    resource.getResourceId(),
                    resource.getResourceName(),
                    resource.getFloor()
            };

            tableModel.addRow(
                    row
            );
        }
    }


    // =========================================================
    // ADD SMARTBOARD
    // =========================================================

    private void addSmartboard() {

        String name =
                nameField.getText().trim();

        int floor =
                (int) floorComboBox.getSelectedItem();


        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a smartboard name."
            );

            return;
        }


        ResourceDAO resourceDAO =
                new ResourceDAO();


        boolean added =
                resourceDAO.addResource(
                        name,
                        "Smartboard",
                        floor,
                        "Available"
                );


        if (added) {

            JOptionPane.showMessageDialog(
                    this,
                    "Smartboard added successfully!"
            );

            nameField.setText("");

            floorComboBox.setSelectedIndex(
                    0
            );

            loadResources();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add smartboard."
            );
        }
    }


    // =========================================================
    // DELETE SMARTBOARD
    // =========================================================

    private void deleteSmartboard() {

        int selectedRow =
                resourceTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a smartboard to delete."
            );

            return;
        }


        int resourceId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this smartboard?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );


        if (choice ==
                JOptionPane.YES_OPTION) {

            ResourceDAO resourceDAO =
                    new ResourceDAO();


            boolean deleted =
                    resourceDAO.deleteResource(
                            resourceId
                    );


            if (deleted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Smartboard deleted successfully!"
                );

                loadResources();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete smartboard."
                );
            }
        }
    }
}