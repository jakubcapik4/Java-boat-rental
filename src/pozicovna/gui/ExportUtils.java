package pozicovna.gui;

import pozicovna.*;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExportUtils {
    public static void exportProstriedky(Pozicovna pozicovna, PozicovnaGUI gui) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("export_prostriedky.csv"))) {
            writer.write("ID,Názov,Typ,Motor,Miest,Spotreba,Objem,Palivo,Nádrž,Munícia,Batéria,Stav batérie,Dojazd,Veľkosť,Typ lode,Cena za deň\n");
            for (DopravnyProstriedok dp : pozicovna.getDProstriedky()) {
                if (dp != null) {
                    StringBuilder row = new StringBuilder();
                    row.append(dp.getId()).append(",");
                    row.append(dp.getNazov()).append(",");
                    String typ = dp.getClass().getSimpleName();
                    row.append(typ).append(",");

                    if (dp instanceof BojovaLod bojovaLod) {
                        row.append(bojovaLod.getMotor()).append(",");
                        row.append(bojovaLod.getPocetMiest()).append(",");
                        row.append(bojovaLod.getSpotreba()).append(",");
                        row.append(bojovaLod.getObjem()).append(",");
                        row.append(bojovaLod.getPalivo()).append(",");
                        row.append(bojovaLod.getStavNadrze()).append("/").append(bojovaLod.getObjemNadrze()).append(",");
                        row.append(bojovaLod.getMunicia()).append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                    } else if (dp instanceof BojovaLodNaOvladanie toyBoat) {
                        row.append(toyBoat.getMotor()).append(",");
                        row.append(toyBoat.getPocetMiest()).append(",");
                        row.append(toyBoat.getSpotreba()).append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append(toyBoat.getVelkostBaterie()).append(",");
                        row.append(toyBoat.getStavBaterie()).append(",");
                        row.append(toyBoat.getDojazd()).append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                    } else if (dp instanceof VodnySkuter skuter) {
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                        row.append(skuter.getVelkostBaterie()).append(",");
                        row.append(skuter.getStavBaterie()).append(",");
                        row.append("-").append(",");
                        row.append(skuter.getVelkost()).append(",");
                        row.append(skuter.getTyp()).append(",");
                    } else if (dp instanceof VyletnaLod vyletnaLod) {
                        row.append(vyletnaLod.getMotor()).append(",");
                        row.append(vyletnaLod.getPocetMiest()).append(",");
                        row.append(vyletnaLod.getSpotreba()).append(",");
                        row.append("-").append(",");
                        row.append(vyletnaLod.getTypPaliva()).append(",");
                        row.append(vyletnaLod.getStavNadrze()).append("/").append(vyletnaLod.getObjemNadrze()).append(",");
                        row.append("-").append(",");
                        row.append(vyletnaLod.getVelkostBaterie()).append(",");
                        row.append(vyletnaLod.getStavBaterie()).append(",");
                        row.append(vyletnaLod.getDojazd()).append(",");
                        row.append("-").append(",");
                        row.append("-").append(",");
                    }
                    row.append(dp.getCenaZaDen());
                    writer.write(row.toString());
                    writer.newLine();
                }
            }
            JOptionPane.showMessageDialog(gui, "Prostriedky boli exportované do export_prostriedky.csv");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Chyba pri exporte prostriedkov: " + ex.getMessage());
        }
    }

    public static void exportPrenajmy(Pozicovna pozicovna, PozicovnaGUI gui) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("export_prenajmy.csv"))) {
            writer.write("Meno zákazníka,Názov vozidla,Dátum prenájmu,Počet dní,Celková cena\n");
            for (Prenajom p : pozicovna.getPrenajmy()) {
                writer.write(String.format("%s,%s,%s,%d,%.2f\n",
                    p.getMenoZakaznika(),
                    p.getVozidlo().getNazov(),
                    p.getDatumPrenajmu(),
                    p.getPocetDniPrenajmu(),
                    p.vypocitajCelkovuCenu()));
            }
            JOptionPane.showMessageDialog(gui, "Prenájmy boli exportované do export_prenajmy.csv");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(gui, "Chyba pri exporte prenájmov: " + ex.getMessage());
        }
    }
}