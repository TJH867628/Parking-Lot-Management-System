package view;

import javax.swing.*;

import controller.EntryController;
import controller.ParkingController;
import controller.ExitController;
import model.EntryResult;
import model.ParkingFloor;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;
import model.Iterator.VehicleTypeIterator;
import model.ParkingSpot;
import model.Ticket;
import model.VehicleType;

import java.awt.*;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame {
    private JTabbedPane floorTabs;
    private JButton selectedSpotButton = null;
    private ParkingSpot selectedSpot = null;
    private JLabel selectedSpotLabel;
    private ParkingController parkingController = new ParkingController();
    private EntryController entryController = new EntryController();
    private ExitController exitController = new ExitController();
    private boolean parkingMode = false;
    private int selectedVehicleTypeId;
    private String selectedPlate;
    private boolean selectedHasCard = false;

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
        JButton exitVehicleBtn = new JButton("Exit Parking");
        JButton adminBtn = new JButton("Admin");
        JButton reporButtontBtn = new JButton("Reports");

        parkVehicleBtn.addActionListener(e -> handleParkVehicle());
        exitVehicleBtn.addActionListener(e -> handleExitParking());

        adminBtn.addActionListener(e -> {
            new AdminLoginPanel().setVisible(true);
        });
        reporButtontBtn.addActionListener(e -> {
            new ReportPanel().setVisible(true);
        });
        

        actionPanel.add(parkVehicleBtn);
        actionPanel.add(exitVehicleBtn);
        actionPanel.add(adminBtn);
        actionPanel.add(reporButtontBtn);

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

    private void filterEligibleSpots() {

        FloorIterator floorIterator = parkingController.getFloorIterator();
        int tabIndex = 0;

        while (floorIterator.hasNext()) {

            ParkingFloor floor = floorIterator.next();
            JPanel grid = (JPanel) floorTabs.getComponentAt(tabIndex);

            SpotIterator spotIterator = parkingController.getSpotIteratorByFloor(floor.getFloorId());

            int componentIndex = 0;

            while (spotIterator.hasNext()) {

                ParkingSpot spot = spotIterator.next();

                JPanel spotPanel = (JPanel) grid.getComponent(componentIndex);
                JButton btn = (JButton) spotPanel.getComponent(1);

                boolean eligible = false;

                if (!spot.isAvailable()) {
                    btn.setEnabled(false);
                    btn.setBackground(Color.LIGHT_GRAY);
                    componentIndex++;
                    continue;
                }

                eligible = parkingController.isSpotEligibleForVehicle(spot.getSpotTypeId(),selectedVehicleTypeId);

                btn.setEnabled(eligible);

                if (eligible) {
                    btn.setBackground(Color.GREEN);
                } else {
                    btn.setBackground(Color.LIGHT_GRAY);
                }

                componentIndex++;
            }

            tabIndex++;
        }
    }

    private void selectSpot(JButton btn, ParkingSpot spot) {

        selectedSpot = spot;

        if (parkingMode && spot.isAvailable()) {

            EntryResult result = entryController.registerEntry(
                    selectedPlate,
                    selectedVehicleTypeId,
                    selectedHasCard,
                    spot.getId());

            if (!result.isSuccess()) {
                JOptionPane.showMessageDialog(this, result.getMessage());
                return;
            }

            JOptionPane.showMessageDialog(this,
                    buildTicketMessage(result.getTicket()));

            parkingMode = false;
            selectedVehicleTypeId = -1;
            selectedPlate = null;

            refreshParkingOverview();
            return;
        }

        updateSelectedSpotLabel();
    }

    private void handleParkVehicle() {

        JTextField plateField = new JTextField();
        JComboBox<VehicleType> vehicleTypeCombo = new JComboBox<>();

        VehicleTypeIterator vehicleTypes = entryController.getVehicleTypes();
        while (vehicleTypes.hasNext()) {
            vehicleTypeCombo.addItem(vehicleTypes.next());
        }

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.add(new JLabel("License Plate:"));
        panel.add(plateField);
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(vehicleTypeCombo);
        panel.add(new JLabel("Handicapped Card:"));
        JCheckBox handicappedCardCheck = new JCheckBox();
        panel.add(handicappedCardCheck);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Enter Vehicle Information",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        if(vehicleTypeCombo.getSelectedItem() == null){
            JOptionPane.showMessageDialog(this, "Please select a vehicle type.");
            return;
        }

        if(entryController.hasActiveParking(plateField.getText().trim())){
            JOptionPane.showMessageDialog(this, "This vehicle already has an active parking session.");
            return;
        }

        VehicleType selectedType = (VehicleType) vehicleTypeCombo.getSelectedItem();
        if (selectedType == null) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle type.");
            return;
        }

        String licensePlate = plateField.getText().trim();
        if (licensePlate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter license plate.");
            return;
        }

        this.selectedVehicleTypeId = selectedType.getId();
        this.selectedPlate = licensePlate;
        this.selectedHasCard = handicappedCardCheck.isSelected();

        this.parkingMode = true;

        filterEligibleSpots();

        JOptionPane.showMessageDialog(this, "Please select an eligible available spot.");
    }

    private void handleExitParking() {
        JTextField plateField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.add(new JLabel("Enter License Plate:"));
        panel.add(plateField);

        int option = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Vehicle Exit",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        parkingController.checkAndGenerateFines();

        String plate = plateField.getText().trim();

        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a license plate.");
            return;
        }

        String summary = exitController.calculateExit(plate);

        if (summary.contains("No active parking")) {
            JOptionPane.showMessageDialog(this, summary);
            return;
        }

        String[] options = { "Cash", "Card" };
        String method = (String) JOptionPane.showInputDialog(
                this,
                summary + "\n\nSelect Payment Method:",
                "Payment",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (method == null)
            return;

        double finePaid = exitController.processPayment(plate, method);
        boolean success = finePaid >= 0;

        if (success) {

            String receipt = exitController.buildReceipt(plate, method, finePaid);

            JTextArea textArea = new JTextArea(receipt);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);
            textArea.setCaretPosition(0);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Payment Receipt",
                    JOptionPane.INFORMATION_MESSAGE);

            refreshParkingOverview();

        } else {
            JOptionPane.showMessageDialog(this, "Payment failed!");
        }
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
