package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.awt.*;

public class TankovanieDialog extends JDialog {
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;

    public TankovanieDialog(PozicovnaGUI parent, Pozicovna pozicovna, ProstriedkyTable prostriedkyTable) {
        super(parent, "Tankovať spaľovacie prostriedky", true);
        this.pozicovna = pozicovna;
        this.prostriedkyTable = prostriedkyTable;

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(2, 2, 10, 10));

        JLabel palivoLabel = new JLabel("Palivo (BENZIN/DIESEL):");
        JTextField palivoField = new JTextField();
        JButton potvrdButton = new JButton("Potvrdiť");
        JButton zrusButton = new JButton("Zrušiť");

        add(palivoLabel);
        add(palivoField);
        add(potvrdButton);
        add(zrusButton);

        potvrdButton.addActionListener(e -> {
            try {
                Palivo palivo = Palivo.valueOf(palivoField.getText().trim().toUpperCase());
                pozicovna.refuelAll(palivo);
                JOptionPane.showMessageDialog(this, "Všetky spaľovacie prostriedky boli natankované.");
                prostriedkyTable.refreshTable();
                dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Neplatný typ paliva!");
            }
        });

        zrusButton.addActionListener(e -> dispose());
    }
}