package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.awt.*;

public class NabijanieDialog extends JDialog {
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;

    public NabijanieDialog(PozicovnaGUI parent, Pozicovna pozicovna, ProstriedkyTable prostriedkyTable) {
        super(parent, "Nabiť elektrické prostriedky", true);
        this.pozicovna = pozicovna;
        this.prostriedkyTable = prostriedkyTable;

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(2, 2, 10, 10));

        JLabel kwLabel = new JLabel("Energia (kW):");
        JTextField kwField = new JTextField();
        JButton potvrdButton = new JButton("Potvrdiť");
        JButton zrusButton = new JButton("Zrušiť");

        add(kwLabel);
        add(kwField);
        add(potvrdButton);
        add(zrusButton);

        potvrdButton.addActionListener(e -> {
            try {
                float kw = Float.parseFloat(kwField.getText().trim());
                pozicovna.chargeAll(kw);
                JOptionPane.showMessageDialog(this, "Všetky elektrické prostriedky boli nabité.");
                prostriedkyTable.refreshTable();
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Neplatné množstvo energie!");
            }
        });

        zrusButton.addActionListener(e -> dispose());
    }
}