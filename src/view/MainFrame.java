package view;

import javax.swing.*;

import controller.EntryController;
import controller.ParkingController;
import model.EntryResult;
import model.ParkingFloor;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;
import model.ParkingSpot;
import model.Ticket;
import model.VehicleType;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainFrame extends JFrame {
    private JTabbedPane floorTabs;
    private JButton selectedSpotButton = null;
    private ParkingSpot selectedSpot = null;
    private JLabel selectedSpotLabel;
    private ParkingController parkingController = new ParkingController();
    private EntryController entryController = new EntryController();

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
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Parking Lot Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        selectedSpotLabel = new JLabel("Selected Spot: None");
        selectedSpotLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton parkVehicleBtn = new JButton("Park Vehicle");
        JButton adminBtn = new JButton("Admin");

        parkVehicleBtn.addActionListener(e -> parkSelectedSpot());
        adminBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Admin module is not linked yet.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE));

        actionPanel.add(parkVehicleBtn);
        actionPanel.add(adminBtn);

        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(selectedSpotLabel, BorderLayout.CENTER);
        topBar.add(actionPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
    }

    private void createParkingOverviewPanel() {
        floorTabs = new JTabbedPane();

        FloorIterator floorIterator = parkingController.getFloorIterator();
        while (floorIterator.hasNext()) {
            ParkingFloor floor = floorIterator.next();
            floorTabs.addTab("Floor " + floor.getFloorNumber(), createFloorGrid(floor.getFloorId()));
        }

        add(floorTabs, BorderLayout.CENTER);
    }

    private JPanel createFloorGrid(int floorId) {

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
                spotBtn.addActionListener(e -> selectSpot(spotBtn, spot));
            }

            spotPanel.add(infoLabel, BorderLayout.NORTH);
            spotPanel.add(spotBtn, BorderLayout.CENTER);
            grid.add(spotPanel);
        }

        return grid;
    }

    private void selectSpot(JButton btn, ParkingSpot spot) {
        if (selectedSpotButton != null) {
            selectedSpotButton.setBorder(
                    BorderFactory.createLineBorder(Color.GREEN, 2));
        }

        selectedSpotButton = btn;
        selectedSpot = spot;
        btn.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        updateSelectedSpotLabel();
    }

    private void parkSelectedSpot() {
        if (selectedSpot == null) {
            JOptionPane.showMessageDialog(this, "Please select an available spot first.");
            return;
        }

        JTextField plateField = new JTextField();
        JComboBox<VehicleType> vehicleTypeCombo = new JComboBox<>();
        JCheckBox handicappedCardCheck = new JCheckBox("Has handicapped card");

        List<VehicleType> vehicleTypes = entryController.getVehicleTypes();
        for (VehicleType vehicleType : vehicleTypes) {
            vehicleTypeCombo.addItem(vehicleType);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.add(new JLabel("Selected Spot: " + selectedSpot.getSpotCode()));
        panel.add(new JLabel("Spot Type: " + selectedSpot.getType()));
        panel.add(new JLabel("License Plate:"));
        panel.add(plateField);
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(vehicleTypeCombo);
        panel.add(handicappedCardCheck);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Park Vehicle",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        VehicleType selectedType = (VehicleType) vehicleTypeCombo.getSelectedItem();
        if (selectedType == null) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle type.");
            return;
        }

        String licensePlate = plateField.getText();
        boolean hasCard = handicappedCardCheck.isSelected();

        EntryResult result = entryController.registerEntry(
                licensePlate,
                selectedType.getId(),
                hasCard,
                selectedSpot.getId());

        if (!result.isSuccess()) {
            JOptionPane.showMessageDialog(this, result.getMessage(), "Entry Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, buildTicketMessage(result.getTicket()), "Parking Ticket",
                JOptionPane.INFORMATION_MESSAGE);
        refreshParkingOverview();
    }

    private String buildTicketMessage(Ticket ticket) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return String.format(
                "Ticket No : %s\n" +
                        "Spot      : %s\n" +
                        "Entry Time: %s\n" +
                        "Status    : %s",
                ticket.getTicketCode(),
                ticket.getSpotCode(),
                ticket.getEntryTime().toLocalDateTime().format(formatter),
                ticket.getStatus());
    }

    private void refreshParkingOverview() {
        if (floorTabs != null) {
            remove(floorTabs);
        }

        selectedSpot = null;
        selectedSpotButton = null;
        updateSelectedSpotLabel();
        parkingController = new ParkingController();
        createParkingOverviewPanel();
        revalidate();
        repaint();
    }

    private void updateSelectedSpotLabel() {
        if (selectedSpot == null) {
            selectedSpotLabel.setText("Selected Spot: None");
            return;
        }

        selectedSpotLabel.setText("Selected Spot: " + selectedSpot.getSpotCode() + " | " + selectedSpot.getType());
    }
}
