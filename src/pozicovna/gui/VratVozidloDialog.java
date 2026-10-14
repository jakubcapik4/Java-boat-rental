package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.awt.*;

public class VratVozidloDialog extends JDialog {
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;

    public VratVozidloDialog(PozicovnaGUI parent, Pozicovna pozicovna, ProstriedkyTable prostriedkyTable) {
        super(parent, "Vrátiť vozidlo", true);
        this.pozicovna = pozicovna;
        this.prostriedkyTable = prostriedkyTable;

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nazovLabel = new JLabel("Názov vozidla:");
        JTextField nazovField = new JTextField();
        JLabel menoLabel = new JLabel("Meno zákazníka:");
        JTextField menoField = new JTextField();
        JButton potvrdButton = new JButton("Potvrdiť");
        JButton zrusButton = new JButton("Zrušiť");

        add(nazovLabel);
        add(nazovField);
        add(menoLabel);
        add(menoField);
        add(potvrdButton);
        add(zrusButton);

        potvrdButton.addActionListener(e -> {
            try {
                String nazovVozidla = nazovField.getText().trim();
                String meno = menoField.getText().trim();

                Prenajom naOdstranenie = null;
                for (Prenajom p : pozicovna.getPrenajmy()) {
                    if (p.getVozidlo().getNazov().equalsIgnoreCase(nazovVozidla) &&
                        p.getMenoZakaznika().equalsIgnoreCase(meno)) {
                        naOdstranenie = p;
                        break;
                    }
                }

                if (naOdstranenie != null) {
                    naOdstranenie.getVozidlo().vratit();
                    pozicovna.getPrenajmy().remove(naOdstranenie);
                    pozicovna.ulozPrenajmyDoSuboru("prenajmy.csv");
                    JOptionPane.showMessageDialog(this, "Vozidlo bolo úspešne vrátené.");
                    prostriedkyTable.refreshTable();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Prenájom nebol nájdený. Skontroluj názov a meno.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Chyba pri vracaní vozidla: " + ex.getMessage());
            }
        });

        zrusButton.addActionListener(e -> dispose());
    }
}