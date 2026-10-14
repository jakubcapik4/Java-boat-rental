package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.awt.*;

public class PridajProstriedokDialog extends JDialog {
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;
    private JComboBox<String> typCombo;
    private JComboBox<String> motorCombo;
    private JComboBox<String> palivoCombo;
    private JComboBox<String> velkostCombo;
    private JComboBox<String> boatTypeCombo;
    private JTextField pocetMiestField;
    private JTextField spotrebaField;
    private JTextField cenaZaDenField;
    private JTextField objemField;
    private JTextField objemNadrzeField;
    private JTextField municiaField;
    private JTextField velkostBaterieField;
    private JTextField dojazdField;
    private JTextField stavBaterieField;
    private JTextField stavNadrzeField;
    private JTextField stavBaterieVyletnaField;

    public PridajProstriedokDialog(PozicovnaGUI parent, Pozicovna pozicovna, ProstriedkyTable prostriedkyTable) {
        super(parent, "Pridať dopravný prostriedok", true);
        this.pozicovna = pozicovna;
        this.prostriedkyTable = prostriedkyTable;

        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 2, 10, 10));

        // Inicializácia komponentov
        JLabel typLabel = new JLabel("Typ prostriedku:");
        typCombo = new JComboBox<>(new String[]{"BojovaLod", "BojovaLodNaOvladanie", "VodnySkuter", "VyletnaLod"});
        JLabel motorLabel = new JLabel("Motor:");
        motorCombo = new JComboBox<>(new String[]{"BENZIN", "DIESEL", "ELEKTRICKY", "HYBRID"});
        JLabel pocetMiestLabel = new JLabel("Počet miest:");
        pocetMiestField = new JTextField();
        JLabel spotrebaLabel = new JLabel("Spotreba (l/100km):");
        spotrebaField = new JTextField();
        JLabel cenaZaDenLabel = new JLabel("Cena za deň (€):");
        cenaZaDenField = new JTextField();
        JLabel objemLabel = new JLabel("Objem (m³):");
        objemField = new JTextField();
        JLabel objemNadrzeLabel = new JLabel("Objem nádrže (l):");
        objemNadrzeField = new JTextField();
        JLabel palivoLabel = new JLabel("Palivo:");
        palivoCombo = new JComboBox<>(new String[]{"BENZIN", "DIESEL"});
        JLabel municiaLabel = new JLabel("Munícia:");
        municiaField = new JTextField();
        JLabel velkostBaterieLabel = new JLabel("Veľkosť batérie (kWh):");
        velkostBaterieField = new JTextField();
        JLabel dojazdLabel = new JLabel("Dojazd (km):");
        dojazdField = new JTextField();
        JLabel velkostLabel = new JLabel("Veľkosť:");
        velkostCombo = new JComboBox<>(new String[]{"S", "M", "L"});
        JLabel boatTypeLabel = new JLabel("Typ lode:");
        boatTypeCombo = new JComboBox<>(new String[]{"KANOE", "KABINOVA", "RYBARSKA"});
        JLabel stavBaterieLabel = new JLabel("Stav batérie (%):");
        stavBaterieField = new JTextField();
        JLabel stavNadrzeLabel = new JLabel("Stav nádrže (l):");
        stavNadrzeField = new JTextField();
        JLabel stavBaterieVyletnaLabel = new JLabel("Stav batérie výletnej lode (%):");
        stavBaterieVyletnaField = new JTextField();

        JButton potvrdButton = new JButton("Potvrdiť");
        JButton zrusButton = new JButton("Zrušiť");

        // Pridanie komponentov do dialógu
        add(typLabel);
        add(typCombo);
        add(motorLabel);
        add(motorCombo);
        add(pocetMiestLabel);
        add(pocetMiestField);
        add(spotrebaLabel);
        add(spotrebaField);
        add(cenaZaDenLabel);
        add(cenaZaDenField);
        add(objemLabel);
        add(objemField);
        add(objemNadrzeLabel);
        add(objemNadrzeField);
        add(palivoLabel);
        add(palivoCombo);
        add(municiaLabel);
        add(municiaField);
        add(velkostBaterieLabel);
        add(velkostBaterieField);
        add(dojazdLabel);
        add(dojazdField);
        add(velkostLabel);
        add(velkostCombo);
        add(boatTypeLabel);
        add(boatTypeCombo);
        add(stavBaterieLabel);
        add(stavBaterieField);
        add(stavNadrzeLabel);
        add(stavNadrzeField);
        add(stavBaterieVyletnaLabel);
        add(stavBaterieVyletnaField);
        add(potvrdButton);
        add(zrusButton);

        // Automatické nastavenie motora pri zmene typu
        typCombo.addActionListener(e -> {
            String typ = (String) typCombo.getSelectedItem();
            if (typ.equals("BojovaLodNaOvladanie") || typ.equals("VodnySkuter")) {
                motorCombo.setSelectedItem("ELEKTRICKY");
                motorCombo.setEnabled(false);
            } else if (typ.equals("VyletnaLod")) {
                motorCombo.setSelectedItem("HYBRID");
                motorCombo.setEnabled(false);
            } else {
                motorCombo.setEnabled(true);
            }
        });

        // Akcia tlačidla Potvrdiť
        potvrdButton.addActionListener(e -> {
            try {
                String typ = (String) typCombo.getSelectedItem();
                DopravnyProstriedok dp = null;

                // Validácia ceny za deň
                if (cenaZaDenField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Cena za deň je povinná!");
                    return;
                }
                float cenaZaDen = Float.parseFloat(cenaZaDenField.getText().trim());
                if (cenaZaDen <= 0) {
                    JOptionPane.showMessageDialog(this, "Cena za deň musí byť kladná!");
                    return;
                }

                if (typ.equals("BojovaLod")) {
                    if (pocetMiestField.getText().trim().isEmpty() || spotrebaField.getText().trim().isEmpty() ||
                        objemField.getText().trim().isEmpty() || objemNadrzeField.getText().trim().isEmpty() ||
                        municiaField.getText().trim().isEmpty() || stavNadrzeField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Všetky polia pre BojovaLod sú povinné!");
                        return;
                    }
                    Motor motor = Motor.valueOf((String) motorCombo.getSelectedItem());
                    int pocetMiest = Integer.parseInt(pocetMiestField.getText().trim());
                    float spotreba = Float.parseFloat(spotrebaField.getText().trim());
                    float objem = Float.parseFloat(objemField.getText().trim());
                    float objemNadrze = Float.parseFloat(objemNadrzeField.getText().trim());
                    float stavNadrze = Float.parseFloat(stavNadrzeField.getText().trim());
                    Palivo palivo = Palivo.valueOf((String) palivoCombo.getSelectedItem());
                    int municia = Integer.parseInt(municiaField.getText().trim());
                    if (pocetMiest <= 0 || spotreba <= 0 || objem <= 0 || objemNadrze <= 0 || stavNadrze < 0 || 
                        stavNadrze > objemNadrze || municia < 0) {
                        JOptionPane.showMessageDialog(this, "Neplatné hodnoty pre BojovaLod!");
                        return;
                    }
                    dp = new BojovaLod(motor, pocetMiest, spotreba, objem, objemNadrze, palivo, municia, cenaZaDen);
                } else if (typ.equals("BojovaLodNaOvladanie")) {
                    if (pocetMiestField.getText().trim().isEmpty() || spotrebaField.getText().trim().isEmpty() ||
                        velkostBaterieField.getText().trim().isEmpty() || dojazdField.getText().trim().isEmpty() ||
                        stavBaterieField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Všetky polia pre BojovaLodNaOvladanie sú povinné!");
                        return;
                    }
                    Motor motor = Motor.ELEKTRICKY;
                    int pocetMiest = Integer.parseInt(pocetMiestField.getText().trim());
                    float spotreba = Float.parseFloat(spotrebaField.getText().trim());
                    float velkostBaterie = Float.parseFloat(velkostBaterieField.getText().trim());
                    float dojazd = Float.parseFloat(dojazdField.getText().trim());
                    float stavBaterie = Float.parseFloat(stavBaterieField.getText().trim());
                    if (pocetMiest <= 0 || spotreba <= 0 || velkostBaterie <= 0 || dojazd <= 0 || stavBaterie < 0 || stavBaterie > 100) {
                        JOptionPane.showMessageDialog(this, "Neplatné hodnoty pre BojovaLodNaOvladanie!");
                        return;
                    }
                    dp = new BojovaLodNaOvladanie(motor, pocetMiest, spotreba, velkostBaterie, dojazd, cenaZaDen);
                } else if (typ.equals("VodnySkuter")) {
                    if (velkostBaterieField.getText().trim().isEmpty() || stavBaterieField.getText().trim().isEmpty() ||
                        pocetMiestField.getText().trim().isEmpty() || dojazdField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Povinné polia pre VodnySkuter!");
                        return;
                    }
                    Velkost velkost = Velkost.valueOf((String) velkostCombo.getSelectedItem());
                    BoatType boatType = BoatType.valueOf((String) boatTypeCombo.getSelectedItem());
                    float velkostBaterie = Float.parseFloat(velkostBaterieField.getText().trim());
                    float stavBaterie = Float.parseFloat(stavBaterieField.getText().trim());
                    int pocetMiest = Integer.parseInt(pocetMiestField.getText().trim());
                    float dojazd = Float.parseFloat(dojazdField.getText().trim());
                    if (velkostBaterie <= 0 || stavBaterie < 0 || stavBaterie > 100 || pocetMiest <= 0 || dojazd <= 0) {
                        JOptionPane.showMessageDialog(this, "Neplatné hodnoty pre VodnySkuter!");
                        return;
                    }
                    dp = new VodnySkuter(velkost, boatType, velkostBaterie, stavBaterie, cenaZaDen, Motor.ELEKTRICKY, pocetMiest, dojazd);
                } else if (typ.equals("VyletnaLod")) {
                    if (pocetMiestField.getText().trim().isEmpty() || spotrebaField.getText().trim().isEmpty() ||
                        objemNadrzeField.getText().trim().isEmpty() || stavNadrzeField.getText().trim().isEmpty() ||
                        velkostBaterieField.getText().trim().isEmpty() || stavBaterieVyletnaField.getText().trim().isEmpty() ||
                        dojazdField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Všetky polia pre VyletnaLod sú povinné!");
                        return;
                    }
                    Motor motor = Motor.HYBRID;
                    int pocetMiest = Integer.parseInt(pocetMiestField.getText().trim());
                    float spotreba = Float.parseFloat(spotrebaField.getText().trim());
                    float objemNadrze = Float.parseFloat(objemNadrzeField.getText().trim());
                    float stavNadrze = Float.parseFloat(stavNadrzeField.getText().trim());
                    Palivo palivo = Palivo.valueOf((String) palivoCombo.getSelectedItem());
                    float velkostBaterie = Float.parseFloat(velkostBaterieField.getText().trim());
                    float stavBaterie = Float.parseFloat(stavBaterieVyletnaField.getText().trim());
                    float dojazd = Float.parseFloat(dojazdField.getText().trim());
                    if (pocetMiest <= 0 || spotreba <= 0 || objemNadrze <= 0 || stavNadrze < 0 || 
                        stavNadrze > objemNadrze || velkostBaterie <= 0 || stavBaterie < 0 || 
                        stavBaterie > 100 || dojazd <= 0) {
                        JOptionPane.showMessageDialog(this, "Neplatné hodnoty pre VyletnaLod!");
                        return;
                    }
                    dp = new VyletnaLod(motor, pocetMiest, spotreba, objemNadrze, stavNadrze, palivo, velkostBaterie, stavBaterie, dojazd, cenaZaDen);
                }

                if (dp != null) {
                    if (pozicovna.addProstriedok(dp)) {
                        pozicovna.ulozProstriedkyDoSuboru("prostriedky.csv");
                        prostriedkyTable.refreshTable();
                        JOptionPane.showMessageDialog(this, "Prostriedok úspešne pridaný!");
                        System.out.println("Pridaný prostriedok: Typ=" + typ + ", ID=" + dp.getId() + ", Názov=" + dp.getNazov());
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Nepodarilo sa pridať prostriedok (možná plná kapacita)!");
                        System.out.println("Zlyhanie pridania prostriedku: Typ=" + typ);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Skontroluj číselné hodnoty!");
                System.out.println("Chyba pri pridávaní prostriedku: Neplatné číslo - " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Chyba: " + ex.getMessage());
                System.out.println("Chyba pri pridávaní prostriedku: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Chyba: " + ex.getMessage());
                System.out.println("Chyba pri pridávaní prostriedku: " + ex.getMessage());
            }
        });

        zrusButton.addActionListener(e -> dispose());
    }
}