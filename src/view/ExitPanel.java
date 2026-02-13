package view;

import controller.ExitController;

import javax.swing.*;
import java.awt.*;

public class ExitPanel extends JPanel {

    private JTextField plateField;
    private JTextArea resultArea;
    private JComboBox<String> paymentMethod;

    private ExitController exitController = new ExitController();

    public ExitPanel() {
        setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("License Plate:"));

        plateField = new JTextField(15);
        topPanel.add(plateField);

        JButton checkBtn = new JButton("Check");
        topPanel.add(checkBtn);

        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER =====
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // ===== BOTTOM =====
        JPanel bottomPanel = new JPanel();

        paymentMethod = new JComboBox<>(new String[] { "Cash", "Card" });
        bottomPanel.add(new JLabel("Payment:"));
        bottomPanel.add(paymentMethod);

        JButton payBtn = new JButton("Pay & Exit");
        bottomPanel.add(payBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        checkBtn.addActionListener(e -> handleCheck());
        payBtn.addActionListener(e -> handlePayment());
    }

    private void handleCheck() {
        String plate = plateField.getText().trim();

        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter plate number!");
            return;
        }

        String result = exitController.calculateExit(plate);
        resultArea.setText(result);
    }

    private void handlePayment() {
        String plate = plateField.getText().trim();
        String method = (String) paymentMethod.getSelectedItem();

        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter plate number!");
            return;
        }

        double finePaid = exitController.processPayment(plate, method);
        boolean success = finePaid >= 0;

        if (success) {

            String receipt = exitController.buildReceipt(plate, method, finePaid);

            JTextArea textArea = new JTextArea(receipt);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Payment Receipt",
                    JOptionPane.INFORMATION_MESSAGE);

            resultArea.setText("");
            plateField.setText("");

        } else {
            JOptionPane.showMessageDialog(this, "Payment failed!");
        }
    }

}
