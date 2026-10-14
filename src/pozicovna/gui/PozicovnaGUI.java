package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Grafické používateľské rozhranie pre aplikáciu Požičovňa lodí.
 * 
 * Táto trieda reprezentuje hlavné okno aplikácie, ktoré zobrazuje tabuľku s prostriedkami,
 * filter panel, ovládacie tlačidlá a menu. Pri štarte sa pokúsi načítať údaje o prostriedkoch 
 * a prenájmoch zo súborov. Ak súbory neexistujú, inicializujú sa predvolené prostriedky.
 * 
 * @author [Tvoje meno]
 */
public class PozicovnaGUI extends JFrame {
    private Pozicovna pozicovna;
    private ProstriedkyTable prostriedkyTable;
    private FilterPanel filterPanel;
    private ButtonPanel buttonPanel;
    private MenuBar menuBar;

    /**
     * Vytvorí nové hlavné GUI pre požičovňu.
     * Inicializuje dáta, komponenty GUI a nastaví rozloženie hlavného okna.
     */
    public PozicovnaGUI() {
        pozicovna = new Pozicovna("Moja Požičovňa GUI", 15);

        // Načítaj prostriedky zo súboru, ak existuje, inak inicializuj prednastavené
        if (Files.exists(Paths.get("prostriedky.csv"))) {
            nacitajProstriedkyZoSuboru();
        } else {
            inicializujProstriedky();
        }

        // Načítaj prenájmy zo súboru, ak existuje
        if (Files.exists(Paths.get("prenajmy.csv"))) {
            nacitajPrenajmyZoSuboru();
        }

        // Nastavenie hlavného okna
        setTitle("Požičovňa Lodí");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Inicializácia komponentov
        prostriedkyTable = new ProstriedkyTable(pozicovna);
        filterPanel = new FilterPanel(this, pozicovna);
        buttonPanel = new ButtonPanel(this, pozicovna, prostriedkyTable);
        menuBar = new MenuBar(this, pozicovna);
        menuBar.setProstriedkyTable(prostriedkyTable);
        filterPanel.setProstriedkyTable(prostriedkyTable);

        // Pridanie komponentov do rozloženia
        add(prostriedkyTable.getScrollPane(), BorderLayout.CENTER);
        add(filterPanel.getPanel(), BorderLayout.NORTH);
        add(buttonPanel.getPanel(), BorderLayout.SOUTH);
        setJMenuBar(menuBar.getMenuBar());

        setVisible(true);
    }

    /**
     * Inicializuje prednastavené prostriedky do požičovne,
     * ak súbor s údajmi neexistuje.
     * Prostriedky sa následne uložia do súboru {@code prostriedky.csv}.
     */
    private void inicializujProstriedky() {
        pozicovna.addProstriedok(new BojovaLod(Motor.BENZIN, 4, 10.0f, 100.0f, 50.0f, Palivo.BENZIN, 50, 50.0f));
        pozicovna.addProstriedok(new BojovaLod(Motor.DIESEL, 6, 12.0f, 120.0f, 60.0f, Palivo.DIESEL, 30, 60.0f));
        pozicovna.addProstriedok(new BojovaLodNaOvladanie(Motor.ELEKTRICKY, 2, 5.0f, 20.0f, 100.0f, 30.0f));
        pozicovna.addProstriedok(new BojovaLodNaOvladanie(Motor.ELEKTRICKY, 1, 4.0f, 15.0f, 80.0f, 25.0f));
        pozicovna.addProstriedok(new VodnySkuter(Velkost.S, BoatType.KANOE, 15.0f, 50.0f, 20.0f, Motor.ELEKTRICKY, 2, 30.0f));
        pozicovna.addProstriedok(new VodnySkuter(Velkost.M, BoatType.KABINOVA, 20.0f, 60.0f, 30.0f, Motor.ELEKTRICKY, 4, 40.0f));
        pozicovna.addProstriedok(new VodnySkuter(Velkost.L, BoatType.RYBARSKA, 25.0f, 70.0f, 35.0f, Motor.ELEKTRICKY, 6, 50.0f));
        pozicovna.addProstriedok(new VyletnaLod(Motor.HYBRID, 6, 12.0f, 1420.0f, 60.0f, Palivo.BENZIN, 50.0f, 100.0f, 25.0f, 60.0f));
        pozicovna.addProstriedok(new VyletnaLod(Motor.HYBRID, 8, 15.0f, 150.0f, 80.0f, Palivo.DIESEL, 60.0f, 100.0f, 30.0f, 70.0f));
        pozicovna.addProstriedok(new VyletnaLod(Motor.HYBRID, 5, 10.0f, 100.0f, 50.0f, Palivo.BENZIN, 40.0f, 100.0f, 20.0f, 55.0f));
        pozicovna.ulozProstriedkyDoSuboru("prostriedky.csv");
    }

    /**
     * Načíta údaje o prostriedkoch zo súboru {@code prostriedky.csv}.
     */
    private void nacitajProstriedkyZoSuboru() {
        pozicovna.nacitajProstriedkyZoSuboru("prostriedky.csv");
    }

    /**
     * Načíta údaje o prenájmoch zo súboru {@code prenajmy.csv}.
     */
    private void nacitajPrenajmyZoSuboru() {
        pozicovna.nacitajPrenajmyZoSuboru("prenajmy.csv");
    }

    /**
     * Spustí aplikáciu Požičovňa GUI v Event Dispatch Thread (EDT).
     * Odporúčaný spôsob spustenia Swing aplikácie.
     * 
     * @param args argumenty príkazového riadku (nepoužité)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PozicovnaGUI::new);
    }
}
