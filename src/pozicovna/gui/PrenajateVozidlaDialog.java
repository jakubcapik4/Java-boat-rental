package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrenajateVozidlaDialog extends JDialog {
    private Pozicovna pozicovna;

    public PrenajateVozidlaDialog(PozicovnaGUI parent, Pozicovna pozicovna) {
        super(parent, "Prenajaté vozidlá", true);
        this.pozicovna = pozicovna;

        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        String[] columns = {"Meno zákazníka", "Názov vozidla", "Dátum prenájmu", "Počet dní", "Celková cena (€)"};
        DefaultTableModel prenajmyModel = new DefaultTableModel(columns, 0);
        JTable prenajmyTable = new JTable(prenajmyModel);
        JScrollPane scrollPane = new JScrollPane(prenajmyTable);
        add(scrollPane, BorderLayout.CENTER);

        for (Prenajom p : pozicovna.getPrenajmy()) {
            Object[] row = {
                p.getMenoZakaznika(),
                p.getVozidlo().getNazov(),
                p.getDatumPrenajmu(),
                p.getPocetDniPrenajmu(),
                p.vypocitajCelkovuCenu()
            };
            prenajmyModel.addRow(row);
        }

        JButton zatvorButton = new JButton("Zatvoriť");
        zatvorButton.addActionListener(e -> dispose());
        add(zatvorButton, BorderLayout.SOUTH);
    }
}