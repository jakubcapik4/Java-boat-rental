package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProstriedkyTable {
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private Pozicovna pozicovna;

    public ProstriedkyTable(Pozicovna pozicovna) {
        this.pozicovna = pozicovna;

        String[] columns = {
            "ID", "Názov", "Typ", "Motor", "Miest", "Spotreba (l/100km)", "Objem (m³)", "Palivo",
            "Nádrž (l)", "Munícia", "Batéria (kWh)", "Stav batérie (%)", "Dojazd (km)",
            "Veľkosť", "Typ lode", "Cena za deň (€)"
        };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        refreshTable();
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public DopravnyProstriedok getSelectedProstriedok() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            return pozicovna.najdiProstriedok(id);
        }
        return null;
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        DopravnyProstriedok[] prostriedky = pozicovna.getAllFreeProstriedky();
        if (prostriedky != null) {
            for (DopravnyProstriedok dp : prostriedky) {
                Object[] row = new Object[16];
                row[0] = dp.getId();
                row[1] = dp.getNazov();
                row[15] = dp.getCenaZaDen();
                String typ = dp.getClass().getSimpleName();
                row[2] = typ;

                if (dp instanceof BojovaLod bojovaLod) {
                    row[3] = bojovaLod.getMotor();
                    row[4] = bojovaLod.getPocetMiest() != 0 ? bojovaLod.getPocetMiest() : "-";
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
                    row[4] = toyBoat.getPocetMiest() != 0 ? toyBoat.getPocetMiest() : "-";
                    row[5] = toyBoat.getSpotreba();
                    row[6] = "-";
                    row[7] = "-";
                    row[8] = "-";
                    row[9] = "-";
                    row[10] = toyBoat.getVelkostBaterie();
                    row[11] = toyBoat.getStavBaterie();
                    row[12] = toyBoat.getDojazd() != 0 ? toyBoat.getDojazd() : "-";
                    row[13] = "-";
                    row[14] = "-";
                } else if (dp instanceof VodnySkuter skuter) {
                    row[3] = skuter.getMotor();
                    row[4] = skuter.getPocetMiest();
                    row[5] = "-";
                    row[6] = "-";
                    row[7] = "-";
                    row[8] = "-";
                    row[9] = "-";
                    row[10] = skuter.getVelkostBaterie();
                    row[11] = skuter.getStavBaterie();
                    row[12] = skuter.getDojazd();
                    row[13] = skuter.getVelkost();
                    row[14] = skuter.getTyp();
                } else if (dp instanceof VyletnaLod vyletnaLod) {
                    row[3] = vyletnaLod.getMotor();
                    row[4] = vyletnaLod.getPocetMiest() != 0 ? vyletnaLod.getPocetMiest() : "-";
                    row[5] = vyletnaLod.getSpotreba();
                    row[6] = "-";
                    row[7] = vyletnaLod.getTypPaliva();
                    row[8] = String.format("%.1f/%.1f", vyletnaLod.getStavNadrze(), vyletnaLod.getObjemNadrze());
                    row[9] = "-";
                    row[10] = vyletnaLod.getVelkostBaterie();
                    row[11] = vyletnaLod.getStavBaterie();
                    row[12] = vyletnaLod.getDojazd() != 0 ? vyletnaLod.getDojazd() : "-";
                    row[13] = "-";
                    row[14] = "-";
                }
                tableModel.addRow(row);
                System.out.println("Zobrazujem prostriedok v ProstriedkyTable: ID=" + dp.getId() + ", Názov=" + dp.getNazov());
            }
        } else {
            System.out.println("Žiadne dostupné prostriedky na zobrazenie v ProstriedkyTable.");
        }
    }
}