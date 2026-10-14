package pozicovna;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Trieda reprezentujúca požičovňu dopravných prostriedkov, ako sú lode a vodné skútre.
 * Spravuje zoznam prostriedkov, prenájmov a poskytuje funkcie na ich správu, filtrovanie,
 * triedenie, nabíjanie, tankovanie a ukladanie dát do súborov. Obsahuje aj konzolovú aplikáciu
 * na interakciu s používateľom.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public class Pozicovna {
    private String nazov;
    private DopravnyProstriedok[] dProstriedky;
    private List<Prenajom> prenajmy;

    /**
     * Vytvorí novú inštanciu požičovne s daným názvom a maximálnym počtom prostriedkov.
     *
     * @param nazov Názov požičovne.
     * @param maxPocetDopravnychProstriedkov Maximálny počet dopravných prostriedkov, ktoré môže požičovňa spravovať.
     */
    public Pozicovna(String nazov, int maxPocetDopravnychProstriedkov) {
        this.nazov = nazov;
        this.dProstriedky = new DopravnyProstriedok[maxPocetDopravnychProstriedkov];
        this.prenajmy = new ArrayList<>();
    }

    /**
     * Vracia zoznam aktuálnych prenájmov.
     *
     * @return Zoznam objektov typu {@link Prenajom}.
     */
    public List<Prenajom> getPrenajmy() {
        return prenajmy;
    }

    /**
     * Vracia pole dopravných prostriedkov spravovaných požičovňou.
     *
     * @return Pole objektov typu {@link DopravnyProstriedok}.
     */
    public DopravnyProstriedok[] getDProstriedky() {
        return dProstriedky;
    }

    /**
     * Nájde prvú voľnú pozíciu v poli dopravných prostriedkov.
     *
     * @return Index voľnej pozície, alebo -1, ak nie je žiadna pozícia voľná.
     */
    public int findFreePos() {
        for (int i = 0; i < dProstriedky.length; i++) {
            if (dProstriedky[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Pridá dopravný prostriedok do požičovne na prvú voľnú pozíciu.
     *
     * @param dp Dopravný prostriedok na pridanie ({@link DopravnyProstriedok}).
     * @return {@code true}, ak bol prostriedok pridaný, {@code false}, ak nie je voľná pozícia.
     */
    public boolean addProstriedok(DopravnyProstriedok dp) {
        int pos = findFreePos();
        if (pos == -1) return false;
        dProstriedky[pos] = dp;
        return true;
    }

    /**
     * Odstráni dopravný prostriedok z požičovne, ak nie je prenajatý.
     *
     * @param dpToRemove Dopravný prostriedok na odstránenie ({@link DopravnyProstriedok}).
     * @return {@code true}, ak bol prostriedok odstránený, {@code false}, ak je prenajatý alebo nenájdený.
     */
    public boolean removeProstriedok(DopravnyProstriedok dpToRemove) {
        if (dpToRemove == null) {
            return false;
        }
        if (dpToRemove.getPozicane()) {
            return false; // Nemôžeme odstrániť prenajatý prostriedok
        }
        for (int i = 0; i < dProstriedky.length; i++) {
            if (dProstriedky[i] == dpToRemove) {
                dProstriedky[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Nájde dopravný prostriedok podľa jeho ID.
     *
     * @param id Jedinečné ID prostriedku.
     * @return Nájdený dopravný prostriedok ({@link DopravnyProstriedok}), alebo {@code null}, ak neexistuje.
     */
    public DopravnyProstriedok najdiProstriedok(int id) {
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp != null && dp.getId() == id) {
                return dp;
            }
        }
        return null;
    }

    /**
     * Vracia prvú dostupnú loď, ktorá nie je prenajatá.
     *
     * @return Prvá dostupná loď ({@link Lod}), alebo {@code null}, ak žiadna nie je dostupná.
     */
    public Lod getFirstFreeLod() {
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof Lod && !dp.getPozicane()) {
                return (Lod) dp;
            }
        }
        return null;
    }

    /**
     * Vracia celkový počet lodí v požičovni.
     *
     * @return Počet lodí typu {@link Lod}.
     */
    public int getPocetLodi() {
        int pocet = 0;
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof Lod) {
                pocet++;
            }
        }
        return pocet;
    }

    /**
     * Vracia pole všetkých dostupných lodí, ktoré nie sú prenajaté.
     *
     * @return Pole dostupných lodí ({@link Lod}), alebo {@code null}, ak žiadne nie sú dostupné.
     */
    public Lod[] getAllFreeLode() {
        List<Lod> freeLode = new ArrayList<>();
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof Lod && !dp.getPozicane()) {
                freeLode.add((Lod) dp);
            }
        }
        return freeLode.isEmpty() ? null : freeLode.toArray(new Lod[0]);
    }

    /**
     * Vracia pole všetkých prenajatých lodí.
     *
     * @return Pole prenajatých lodí ({@link Lod}), alebo {@code null}, ak žiadne nie sú prenajaté.
     */
    public Lod[] getAllBorrowedLodi() {
        List<Lod> pozicane = new ArrayList<>();
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof Lod && dp.getPozicane()) {
                pozicane.add((Lod) dp);
            }
        }
        return pozicane.isEmpty() ? null : pozicane.toArray(new Lod[0]);
    }

    /**
     * Vracia celkový počet bojových lodí na ovládanie v požičovni.
     *
     * @return Počet lodí typu {@link BojovaLodNaOvladanie}.
     */
    public int getPocetToyBoats() {
        int pocet = 0;
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof BojovaLodNaOvladanie) {
                pocet++;
            }
        }
        return pocet;
    }

    /**
     * Vracia pole všetkých dostupných bojových lodí na ovládanie, ktoré nie sú prenajaté.
     *
     * @return Pole dostupných lodí ({@link BojovaLodNaOvladanie}), alebo {@code null}, ak žiadne nie sú dostupné.
     */
    public BojovaLodNaOvladanie[] getAllFreeToyBoats() {
        List<BojovaLodNaOvladanie> freeToyBoats = new ArrayList<>();
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof BojovaLodNaOvladanie && !dp.getPozicane()) {
                freeToyBoats.add((BojovaLodNaOvladanie) dp);
            }
        }
        return freeToyBoats.isEmpty() ? null : freeToyBoats.toArray(new BojovaLodNaOvladanie[0]);
    }

    /**
     * Vracia pole dostupných bojových lodí na ovládanie s minimálnym dojazdom.
     *
     * @param minDojazd Minimálny požadovaný dojazd v kilometroch.
     * @return Pole lodí ({@link BojovaLodNaOvladanie}) s dojazdom väčším alebo rovným zadanému,
     *         alebo {@code null}, ak žiadne nevyhovujú.
     */
    public BojovaLodNaOvladanie[] getAllFreeToyBoatsWithMinRange(float minDojazd) {
        List<BojovaLodNaOvladanie> filtered = new ArrayList<>();
        for (BojovaLodNaOvladanie toyBoat : getAllFreeToyBoats()) {
            if (toyBoat != null && toyBoat.getDojazd() >= minDojazd) {
                filtered.add(toyBoat);
            }
        }
        return filtered.isEmpty() ? null : filtered.toArray(new BojovaLodNaOvladanie[0]);
    }

    /**
     * Vracia pole všetkých dostupných dopravných prostriedkov, ktoré nie sú prenajaté.
     *
     * @return Pole dostupných prostriedkov ({@link DopravnyProstriedok}), alebo {@code null}, ak žiadne nie sú dostupné.
     */
    public DopravnyProstriedok[] getAllFreeProstriedky() {
        List<DopravnyProstriedok> freeProstriedky = new ArrayList<>();
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp != null && !dp.getPozicane()) {
                freeProstriedky.add(dp);
            }
        }
        return freeProstriedky.isEmpty() ? null : freeProstriedky.toArray(new DopravnyProstriedok[0]);
    }

    /**
     * Nabije všetky dopravné prostriedky, ktoré implementujú {@link NabijanieAble},
     * zadaným množstvom energie.
     *
     * @param kw Množstvo energie na nabitie v kilowatthodinách (kWh).
     */
    public void chargeAll(float kw) {
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof NabijanieAble) {
                ((NabijanieAble) dp).charge(kw);
            }
        }
    }

    /**
     * Natankuje všetky dopravné prostriedky, ktoré implementujú {@link TankujAble},
     * zadaným typom paliva.
     *
     * @param palivo Typ paliva na tankovanie ({@link Palivo}).
     */
    public void refuelAll(Palivo palivo) {
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof TankujAble) {
                ((TankujAble) dp).tankuj(palivo);
            }
        }
    }

    /**
     * Nájde prvú dostupnú bojovú loď so spaľovacím motorom, ktorá používa zadané palivo
     * a má minimálny objem.
     *
     * @param palivo Typ paliva ({@link Palivo}).
     * @param objem Minimálny požadovaný objem v metroch kubických.
     * @return Prvá dostupná bojová loď ({@link BojovaLod}), alebo {@code null}, ak žiadna nevyhovuje.
     */
    public BojovaLod getFirstFreeSpalovak(Palivo palivo, float objem) {
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof BojovaLod && !dp.getPozicane() &&
                ((BojovaLod) dp).getPalivo() == palivo && ((BojovaLod) dp).getObjem() >= objem) {
                return (BojovaLod) dp;
            }
        }
        return null;
    }

    /**
     * Filtruje dostupné lode podľa rozsahu munície.
     *
     * @param minMunicia Minimálny počet munície.
     * @param maxMunicia Maximálny počet munície.
     * @return Zoznam dostupných lodí ({@link Lod}) s muníciou v zadanom rozsahu.
     */
    public List<Lod> filterMuniciaLode(int minMunicia, int maxMunicia) {
        List<Lod> ammoLode = new ArrayList<>();
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp instanceof Lod lod && !lod.getPozicane() &&
                lod.getMunicia() >= minMunicia && lod.getMunicia() <= maxMunicia) {
                ammoLode.add(lod);
            }
        }
        return ammoLode;
    }

    /**
     * Zoradí dostupné dopravné prostriedky podľa zadaného kritéria.
     *
     * @param kriterium Kritérium zoradenia ("spotreba", "municia", "dojazd").
     * @return Pole zoradených prostriedkov ({@link DopravnyProstriedok}), alebo {@code null}, ak žiadne nie sú dostupné.
     */
    public DopravnyProstriedok[] zoradProstriedky(String kriterium) {
        List<DopravnyProstriedok> prostriedky = new ArrayList<>();
        for (DopravnyProstriedok dp : dProstriedky) {
            if (dp != null && !dp.getPozicane()) {
                prostriedky.add(dp);
            }
        }
        if (prostriedky.isEmpty()) return null;

        switch (kriterium.toLowerCase()) {
            case "spotreba":
                prostriedky.sort(Comparator.comparing(dp -> {
                    if (dp instanceof Lod) {
                        return ((Lod) dp).getSpotreba();
                    } else if (dp instanceof VodnySkuter) {
                        return 0.0f;
                    }
                    return Float.MAX_VALUE;
                }));
                break;
            case "municia":
                prostriedky.sort(Comparator.comparingInt(dp -> {
                    if (dp instanceof Lod) {
                        return ((Lod) dp).getMunicia();
                    }
                    return 0;
                }));
                break;
            case "dojazd":
                prostriedky.sort(Comparator.comparingDouble(dp -> {
                    if (dp instanceof BojovaLodNaOvladanie) {
                        return ((BojovaLodNaOvladanie) dp).getDojazd();
                    } else if (dp instanceof VyletnaLod) {
                        return ((VyletnaLod) dp).getDojazd();
                    } else if (dp instanceof VodnySkuter) {
                        return ((VodnySkuter) dp).getDojazd();
                    }
                    return 0.0;
                }));
                break;
            default:
                return prostriedky.toArray(new DopravnyProstriedok[0]);
        }
        return prostriedky.toArray(new DopravnyProstriedok[0]);
    }

    /**
     * Generuje zmluvu o prenájme a uloží ju do súboru.
     *
     * @param prenajom Prenájom, pre ktorý sa zmluva generuje ({@link Prenajom}).
     * @return {@code true}, ak bola zmluva úspešne vygenerovaná, {@code false} v prípade chyby.
     */
    public boolean generujZmluvu(Prenajom prenajom) {
        String sablona = "ZMLUVA O PRENÁJME\n" +
                         "====================\n" +
                         "Zákazník: {meno_zakaznika}\n" +
                         "Vozidlo: {nazov_vozidla} (ID: {vozidlo_id})\n" +
                         "Dátum prenájmu: {datum}\n" +
                         "Počet dní: {dni}\n" +
                         "Celková cena: {cena}€\n" +
                         "Spotreba: {spotreba} l/100km\n" +
                         "Podpis zákazníka: ____________________\n" +
                         "Podpis požičovne: ____________________\n" +
                         "====================\n";
        String spotreba = prenajom.getVozidlo() instanceof Lod ? String.valueOf(((Lod) prenajom.getVozidlo()).getSpotreba()) : "Není";
        String zmluva = sablona
            .replace("{meno_zakaznika}", prenajom.getMenoZakaznika())
            .replace("{nazov_vozidla}", prenajom.getVozidlo().getNazov())
            .replace("{vozidlo_id}", String.valueOf(prenajom.getVozidlo().getId()))
            .replace("{datum}", prenajom.getDatumPrenajmu())
            .replace("{dni}", String.valueOf(prenajom.getPocetDniPrenajmu()))
            .replace("{cena}", String.valueOf(prenajom.vypocitajCelkovuCenu()))
            .replace("{spotreba}", spotreba);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("zmluva_" + prenajom.getVozidlo().getId() + "_" + prenajom.getDatumPrenajmu() + ".txt"))) {
            writer.write(zmluva);
            return true;
        } catch (IOException e) {
            System.out.println("Chyba pri generovaní zmluvy: " + e.getMessage());
            return false;
        }
    }

    /**
     * Vytvorí nový prenájom pre zadaný dopravný prostriedok. Používateľ zadá detaily
     * cez konzolu (meno zákazníka, dátum, počet dní). Ak je prenájom úspešný, generuje zmluvu.
     *
     * @param vybraneVozidlo Dopravný prostriedok na prenájom ({@link DopravnyProstriedok}).
     */
    public void vytvorPrenajom(DopravnyProstriedok vybraneVozidlo) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Zadaj meno zákazníka: ");
        String meno = scanner.nextLine().trim();
        if (meno.isEmpty()) {
            System.out.println("Meno zákazníka nesmie byť prázdne!");
            return;
        }

        System.out.print("Zadaj dátum začiatku (RRRR-MM-DD): ");
        String datum = scanner.nextLine();

        System.out.print("Zadaj počet dní prenájmu: ");
        int dni;
        try {
            dni = Integer.parseInt(scanner.nextLine());
            if (dni <= 0) {
                System.out.println("Počet dní musí byť kladný!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Neplatný počet dní!");
            return;
        }

        if (vybraneVozidlo == null || vybraneVozidlo.getPozicane()) {
            System.out.println("Vozidlo nie je dostupné!");
            return;
        }

        try {
            LocalDate.parse(datum);
        } catch (DateTimeParseException e) {
            System.out.println("Neplatný formát dátumu (RRRR-MM-DD)!");
            return;
        }

        Prenajom novyPrenajom = new Prenajom(vybraneVozidlo, meno, datum, dni);
        vybraneVozidlo.pozicaj();
        prenajmy.add(novyPrenajom);
        ulozPrenajmyDoSuboru("prenajmy.csv");
        if (generujZmluvu(novyPrenajom)) {
            System.out.println("Prenájom a zmluva úspešne vytvorené!");
        } else {
            System.out.println("Prenájom vytvorený, ale zmluvu sa nepodarilo vygenerovať!");
        }
    }

    /**
     * Uloží zoznam prenájmov do CSV súboru.
     *
     * @param nazovSuboru Názov súboru, do ktorého sa prenájmy uložia.
     */
    public void ulozPrenajmyDoSuboru(String nazovSuboru) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nazovSuboru))) {
            for (Prenajom p : prenajmy) {
                writer.write(p.getMenoZakaznika() + ";" +
                             p.getVozidlo().getNazov() + ";" +
                             p.getDatumPrenajmu() + ";" +
                             p.getPocetDniPrenajmu());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Chyba pri ukladaní prenájmov: " + e.getMessage());
        }
    }

    /**
     * Načíta zoznam prenájmov z CSV súboru a aktualizuje stav prostriedkov.
     *
     * @param nazovSuboru Názov súboru, z ktorého sa prenájmy načítajú.
     */
    public void nacitajPrenajmyZoSuboru(String nazovSuboru) {
        prenajmy.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nazovSuboru))) {
            String riadok;
            while ((riadok = reader.readLine()) != null) {
                String[] casti = riadok.split(";");
                if (casti.length == 4) {
                    String meno = casti[0];
                    String nazovVozidla = casti[1];
                    String datum = casti[2];
                    int dni = Integer.parseInt(casti[3]);

                    DopravnyProstriedok vozidlo = najdiVozidloPodlaNazvu(nazovVozidla);
                    if (vozidlo != null) {
                        Prenajom prenajom = new Prenajom(vozidlo, meno, datum, dni);
                        prenajmy.add(prenajom);
                        vozidlo.pozicaj();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri načítaní prenájmov: " + e.getMessage());
        }
    }

    /**
     * Uloží zoznam dopravných prostriedkov do CSV súboru.
     *
     * @param nazovSuboru Názov súboru, do ktorého sa prostriedky uložia.
     */
    public void ulozProstriedkyDoSuboru(String nazovSuboru) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nazovSuboru))) {
            for (DopravnyProstriedok dp : dProstriedky) {
                if (dp != null) {
                    String typ = dp.getClass().getSimpleName();
                    String data = "";
                    if (dp instanceof BojovaLod bojovaLod) {
                        data = typ + ";" + dp.getId() + ";" + bojovaLod.getMotor() + ";" +
                               bojovaLod.getPocetMiest() + ";" + bojovaLod.getSpotreba() + ";" +
                               bojovaLod.getObjem() + ";" + bojovaLod.getObjemNadrze() + ";" +
                               bojovaLod.getStavNadrze() + ";" + bojovaLod.getPalivo() + ";" +
                               bojovaLod.getMunicia() + ";" + dp.getCenaZaDen();
                    } else if (dp instanceof BojovaLodNaOvladanie toyBoat) {
                        data = typ + ";" + dp.getId() + ";" + toyBoat.getMotor() + ";" +
                               toyBoat.getPocetMiest() + ";" + toyBoat.getSpotreba() + ";" +
                               toyBoat.getVelkostBaterie() + ";" + toyBoat.getStavBaterie() + ";" +
                               toyBoat.getDojazd() + ";" + dp.getCenaZaDen();
                    } else if (dp instanceof VodnySkuter skuter) {
                        data = typ + ";" + dp.getId() + ";" + skuter.getMotor() + ";" +
                               skuter.getVelkost() + ";" + skuter.getTyp() + ";" +
                               skuter.getVelkostBaterie() + ";" + skuter.getStavBaterie() + ";" +
                               skuter.getPocetMiest() + ";" + skuter.getDojazd() + ";" + dp.getCenaZaDen();
                    } else if (dp instanceof VyletnaLod vyletnaLod) {
                        data = typ + ";" + dp.getId() + ";" + vyletnaLod.getMotor() + ";" +
                               vyletnaLod.getPocetMiest() + ";" + vyletnaLod.getSpotreba() + ";" +
                               vyletnaLod.getObjemNadrze() + ";" + vyletnaLod.getStavNadrze() + ";" +
                               vyletnaLod.getTypPaliva() + ";" + vyletnaLod.getVelkostBaterie() + ";" +
                               vyletnaLod.getStavBaterie() + ";" + vyletnaLod.getDojazd() + ";" +
                               dp.getCenaZaDen();
                    }
                    writer.write(data);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri ukladaní prostriedkov: " + e.getMessage());
        }
    }

    /**
     * Načíta dopravné prostriedky z CSV súboru. Ak súbor neexistuje, inicializuje predvolené prostriedky.
     *
     * @param nazovSuboru Názov súboru, z ktorého sa prostriedky načítajú.
     */
    public void nacitajProstriedkyZoSuboru(String nazovSuboru) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nazovSuboru))) {
            String riadok;
            while ((riadok = reader.readLine()) != null) {
                String[] casti = riadok.split(";");
                if (casti.length < 2) continue;
                String typ = casti[0];
                int id = Integer.parseInt(casti[1]);
                float cenaZaDen = Float.parseFloat(casti[casti.length - 1]);

                DopravnyProstriedok dp = null;
                if (typ.equals("BojovaLod") && casti.length == 11) {
                    Motor motor = Motor.valueOf(casti[2]);
                    int pocetMiest = Integer.parseInt(casti[3]);
                    float spotreba = Float.parseFloat(casti[4]);
                    float objem = Float.parseFloat(casti[5]);
                    float objemNadrze = Float.parseFloat(casti[6]);
                    float stavNadrze = Float.parseFloat(casti[7]);
                    Palivo palivo = Palivo.valueOf(casti[8]);
                    int municia = Integer.parseInt(casti[9]);
                    dp = new BojovaLod(motor, pocetMiest, spotreba, objem, objemNadrze, palivo, municia, cenaZaDen);
                } else if (typ.equals("BojovaLodNaOvladanie") && casti.length == 9) {
                    Motor motor = Motor.valueOf(casti[2]);
                    int pocetMiest = Integer.parseInt(casti[3]);
                    float spotreba = Float.parseFloat(casti[4]);
                    float velkostBaterie = Float.parseFloat(casti[5]);
                    float stavBaterie = Float.parseFloat(casti[6]);
                    float dojazd = Float.parseFloat(casti[7]);
                    dp = new BojovaLodNaOvladanie(motor, pocetMiest, spotreba, velkostBaterie, dojazd, cenaZaDen);
                } else if (typ.equals("VodnySkuter") && casti.length == 10) {
                    Motor motor = Motor.valueOf(casti[2]);
                    Velkost velkost = Velkost.valueOf(casti[3]);
                    BoatType boatType = BoatType.valueOf(casti[4]);
                    float velkostBaterie = Float.parseFloat(casti[5]);
                    float stavBaterie = Float.parseFloat(casti[6]);
                    int pocetMiest = Integer.parseInt(casti[7]);
                    float dojazd = Float.parseFloat(casti[8]);
                    dp = new VodnySkuter(velkost, boatType, velkostBaterie, stavBaterie, cenaZaDen, motor, pocetMiest, dojazd);
                } else if (typ.equals("VyletnaLod") && casti.length == 12) {
                    Motor motor = Motor.valueOf(casti[2]);
                    int pocetMiest = Integer.parseInt(casti[3]);
                    float spotreba = Float.parseFloat(casti[4]);
                    float objemNadrze = Float.parseFloat(casti[5]);
                    float stavNadrze = Float.parseFloat(casti[6]);
                    Palivo palivo = Palivo.valueOf(casti[7]);
                    float velkostBaterie = Float.parseFloat(casti[8]);
                    float stavBaterie = Float.parseFloat(casti[9]);
                    float dojazd = Float.parseFloat(casti[10]);
                    dp = new VyletnaLod(motor, pocetMiest, spotreba, objemNadrze, stavNadrze, palivo, velkostBaterie, stavBaterie, dojazd, cenaZaDen);
                }
                if (dp != null) addProstriedok(dp);
            }
        } catch (IOException e) {
            System.out.println("Chyba pri načítaní prostriedkov: " + e.getMessage());
            // Ak súbor neexistuje, inicializuj predvolené hodnoty
            if (Files.notExists(Paths.get(nazovSuboru))) {
                System.out.println("Súbor neexistuje, inicializujem predvolené prostriedky...");
                addProstriedok(new BojovaLod(Motor.BENZIN, 4, 10.0f, 100.0f, 50.0f, Palivo.BENZIN, 50, 50.0f));
                addProstriedok(new BojovaLod(Motor.DIESEL, 6, 12.0f, 120.0f, 60.0f, Palivo.DIESEL, 30, 60.0f));
                addProstriedok(new BojovaLodNaOvladanie(Motor.ELEKTRICKY, 2, 5.0f, 20.0f, 100.0f, 30.0f));
                addProstriedok(new BojovaLodNaOvladanie(Motor.ELEKTRICKY, 1, 4.0f, 15.0f, 80.0f, 25.0f));
                addProstriedok(new VodnySkuter(Velkost.S, BoatType.KANOE, 15.0f, 50.0f, 20.0f, Motor.ELEKTRICKY, 2, 50.0f));
                addProstriedok(new VodnySkuter(Velkost.M, BoatType.KABINOVA, 20.0f, 60.0f, 30.0f, Motor.ELEKTRICKY, 4, 75.0f));
                addProstriedok(new VodnySkuter(Velkost.L, BoatType.RYBARSKA, 25.0f, 70.0f, 35.0f, Motor.ELEKTRICKY, 6, 100.0f));
                addProstriedok(new VyletnaLod(Motor.HYBRID, 6, 12.0f, 1420.0f, 60.0f, Palivo.BENZIN, 50.0f, 120.0f, 25.0f, 60.0f));
                addProstriedok(new VyletnaLod(Motor.HYBRID, 8, 15.0f, 150.0f, 80.0f, Palivo.DIESEL, 60.0f, 200.0f, 30.0f, 70.0f));
                addProstriedok(new VyletnaLod(Motor.HYBRID, 5, 10.0f, 100.0f, 50.0f, Palivo.BENZIN, 40.0f, 120.0f, 20.0f, 55.0f));
                ulozProstriedkyDoSuboru(nazovSuboru);
            }
        }
    }

    /**
     * Nájde dopravný prostriedok podľa jeho názvu.
     *
     * @param nazov Názov prostriedku.
     * @return Nájdený dopravný prostriedok ({@link DopravnyProstriedok}), alebo {@code null}, ak neexistuje.
     */
    private DopravnyProstriedok najdiVozidloPodlaNazvu(String nazov) {
        for (DopravnyProstriedok v : dProstriedky) {
            if (v != null && v.getNazov().equals(nazov)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Vypíše zoznam aktuálnych prenájmov do konzoly.
     */
    public void vypisPrenajmy() {
        if (prenajmy.isEmpty()) {
            System.out.println("Žiadne prenájmy.");
            return;
        }

        for (Prenajom p : prenajmy) {
            System.out.println(p);
        }
    }

    /**
     * Umožní vrátenie dopravného prostriedku z prenájmu. Používateľ zadá názov
     * vozidla a meno zákazníka cez konzolu.
     */
    public void vratVozidlo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Zadaj názov vozidla, ktoré chceš vrátiť: ");
        String nazovVozidla = scanner.nextLine();

        System.out.print("Zadaj meno zákazníka, ktorý si ho požičal: ");
        String meno = scanner.nextLine();

        Prenajom naOdstranenie = null;
        for (Prenajom p : prenajmy) {
            if (p.getVozidlo().getNazov().equalsIgnoreCase(nazovVozidla) &&
                p.getMenoZakaznika().equalsIgnoreCase(meno)) {
                naOdstranenie = p;
                break;
            }
        }

        if (naOdstranenie != null) {
            naOdstranenie.getVozidlo().vratit();
            prenajmy.remove(naOdstranenie);
            ulozPrenajmyDoSuboru("prenajmy.csv");
            System.out.println("Vozidlo bolo úspešne vrátené.");
        } else {
            System.out.println("Prenájom nebol nájdený. Skontroluj názov a meno.");
        }
    }

    /**
     * Hlavná metóda spúšťajúca konzolovú aplikáciu požičovne. Umožňuje používateľovi
     * vytvárať prenájmy, vracať vozidlá, zobrazovať dostupné prostriedky, triediť ich a ukončiť program.
     *
     * @param args Argumenty príkazového riadku (nepoužité).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pozicovna pozicovna = new Pozicovna("Moja Požičovňa", 15);

        // Načítaj existujúce dáta z CSV
        pozicovna.nacitajProstriedkyZoSuboru("prostriedky.csv");
        pozicovna.nacitajPrenajmyZoSuboru("prenajmy.csv");

        while (true) {
            System.out.println("\n=== Požičovňa Lodí ===");
            System.out.println("1. Vytvoriť prenájom");
            System.out.println("2. Vrátiť vozidlo");
            System.out.println("3. Zobraziť dostupné prostriedky");
            System.out.println("4. Zobraziť prenájmy");
            System.out.println("5. Zobraziť lode s muníciou (20-100)");
            System.out.println("6. Nabíjať všetky elektrické prostriedky");
            System.out.println("7. Tankovať všetky spaľovacie prostriedky");
            System.out.println("8. Zoradiť prostriedky");
            System.out.println("9. Ukončiť");
            System.out.print("Vyber možnosť: ");

            String volba = scanner.nextLine();
            switch (volba) {
                case "1":
                    System.out.print("Zadaj ID prostriedku na prenájom: ");
                    try {
                        int id = Integer.parseInt(scanner.nextLine());
                        DopravnyProstriedok prostriedok = pozicovna.najdiProstriedok(id);
                        pozicovna.vytvorPrenajom(prostriedok);
                    } catch (NumberFormatException e) {
                        System.out.println("Neplatné ID!");
                    }
                    break;

                case "2":
                    pozicovna.vratVozidlo();
                    break;

                case "3":
                    DopravnyProstriedok[] freeProstriedky = pozicovna.getAllFreeProstriedky();
                    if (freeProstriedky != null) {
                        System.out.println("Dostupné prostriedky:");
                        for (DopravnyProstriedok dp : freeProstriedky) {
                            String identifikacia = dp instanceof Lod ? ((Lod) dp).identifikujSa() : dp.getClass().getSimpleName();
                            System.out.println(identifikacia + ", ID: " + dp.getId() + ", Cena za deň: " + dp.getCenaZaDen() + "€");
                        }
                    } else {
                        System.out.println("Žiadne dostupné prostriedky.");
                    }
                    break;

                case "4":
                    pozicovna.vypisPrenajmy();
                    break;

                case "5":
                    List<Lod> ammoLode = pozicovna.filterMuniciaLode(20, 100);
                    if (!ammoLode.isEmpty()) {
                        System.out.println("Lode s muníciou 20-100:");
                        for (Lod lod : ammoLode) {
                            System.out.println(lod.identifikujSa() + ", Náboje: " + lod.getMunicia() + ", ID: " + lod.getId());
                        }
                    } else {
                        System.out.println("Žiadne lode s daným rozsahom munície.");
                    }
                    break;

                case "6":
                    System.out.print("Zadaj množstvo energie na nabitie (kW): ");
                    try {
                        float kw = Float.parseFloat(scanner.nextLine());
                        pozicovna.chargeAll(kw);
                        System.out.println("Všetky elektrické prostriedky boli nabité.");
                    } catch (NumberFormatException e) {
                        System.out.println("Neplatné množstvo energie!");
                    }
                    break;

                case "7":
                    System.out.println("Dostupné palivá: BENZIN, DIESEL");
                    System.out.print("Zadaj typ paliva: ");
                    String palivo = scanner.nextLine().toUpperCase();
                    try {
                        Palivo typPaliva = Palivo.valueOf(palivo);
                        pozicovna.refuelAll(typPaliva);
                        System.out.println("Všetky spaľovacie prostriedky boli natankované.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Neplatný typ paliva!");
                    }
                    break;

                case "8":
                    System.out.print("Zadaj kritérium zoradenia (spotreba/municia/dojazd): ");
                    String kriterium = scanner.nextLine();
                    DopravnyProstriedok[] zoradeneProstriedky = pozicovna.zoradProstriedky(kriterium);
                    if (zoradeneProstriedky != null) {
                        System.out.println("Zoradené prostriedky:");
                        for (DopravnyProstriedok dp : zoradeneProstriedky) {
                            String identifikacia = dp instanceof Lod ? ((Lod) dp).identifikujSa() : dp.getClass().getSimpleName();
                            System.out.println(identifikacia + ", ID: " + dp.getId() + ", Cena za deň: " + dp.getCenaZaDen() + "€");
                        }
                    } else {
                        System.out.println("Žiadne dostupné prostriedky.");
                    }
                    break;

                case "9":
                    pozicovna.ulozProstriedkyDoSuboru("prostriedky.csv");
                    System.out.println("Ukončujem program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Neplatná voľba!");
            }
        }
    }
}