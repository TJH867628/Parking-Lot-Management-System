package view;

import controller.EntryController;
import model.EntryResult;
import model.EntrySpot;
import model.Ticket;
import model.VehicleType;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EntryPanel extends JPanel {
    private EntryController entryController = new EntryController();

    private JTextField plateField;
    private JComboBox<VehicleType> vehicleTypeCombo;
    private JCheckBox handicappedCardCheck;
    private JComboBox<EntrySpot> spotCombo;
    private JTextArea ticketArea;

    public EntryPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTicketPanel(), BorderLayout.CENTER);
        loadVehicleTypes();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        plateField = new JTextField();
        vehicleTypeCombo = new JComboBox<>();
        handicappedCardCheck = new JCheckBox("Has handicapped card");
        spotCombo = new JComboBox<>();

        JButton searchSpotBtn = new JButton("Search Suitable Spots");
        JButton parkBtn = new JButton("Park Vehicle");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("License Plate:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(plateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Vehicle Type:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(vehicleTypeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(handicappedCardCheck, gbc);

        gbc.gridy = 3;
        panel.add(searchSpotBtn, gbc);

        gbc.gridy = 4;
        panel.add(spotCombo, gbc);

        gbc.gridy = 5;
        panel.add(parkBtn, gbc);

        searchSpotBtn.addActionListener(e -> loadSuitableSpots());
        parkBtn.addActionListener(e -> parkVehicle());

        return panel;
    }

    private JScrollPane createTicketPanel() {
        ticketArea = new JTextArea();
        ticketArea.setEditable(false);
        ticketArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        ticketArea.setText("Ticket details will appear here.");
        return new JScrollPane(ticketArea);
    }

    private void loadVehicleTypes() {
        vehicleTypeCombo.removeAllItems();
        List<VehicleType> vehicleTypes = entryController.getVehicleTypes();
        for (VehicleType vehicleType : vehicleTypes) {
            vehicleTypeCombo.addItem(vehicleType);
        }
    }

    private void loadSuitableSpots() {
        VehicleType selectedType = (VehicleType) vehicleTypeCombo.getSelectedItem();
        if (selectedType == null) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle type.");
            return;
        }

        spotCombo.removeAllItems();
        List<EntrySpot> spots = entryController.getAvailableSpots(selectedType.getId());
        for (EntrySpot spot : spots) {
            spotCombo.addItem(spot);
        }

        if (spots.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No suitable available spots found.");
        }
    }

    private void parkVehicle() {
        VehicleType selectedType = (VehicleType) vehicleTypeCombo.getSelectedItem();
        EntrySpot selectedSpot = (EntrySpot) spotCombo.getSelectedItem();

        if (selectedType == null) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle type.");
            return;
        }

        if (selectedSpot == null) {
            JOptionPane.showMessageDialog(this, "Please search and select an available spot.");
            return;
        }

        String plate = plateField.getText();
        boolean hasCard = handicappedCardCheck.isSelected();

        EntryResult result = entryController.registerEntry(plate, selectedType.getId(), hasCard, selectedSpot.getSpotId());
        if (!result.isSuccess()) {
            JOptionPane.showMessageDialog(this, result.getMessage(), "Entry Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, result.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
        renderTicket(result.getTicket(), plate);
        loadSuitableSpots();
    }

    private void renderTicket(Ticket ticket, String rawPlate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String plate = rawPlate == null ? "" : rawPlate.trim().toUpperCase();

        String ticketText = """
                ===== Parking Ticket =====
                Ticket No  : %s
                Plate No   : %s
                Spot       : %s
                Entry Time : %s
                Status     : %s
                ==========================
                """
                .formatted(
                        ticket.getTicketCode(),
                        plate,
                        ticket.getSpotCode(),
                        ticket.getEntryTime().toLocalDateTime().format(formatter),
                        ticket.getStatus().toUpperCase());

        ticketArea.setText(ticketText);
    }
}
