package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.awt.*;
import pozicovna.DopravnyProstriedok;

public class ZoradProstriedkyDialog extends JDialog {
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;

    public ZoradProstriedkyDialog(PozicovnaGUI parent, Pozicovna pozicovna, ProstriedkyTable prostriedkyTable) {
        super(parent, "Zoradiť prostriedky", true);
        this.pozicovna = pozicovna;
        this.prostriedkyTable = prostriedkyTable;

        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(2, 2, 10, 10));

        JLabel kriteriumLabel = new JLabel("Kritérium (spotreba/municia/dojazd):");
        JTextField kriteriumField = new JTextField();
        JButton potvrdButton = new JButton("Potvrdiť");
        JButton zrusButton = new JButton("Zrušiť");

        add(kriteriumLabel);
        add(kriteriumField);
        add(potvrdButton);
        add(zrusButton);

        potvrdButton.addActionListener(e -> {
            try {
                String kriterium = kriteriumField.getText().trim();
                DopravnyProstriedok[] zoradeneProstriedky = pozicovna.zoradProstriedky(kriterium);
                if (zoradeneProstriedky != null) {
                    prostriedkyTable.getTableModel().setRowCount(0);
                    for (DopravnyProstriedok dp : zoradeneProstriedky) {
                        Object[] row = new Object[16];
                        row[0] = dp.getId();
                        row[1] = dp.getNazov();
                        row[15] = dp.getCenaZaDen();
                        String typ = dp.getClass().getSimpleName();
                        row[2] = typ;

                        if (dp instanceof BojovaLod bojovaLod) {
                            row[3] = bojovaLod.getMotor();
                            row[4] = bojovaLod.getPocetMiest();
                            row[5] = bojovaLod.getSpotreba();
                            row[6] = bojovaLod.getObjem();
                            row[7] = bojovaLod.getPalivo();
                            row[8] = String.format("%.1f/%.1f", bojovaLod.getStavNadrze(), bojovaLod.getObjemNadrze());
                            row[9] = bojovaLod.getMunicia();
                            row[10] = "-";
                            row[11] = "-";
                            row[12] = "-";
                            row[13] = "-";
                            row[14] = "-";
                        } else if (dp instanceof BojovaLodNaOvladanie toyBoat) {
                            row[3] = toyBoat.getMotor();
                            row[4] = toyBoat.getPocetMiest();
                            row[5] = toyBoat.getSpotreba();
                            row[6] = "-";
                            row[7] = "-";
                            row[8] = "-";
                            row[9] = "-";
                            row[10] = toyBoat.getVelkostBaterie();
                            row[11] = toyBoat.getStavBaterie();
                            row[12] = toyBoat.getDojazd();
                            row[13] = "-";
                            row[14] = "-";
                        } else if (dp instanceof VodnySkuter skuter) {
                            row[3] = "-";
                            row[4] = "-";
                            row[5] = "-";
                            row[6] = "-";
                            row[7] = "-";
                            row[8] = "-";
                            row[9] = "-";
                            row[10] = skuter.getVelkostBaterie();
                            row[11] = skuter.getStavBaterie();
                            row[12] = "-";
                            row[13] = skuter.getVelkost();
                            row[14] = skuter.getTyp();
                        } else if (dp instanceof VyletnaLod vyletnaLod) {
                            row[3] = vyletnaLod.getMotor();
                            row[4] = vyletnaLod.getPocetMiest();
                            row[5] = vyletnaLod.getSpotreba();
                            row[6] = "-";
                            row[7] = vyletnaLod.getTypPaliva();
                            row[8] = String.format("%.1f/%.1f", vyletnaLod.getStavNadrze(), vyletnaLod.getObjemNadrze());
                            row[9] = "-";
                            row[10] = vyletnaLod.getVelkostBaterie();
                            row[11] = vyletnaLod.getStavBaterie();
                            row[12] = vyletnaLod.getDojazd();
                            row[13] = "-";
                            row[14] = "-";
                        }
                        prostriedkyTable.getTableModel().addRow(row);
                    }
                    JOptionPane.showMessageDialog(this, "Prostriedky boli zoradené.");
                } else {
                    JOptionPane.showMessageDialog(this, "Žiadne dostupné prostriedky.");
                }
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Chyba pri zoraďovaní: " + ex.getMessage());
            }
        });

        zrusButton.addActionListener(e -> dispose());
    }
}