package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.awt.*;

public class ButtonPanel {
    private JPanel panel;
    private PozicovnaGUI gui;
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;

    public ButtonPanel(PozicovnaGUI gui, Pozicovna pozicovna, ProstriedkyTable prostriedkyTable) {
        this.gui = gui;
        this.pozicovna = pozicovna;
        this.prostriedkyTable = prostriedkyTable;

        panel = new JPanel(new FlowLayout());
        JButton pridajProstriedokButton = new JButton("Pridať prostriedok");
        JButton vytvorPrenajomButton = new JButton("Vytvoriť prenájom");
        JButton vratVozidloButton = new JButton("Vrátiť vozidlo");
        JButton zobrazPrenajateVozidlaButton = new JButton("Zobraziť prenajaté vozidlá");
        JButton odstranVozidloButton = new JButton("Odstrániť vozidlo");

        panel.add(pridajProstriedokButton);
        panel.add(vytvorPrenajomButton);
        panel.add(vratVozidloButton);
        panel.add(zobrazPrenajateVozidlaButton);
        panel.add(odstranVozidloButton);

        pridajProstriedokButton.addActionListener(e -> new PridajProstriedokDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        vytvorPrenajomButton.addActionListener(e -> new VytvorPrenajomDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        vratVozidloButton.addActionListener(e -> new VratVozidloDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        zobrazPrenajateVozidlaButton.addActionListener(e -> new PrenajateVozidlaDialog(gui, pozicovna).setVisible(true));

        odstranVozidloButton.addActionListener(e -> {
            int selectedRow = prostriedkyTable.getTable().getSelectedRow();
            if (selectedRow != -1) {
                DopravnyProstriedok dp = prostriedkyTable.getSelectedProstriedok();
                if (dp != null) {
                    System.out.println("Pokúšam sa odstrániť prostriedok: " + dp.getNazov() + ", ID: " + dp.getId() + ", Prenajaté: " + dp.getPozicane());
                    if (pozicovna.removeProstriedok(dp)) {
                        pozicovna.ulozProstriedkyDoSuboru("prostriedky.csv");
                        prostriedkyTable.refreshTable();
                        JOptionPane.showMessageDialog(gui, "Vozidlo bolo úspešne odstránené!");
                    } else {
                        JOptionPane.showMessageDialog(gui, "Nepodarilo sa odstrániť vozidlo (skontroluj, či nie je prenajaté)!");
                        System.out.println("Odstránenie zlyhalo: Prenajaté=" + dp.getPozicane());
                    }
                } else {
                    JOptionPane.showMessageDialog(gui, "Prosím, vyber vozidlo na odstránenie!");
                }
            } else {
                JOptionPane.showMessageDialog(gui, "Prosím, vyber vozidlo na odstránenie!");
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}