package view;

import javax.swing.*;
import java.awt.*;
import controller.ParkingController;
import model.ParkedVehicle;
import model.Iterator.ParkedVehicleIterator;
import model.Iterator.FineIterator;
import java.util.ArrayList;
import java.util.List;

public class ReportPanel extends JFrame {

    private ParkingController parkingController;
    JTabbedPane tabs;
    public ReportPanel() {

        parkingController = new ParkingController();

        setTitle("Reporting Panel");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshReports());
        topBar.add(refreshBtn);
        add(topBar, BorderLayout.NORTH);

        tabs = new JTabbedPane();

        tabs.addTab("Vehicles In Lot", buildVehiclesReport());
        tabs.addTab("Revenue Report", buildRevenueReport());
        tabs.addTab("Occupancy Report", buildOccupancyReport());
        tabs.addTab("Outstanding Fines", buildFineReport());

        add(tabs, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel buildVehiclesReport() {

        JPanel panel = new JPanel(new BorderLayout());

        ParkedVehicleIterator iterator = parkingController.getParkedVehiclesTable();

        List<String[]> rows = new ArrayList<>();

        while (iterator.hasNext()) {
            ParkedVehicle v = iterator.next();

            rows.add(new String[] {
                    String.valueOf(v.getFloorId()),
                    v.getSpotCode(),
                    v.getLicensePlate(),
                    v.getEntryTime().toString()
            });
        }

        String[] columns = { "Floor", "Spot Code", "License Plate", "Entry Time" };
        String[][] data = rows.toArray(new String[0][]);

        JTable table = new JTable(data, columns);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildRevenueReport() {

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        double total = parkingController.getTotalRevenue();
        double daily = parkingController.getDailyRevenue();
        double weekly = parkingController.getWeeklyRevenue();
        double monthly = parkingController.getMonthlyRevenue();

        panel.add(new JLabel("Total Revenue: RM " + String.format("%.2f", total)));
        panel.add(new JLabel("Daily Revenue: RM " + String.format("%.2f", daily)));
        panel.add(new JLabel("Weekly Revenue: RM " + String.format("%.2f", weekly)));
        panel.add(new JLabel("Monthly Revenue: RM " + String.format("%.2f", monthly)));

        return panel;
    }

    private JPanel buildOccupancyReport() {

        JPanel panel = new JPanel(new BorderLayout());

        List<String[]> rows = parkingController.getOccupancyBySpotType();

        String[] columns = {
                "Spot Type",
                "Total Spots",
                "Occupied",
                "Occupancy Rate"
        };

        String[][] data = rows.toArray(new String[0][]);

        JTable table = new JTable(data, columns);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildFineReport() {

        JPanel panel = new JPanel(new BorderLayout());

        FineIterator iterator = parkingController.getUnpaidFinesTable();
        List<String[]> rows = new ArrayList<>();

        while (iterator.hasNext()) {
            rows.add(iterator.next());
        }

        String[] columns = { "License Plate", "Amount (RM)", "Status", "Reason" };
        String[][] data = rows.toArray(new String[0][]);

        JTable table = new JTable(data, columns);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }


    public void refreshReports() {
        tabs.removeAll();

        tabs.addTab("Vehicles In Lot", buildVehiclesReport());
        tabs.addTab("Revenue Report", buildRevenueReport());
        tabs.addTab("Occupancy Report", buildOccupancyReport());
        tabs.addTab("Outstanding Fines", buildFineReport());

        add(tabs, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}