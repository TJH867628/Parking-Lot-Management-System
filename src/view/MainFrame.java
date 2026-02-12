package view;

import javax.swing.*;

import controller.ParkingController;
import model.ParkingFloor;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;
import model.ParkingSpot;

import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane floorTabs;
    private JButton selectedSpotButton = null;
    private ParkingController parkingController = new ParkingController();

    public MainFrame() {
        setTitle("Parking Lot Management System");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initTopBar();
        createParkingOverviewPanel();

        setVisible(true);
    }

    private void initTopBar() {
        JPanel topBar = new JPanel();
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Parking Lot Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton customerBtn = new JButton("Customer");
        JButton adminBtn = new JButton("Admin");

        customerBtn.addActionListener(e -> openEntryModule());
        adminBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Admin module is not linked yet.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE));

        actionPanel.add(customerBtn);
        actionPanel.add(adminBtn);

        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(actionPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
    }

    private void createParkingOverviewPanel() {
        floorTabs = new JTabbedPane();

        FloorIterator floorIterator = parkingController.getFloorIterator();
        while (floorIterator.hasNext()) {
            ParkingFloor floor = floorIterator.next();
            floorTabs.addTab("Floor " + floor.getFloorNumber(), createFloorGrid(floor.getFloorNumber()));
        }

        add(floorTabs, BorderLayout.CENTER);
    }

    private JPanel createFloorGrid(int floorNumber) {

        JPanel grid = new JPanel(new GridLayout(4, 5, 20, 30));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        SpotIterator spotIterator = parkingController.getSpotIteratorByFloor(floorNumber);
        while (spotIterator.hasNext()) {
            ParkingSpot spot = spotIterator.next();
            String spotCode = spot.getSpotCode();
            String type = spot.getType();
            double rate = spot.getRatePerHour();
            boolean isOccupied = !spot.isAvailable();
            String currentVehicle = spot.getCurrentVehicle();
            String statusText = isOccupied ?  "Parked\n(" + currentVehicle + ")" : "Available";

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
                spotBtn.addActionListener(e -> selectSpot(spotBtn));
            }

            spotPanel.add(infoLabel, BorderLayout.NORTH);
            spotPanel.add(spotBtn, BorderLayout.CENTER);
            grid.add(spotPanel);
        }

        return grid;
    }

    private void selectSpot(JButton btn) {
        if (selectedSpotButton != null) {
            selectedSpotButton.setBorder(
                    BorderFactory.createLineBorder(Color.GREEN, 2));
        }

        selectedSpotButton = btn;

        btn.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
    }

    private void openEntryModule() {
        EntryFrame entryFrame = new EntryFrame();
        entryFrame.setVisible(true);
    }
}
