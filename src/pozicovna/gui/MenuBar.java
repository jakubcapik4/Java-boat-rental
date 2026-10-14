package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;

public class MenuBar {
    private JMenuBar menuBar;
    private PozicovnaGUI gui;
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;

    public MenuBar(PozicovnaGUI gui, Pozicovna pozicovna) {
        this.gui = gui;
        this.pozicovna = pozicovna;
        this.prostriedkyTable = null; // Bude nastavené neskôr

        menuBar = new JMenuBar();
        JMenu akcieMenu = new JMenu("Akcie");
        JMenuItem pridajProstriedokItem = new JMenuItem("Pridať prostriedok");
        JMenuItem vytvorPrenajomItem = new JMenuItem("Vytvoriť prenájom");
        JMenuItem vratVozidloItem = new JMenuItem("Vrátiť vozidlo");
        JMenuItem zobrazPrenajmyItem = new JMenuItem("Zobraziť prenájmy");
        JMenuItem nabijanieItem = new JMenuItem("Nabiť všetky elektrické prostriedky");
        JMenuItem tankovanieItem = new JMenuItem("Tankovať všetky spaľovacie prostriedky");
        JMenuItem zoradProstriedkyItem = new JMenuItem("Zoradiť prostriedky");
        JMenuItem exportProstriedkyItem = new JMenuItem("Exportovať prostriedky");
        JMenuItem exportPrenajmyItem = new JMenuItem("Exportovať prenájmy");
        JMenuItem zobrazPrenajateVozidlaItem = new JMenuItem("Zobraziť prenajaté vozidlá");
        JMenuItem odstranVozidloItem = new JMenuItem("Odstrániť vozidlo");

        akcieMenu.add(pridajProstriedokItem);
        akcieMenu.add(vytvorPrenajomItem);
        akcieMenu.add(vratVozidloItem);
        akcieMenu.add(zobrazPrenajmyItem);
        akcieMenu.add(zobrazPrenajateVozidlaItem);
        akcieMenu.add(nabijanieItem);
        akcieMenu.add(tankovanieItem);
        akcieMenu.add(zoradProstriedkyItem);
        akcieMenu.add(exportProstriedkyItem);
        akcieMenu.add(exportPrenajmyItem);
        akcieMenu.add(odstranVozidloItem);
        menuBar.add(akcieMenu);

        pridajProstriedokItem.addActionListener(e -> new PridajProstriedokDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        vytvorPrenajomItem.addActionListener(e -> new VytvorPrenajomDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        vratVozidloItem.addActionListener(e -> new VratVozidloDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        zobrazPrenajmyItem.addActionListener(e -> new PrenajateVozidlaDialog(gui, pozicovna).setVisible(true));
        zobrazPrenajateVozidlaItem.addActionListener(e -> new PrenajateVozidlaDialog(gui, pozicovna).setVisible(true));
        nabijanieItem.addActionListener(e -> new NabijanieDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        tankovanieItem.addActionListener(e -> new TankovanieDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        zoradProstriedkyItem.addActionListener(e -> new ZoradProstriedkyDialog(gui, pozicovna, prostriedkyTable).setVisible(true));
        exportProstriedkyItem.addActionListener(e -> ExportUtils.exportProstriedky(pozicovna, gui));
        exportPrenajmyItem.addActionListener(e -> ExportUtils.exportPrenajmy(pozicovna, gui));
        odstranVozidloItem.addActionListener(e -> {
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

    public void setProstriedkyTable(ProstriedkyTable prostriedkyTable) {
        this.prostriedkyTable = prostriedkyTable;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}