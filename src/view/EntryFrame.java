package view;

import javax.swing.*;
import java.awt.*;

public class EntryFrame extends JFrame {

    public EntryFrame() {
        setTitle("Parking Lot - Vehicle Entry");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new EntryPanel(), BorderLayout.CENTER);
    }
}
