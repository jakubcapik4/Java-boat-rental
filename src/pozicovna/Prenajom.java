package pozicovna;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Trieda reprezentujúca prenájom dopravného prostriedku v požičovni.
 * Obsahuje informácie o prenajatom vozidle, zákazníkovi, dátume prenájmu
 * a počte dní prenájmu. Umožňuje výpočet celkovej ceny prenájmu.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public class Prenajom {

    private DopravnyProstriedok vozidlo;
    private String menoZakaznika;
    private LocalDate datumPrenajmu;
    private int pocetDniPrenajmu;

    /**
     * Vytvorí novú inštanciu prenájmu s danými parametrami.
     *
     * @param vozidlo Dopravný prostriedok na prenájom ({@link DopravnyProstriedok}).
     * @param menoZakaznika Meno zákazníka (nesmie byť null alebo prázdne).
     * @param datumPrenajmu Dátum prenájmu vo formáte "RRRR-MM-DD".
     * @param pocetDniPrenajmu Počet dní prenájmu (musí byť kladný).
     * @throws IllegalArgumentException Ak je vozidlo null, meno prázdne, dátum neplatný,
     *                                 alebo počet dní záporný.
     */
    public Prenajom(DopravnyProstriedok vozidlo, String menoZakaznika, String datumPrenajmu, int pocetDniPrenajmu) {
        if (vozidlo == null || menoZakaznika == null || menoZakaznika.trim().isEmpty()) {
            throw new IllegalArgumentException("Vozidlo a meno zákazníka musia byť zadané!");
        }
        this.vozidlo = vozidlo;
        this.menoZakaznika = menoZakaznika.trim();
        try {
            this.datumPrenajmu = LocalDate.parse(datumPrenajmu);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Neplatný formát dátumu (RRRR-MM-DD): " + datumPrenajmu);
        }
        if (pocetDniPrenajmu <= 0) {
            throw new IllegalArgumentException("Počet dní prenájmu musí byť kladný!");
        }
        this.pocetDniPrenajmu = pocetDniPrenajmu;
    }

    /**
     * Vracia prenajaté vozidlo.
     *
     * @return Dopravný prostriedok ({@link DopravnyProstriedok}).
     */
    public DopravnyProstriedok getVozidlo() {
        return vozidlo;
    }

    /**
     * Nastaví prenajaté vozidlo.
     *
     * @param vozidlo Nový dopravný prostriedok ({@link DopravnyProstriedok}).
     * @throws IllegalArgumentException Ak je vozidlo null.
     */
    public void setVozidlo(DopravnyProstriedok vozidlo) {
        if (vozidlo == null) {
            throw new IllegalArgumentException("Vozidlo nesmie byť null!");
        }
        this.vozidlo = vozidlo;
    }

    /**
     * Vracia meno zákazníka.
     *
     * @return Meno zákazníka.
     */
    public String getMenoZakaznika() {
        return menoZakaznika;
    }

    /**
     * Nastaví meno zákazníka.
     *
     * @param menoZakaznika Nové meno zákazníka (nesmie byť null alebo prázdne).
     * @throws IllegalArgumentException Ak je meno null alebo prázdne.
     */
    public void setMenoZakaznika(String menoZakaznika) {
        if (menoZakaznika == null || menoZakaznika.trim().isEmpty()) {
            throw new IllegalArgumentException("Meno zákazníka musí byť zadané!");
        }
        this.menoZakaznika = menoZakaznika.trim();
    }

    /**
     * Vracia dátum prenájmu vo formáte "RRRR-MM-DD".
     *
     * @return Dátum prenájmu ako reťazec.
     */
    public String getDatumPrenajmu() {
        return datumPrenajmu.toString();
    }

    /**
     * Nastaví dátum prenájmu.
     *
     * @param datumPrenajmu Nový dátum prenájmu vo formáte "RRRR-MM-DD".
     * @throws IllegalArgumentException Ak je formát dátumu neplatný.
     */
    public void setDatumPrenajmu(String datumPrenajmu) {
        try {
            this.datumPrenajmu = LocalDate.parse(datumPrenajmu);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Neplatný formát dátumu (RRRR-MM-DD): " + datumPrenajmu);
        }
    }

    /**
     * Vracia počet dní prenájmu.
     *
     * @return Počet dní prenájmu.
     */
    public int getPocetDniPrenajmu() {
        return pocetDniPrenajmu;
    }

    /**
     * Nastaví počet dní prenájmu.
     *
     * @param pocetDniPrenajmu Nový počet dní prenájmu (musí byť kladný).
     * @throws IllegalArgumentException Ak je počet dní záporný alebo nula.
     */
    public void setPocetDniPrenajmu(int pocetDniPrenajmu) {
        if (pocetDniPrenajmu <= 0) {
            throw new IllegalArgumentException("Počet dní prenájmu musí byť kladný!");
        }
        this.pocetDniPrenajmu = pocetDniPrenajmu;
    }

    /**
     * Vypočíta celkovú cenu prenájmu na základe ceny za deň a počtu dní.
     *
     * @return Celková cena prenájmu v eurách.
     */
    public float vypocitajCelkovuCenu() {
        return vozidlo.getCenaZaDen() * pocetDniPrenajmu;
    }

    /**
     * Vracia textovú reprezentáciu prenájmu vrátane detailov o vozidle, zákazníkovi,
     * dátume, počte dní a cene. Ak je vozidlo typu {@link Lod}, pridáva informáciu o spotrebe.
     *
     * @return Reťazec s detailmi prenájmu.
     */
    @Override
    public String toString() {
        String spotrebaInfo = vozidlo instanceof Lod ? ", Spotreba: " + ((Lod) vozidlo).getSpotreba() + " l/100km" : "";
        return "Prenajom:\n" +
               "  Vozidlo: " + vozidlo.getNazov() + " (ID: " + vozidlo.getId() + ")\n" +
               "  Meno zákazníka: " + menoZakaznika + "\n" +
               "  Dátum prenájmu: " + datumPrenajmu + "\n" +
               "  Počet dní: " + pocetDniPrenajmu + "\n" +
               "  Celková cena: " + vypocitajCelkovuCenu() + "€" + spotrebaInfo + "\n";
    }
}