package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.awt.*;

public class FilterPanel {
    private JPanel panel;
    private JComboBox<String> typFilterCombo;
    private JTextField minCenaField;
    private JTextField maxCenaField;
    private Pozicovna pozicovna;
    private PozicovnaGUI gui;
    private ProstriedkyTable prostriedkyTable;

    public FilterPanel(PozicovnaGUI gui, Pozicovna pozicovna) {
        this.gui = gui;
        this.pozicovna = pozicovna;
        this.prostriedkyTable = null; // Bude nastavené neskôr, ak je potrebné

        panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Typ prostriedku:"));
        typFilterCombo = new JComboBox<>(new String[]{"Všetky", "BojovaLod", "BojovaLodNaOvladanie", "VodnySkuter", "VyletnaLod"});
        panel.add(typFilterCombo);
        panel.add(new JLabel("Min. cena (€):"));
        minCenaField = new JTextField(5);
        panel.add(minCenaField);
        panel.add(new JLabel("Max. cena (€):"));
        maxCenaField = new JTextField(5);
        panel.add(maxCenaField);
        JButton filterButton = new JButton("Filtrovat");
        panel.add(filterButton);

        filterButton.addActionListener(e -> applyFilter());
    }

    public void setProstriedkyTable(ProstriedkyTable prostriedkyTable) {
        this.prostriedkyTable = prostriedkyTable;
    }

    public JPanel getPanel() {
        return panel;
    }

    private void applyFilter() {
        try {
            String selectedTyp = (String) typFilterCombo.getSelectedItem();
            float minCena = minCenaField.getText().isEmpty() ? 0 : Float.parseFloat(minCenaField.getText().trim());
            float maxCena = maxCenaField.getText().isEmpty() ? Float.MAX_VALUE : Float.parseFloat(maxCenaField.getText().trim());

            if (prostriedkyTable != null) {
                prostriedkyTable.getTableModel().setRowCount(0);
                DopravnyProstriedok[] prostriedky = pozicovna.getAllFreeProstriedky();
                if (prostriedky != null) {
                    for (DopravnyProstriedok dp : prostriedky) {
                        String typ = dp.getClass().getSimpleName();
                        float cena = dp.getCenaZaDen();

                        if ((selectedTyp.equals("Všetky") || typ.equals(selectedTyp)) &&
                            cena >= minCena && cena <= maxCena) {
                            Object[] row = new Object[16];
                            row[0] = dp.getId();
                            row[1] = dp.getNazov();
                            row[15] = cena;
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
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui, "Neplatná hodnota ceny!");
        }
    }
}