package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class VytvorPrenajomDialog extends JDialog {
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;

    public VytvorPrenajomDialog(PozicovnaGUI parent, Pozicovna pozicovna, ProstriedkyTable prostriedkyTable) {
        super(parent, "Vytvoriť prenájom", true);
        this.pozicovna = pozicovna;
        this.prostriedkyTable = prostriedkyTable;

        setSize(1200, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        String[] columns = {
            "ID", "Názov", "Typ", "Motor", "Miest", "Spotreba (l/100km)", "Objem (m³)", "Palivo",
            "Nádrž (l)", "Munícia", "Batéria (kWh)", "Stav batérie (%)", "Dojazd (km)",
            "Veľkosť", "Typ lode", "Cena za deň (€)"
        };
        DefaultTableModel vozidlaModel = new DefaultTableModel(columns, 0);
        JTable vozidlaTable = new JTable(vozidlaModel);
        JScrollPane scrollPane = new JScrollPane(vozidlaTable);
        add(scrollPane, BorderLayout.CENTER);

        try {
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
                    vozidlaModel.addRow(row);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Chyba pri načítavaní vozidiel: " + ex.getMessage());
        }

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel idLabel = new JLabel("ID vozidla:");
        JTextField idField = new JTextField();
        JLabel menoLabel = new JLabel("Meno zákazníka:");
        JTextField menoField = new JTextField();
        JLabel datumLabel = new JLabel("Dátum (RRRR-MM-DD):");
        JTextField datumField = new JTextField();
        JLabel dniLabel = new JLabel("Počet dní:");
        JTextField dniField = new JTextField();
        JButton potvrdButton = new JButton("Potvrdiť");
        JButton zrusButton = new JButton("Zrušiť");

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(menoLabel);
        formPanel.add(menoField);
        formPanel.add(datumLabel);
        formPanel.add(datumField);
        formPanel.add(dniLabel);
        formPanel.add(dniField);
        formPanel.add(potvrdButton);
        formPanel.add(zrusButton);

        add(formPanel, BorderLayout.SOUTH);

        potvrdButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String meno = menoField.getText().trim();
                String datumStr = datumField.getText().trim();
                int dni = Integer.parseInt(dniField.getText().trim());

                if (meno.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Meno zákazníka nesmie byť prázdne!");
                    return;
                }
                if (dni <= 0) {
                    JOptionPane.showMessageDialog(this, "Počet dní musí byť kladný!");
                    return;
                }

                LocalDate datum;
                try {
                    datum = LocalDate.parse(datumStr);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Neplatný formát dátumu (RRRR-MM-DD)!");
                    return;
                }

                if (datum.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "Dátum prenájmu nesmie byť v minulosti!");
                    return;
                }

                DopravnyProstriedok vozidlo = pozicovna.najdiProstriedok(id);
                if (vozidlo == null || vozidlo.getPozicane()) {
                    JOptionPane.showMessageDialog(this, "Vozidlo nie je dostupné!");
                    return;
                }

                LocalDate koniecPrenajmu = datum.plusDays(dni);
                for (Prenajom p : pozicovna.getPrenajmy()) {
                    if (p.getVozidlo().getId() == id) {
                        LocalDate zaciatokExistujuceho = LocalDate.parse(p.getDatumPrenajmu());
                        LocalDate koniecExistujuceho = zaciatokExistujuceho.plusDays(p.getPocetDniPrenajmu());
                        if (!(koniecPrenajmu.isBefore(zaciatokExistujuceho) || datum.isAfter(koniecExistujuceho))) {
                            JOptionPane.showMessageDialog(this, "Vozidlo je už požičané v zadanom období!");
                            return;
                        }
                    }
                }

                System.out.println("Vytváram prenájom pre vozidlo: ID=" + id + ", Názov=" + vozidlo.getNazov());
                Prenajom prenajom = new Prenajom(vozidlo, meno, datumStr, dni);
                if (!vozidlo.pozicaj()) {
                    JOptionPane.showMessageDialog(this, "Nepodarilo sa prenajať vozidlo!");
                    return;
                }
                pozicovna.getPrenajmy().add(prenajom);
                pozicovna.ulozPrenajmyDoSuboru("prenajmy.csv");
                if (pozicovna.generujZmluvu(prenajom)) {
                    JOptionPane.showMessageDialog(this, "Prenájom a zmluva úspešne vytvorené!");
                } else {
                    JOptionPane.showMessageDialog(this, "Prenájom vytvorený, ale zmluvu sa nepodarilo vygenerovať!");
                }
                prostriedkyTable.refreshTable();
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Neplatné ID alebo počet dní!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Chyba pri vytváraní prenájmu: " + ex.getMessage());
            }
        });

        zrusButton.addActionListener(e -> dispose());
    }
}