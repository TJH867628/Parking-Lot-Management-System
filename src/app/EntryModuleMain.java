package app;

import javax.swing.SwingUtilities;

import view.EntryFrame;

public class EntryModuleMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EntryFrame frame = new EntryFrame();
            frame.setVisible(true);
        });
    }
}
