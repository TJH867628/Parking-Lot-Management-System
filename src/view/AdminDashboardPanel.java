package view;

import controller.ParkingController;
import model.ParkingFloor;
import model.ParkingSpot;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminDashboardPanel extends JPanel {
    private ParkingController parkingController;
    private JButton refreshButton;
    private JTabbedPane tabs;

    public AdminDashboardPanel(ParkingController parkingController) {
        this.parkingController = parkingController;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Top bar with Refresh button
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshButton = new JButton("Refresh");
        topBar.add(refreshButton);
        add(topBar, BorderLayout.NORTH);

        // Tabs
        tabs = new JTabbedPane();
        tabs.addTab("Floors & Spots", buildFloorsPanel());
        tabs.addTab("Occupancy Rate", buildOccupancyPanel());
        tabs.addTab("Revenue", buildRevenuePanel());
        tabs.addTab("Vehicles Parked", buildVehiclesPanel());
        tabs.addTab("Unpaid Fines", buildFinesPanel());
        tabs.addTab("Fine Scheme", buildFineSchemePanel());

        add(tabs, BorderLayout.CENTER);

        // Refresh button action
        refreshButton.addActionListener(e -> refreshDashboard());
    }

    // Floors & Spots tab
    private JPanel buildFloorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTabbedPane floorTabs = new JTabbedPane();

        FloorIterator floorIterator = parkingController.getFloorIterator();
        while (floorIterator.hasNext()) {
            ParkingFloor floor = floorIterator.next();
            floorTabs.addTab("Floor " + floor.getFloorNumber(),
                    buildFloorGrid(floor.getFloorId()));
        }

        panel.add(floorTabs, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildFloorGrid(int floorId) {
        JPanel grid = new JPanel(new GridLayout(4, 5, 20, 30));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        SpotIterator spotIterator = parkingController.getSpotIteratorByFloor(floorId);
        while (spotIterator.hasNext()) {
            ParkingSpot spot = spotIterator.next();
            String spotCode = spot.getSpotCode();
            String type = spot.getType();
            double rate = spot.getRatePerHour();
            boolean isOccupied = !spot.isAvailable();
            String currentVehicle = spot.getCurrentVehicle();
            String statusText = isOccupied ? "Parked\n(" + currentVehicle + ")" : "Available";

            JPanel spotPanel = new JPanel(new BorderLayout(5, 5));
            JLabel infoLabel = new JLabel(
                    "<html><center>" +
                            spotCode + " | " + type + "<br>" +
                            "RM " + rate + "/hr" +
                            "</center></html>",
                    SwingConstants.CENTER);
            infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));

            JButton spotBtn = new JButton(
                    "<html><center>" + statusText.replace("\n", "<br>") + "</center></html>");
            spotBtn.setPreferredSize(new Dimension(110, 70));
            spotBtn.setFocusPainted(false);

            if (isOccupied) {
                spotBtn.setBackground(Color.LIGHT_GRAY);
                spotBtn.setEnabled(false);
            } else {
                spotBtn.setBackground(Color.WHITE);
                spotBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            }

            spotPanel.add(infoLabel, BorderLayout.NORTH);
            spotPanel.add(spotBtn, BorderLayout.CENTER);
            grid.add(spotPanel);
        }

        return grid;
    }

    // Occupancy Rate tab
    private JPanel buildOccupancyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        int total = parkingController.getTotalSpots();
        int occupied = parkingController.getOccupiedSpots();
        double rate = total == 0 ? 0 : (occupied * 100.0 / total);

        JLabel label = new JLabel("Current Occupancy Rate: " +
                String.format("%.2f", rate) + "%", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // Revenue tab
private JPanel buildRevenuePanel() {
    JPanel panel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column

    double total = parkingController.getTotalRevenue();
    double daily = parkingController.getDailyRevenue();
    double weekly = parkingController.getWeeklyRevenue();
    double monthly = parkingController.getMonthlyRevenue();

    JLabel totalLabel = new JLabel("Total Revenue Collected: RM " + String.format("%.2f", total), SwingConstants.CENTER);
    JLabel dailyLabel = new JLabel("Daily Revenue: RM " + String.format("%.2f", daily), SwingConstants.CENTER);
    JLabel weeklyLabel = new JLabel("Weekly Revenue: RM " + String.format("%.2f", weekly), SwingConstants.CENTER);
    JLabel monthlyLabel = new JLabel("Monthly Revenue: RM " + String.format("%.2f", monthly), SwingConstants.CENTER);

    Font font = new Font("Arial", Font.BOLD, 16);
    totalLabel.setFont(font);
    dailyLabel.setFont(font);
    weeklyLabel.setFont(font);
    monthlyLabel.setFont(font);

    panel.add(totalLabel);
    panel.add(dailyLabel);
    panel.add(weeklyLabel);
    panel.add(monthlyLabel);

    return panel;
}


    // Vehicles Parked tab
private JPanel buildVehiclesPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    List<String[]> vehicles = parkingController.getParkedVehiclesTable();

    // Define table columns to match DAO order
    String[] columnNames = { "Floor", "Spot Code", "License Plate", "Entry Time" };

    // Convert List<String[]> to 2D array
    String[][] data = vehicles.toArray(new String[0][]);

    JTable table = new JTable(data, columnNames);
    table.setFillsViewportHeight(true);
    table.setRowHeight(25);
    table.setAutoCreateRowSorter(true); // allow sorting by columns

    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
}



    // Unpaid Fines tab (placeholder until DAO implemented)
    // Unpaid Fines tab
   private JPanel buildFinesPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Call controller method
    List<String[]> fines = parkingController.getUnpaidFinesTable();

    // Define table columns
    String[] columnNames = { "License Plate", "Fine Amount (RM)", "Status", "Reason" };
    String[][] data = fines.toArray(new String[0][]);

    JTable table = new JTable(data, columnNames);
    table.setFillsViewportHeight(true);
    table.setRowHeight(25);
    table.setAutoCreateRowSorter(true);

    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
}



    // Fine Scheme tab (placeholder)
    private JPanel buildFineSchemePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Select Fine Scheme:");
        String[] schemes = { "Flat Rate", "Per Hour", "Progressive" };
        JComboBox<String> comboBox = new JComboBox<>(schemes);

        panel.add(label);
        panel.add(comboBox);

        JButton applyBtn = new JButton("Apply Scheme");
        panel.add(applyBtn);

        applyBtn.addActionListener(e -> {
            String scheme = (String) comboBox.getSelectedItem();
            JOptionPane.showMessageDialog(this,
                    "Fine scheme set to: " + scheme);
        });

        return panel;
    }

    private void refreshDashboard() {
    // Run fine generation first
    parkingController.checkAndGenerateFines();

    // Reload controller if needed
    parkingController = new ParkingController();

    // Rebuild tabs
    tabs.removeAll();
    tabs.addTab("Floors & Spots", buildFloorsPanel());
    tabs.addTab("Occupancy Rate", buildOccupancyPanel());
    tabs.addTab("Revenue", buildRevenuePanel());
    tabs.addTab("Vehicles Parked", buildVehiclesPanel());
    tabs.addTab("Unpaid Fines", buildFinesPanel());
    tabs.addTab("Fine Scheme", buildFineSchemePanel());

    revalidate();
    repaint();
    }
}