package pozicovna;

/**
 * Abstraktná trieda reprezentujúca dopravný prostriedok v požičovni.
 * Definuje základné vlastnosti a správanie pre všetky typy dopravných prostriedkov,
 * ako sú lode a skútre. Obsahuje informácie o ID, stave požičania, cene za deň
 * a počte požičaní. Trieda je základom pre konkrétne triedy ako {@link Boat}.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public abstract class DopravnyProstriedok {

    private int id;
    private int pocetPozicani = 0;
    protected boolean pozicane = false;
    private static int pocitadlo = 0;
    private float cenaZaDen;

    /**
     * Vytvorí novú inštanciu dopravného prostriedku s automaticky priradeným ID
     * a zadanou cenou za deň.
     *
     * @param cenaZaDen Cena za prenájom prostriedku na jeden deň v eurách.
     */
    public DopravnyProstriedok(float cenaZaDen) {
        this.id = pocitadlo++;
        this.cenaZaDen = cenaZaDen;
    }

    /**
     * Požičia dopravný prostriedok, ak nie je už požičaný.
     * Zvýši počet požičaní o 1 a nastaví stav požičania na {@code true}.
     *
     * @return {@code true}, ak bolo požičanie úspešné, {@code false}, ak je prostriedok už požičaný.
     */
    public boolean pozicaj() {
        if (pozicane) {
            return false;
        }
        pozicane = true;
        pocetPozicani++;
        return true;
    }

    /**
     * Vráti dopravný prostriedok, čím ho sprístupní na ďalšie požičanie.
     * Nastaví stav požičania na {@code false}.
     */
    public void vratit() {
        pozicane = false;
    }

    /**
     * Vracia, či je dopravný prostriedok momentálne požičaný.
     *
     * @return {@code true}, ak je prostriedok požičaný, inak {@code false}.
     */
    public boolean getPozicane() {
        return pozicane;
    }

    /**
     * Vracia celkový počet požičaní dopravného prostriedku.
     *
     * @return Počet požičaní.
     */
    public int getPocetPozicani() {
        return pocetPozicani;
    }

    /**
     * Vracia jedinečné ID dopravného prostriedku.
     *
     * @return ID prostriedku.
     */
    public int getId() {
        return id;
    }

    /**
     * Vracia cenu prenájmu dopravného prostriedku za jeden deň.
     *
     * @return Cena za deň v eurách.
     */
    public float getCenaZaDen() {
        return cenaZaDen;
    }

    /**
     * Nastaví cenu prenájmu dopravného prostriedku za jeden deň.
     *
     * @param cenaZaDen Cena za deň v eurách.
     * @throws IllegalArgumentException Ak je cena záporná.
     */
    public void setCenaZaDen(float cenaZaDen) {
        if (cenaZaDen < 0) {
            throw new IllegalArgumentException("Cena za deň musí byť nezáporná!");
        }
        this.cenaZaDen = cenaZaDen;
    }

    /**
     * Abstraktná metóda, ktorá vracia identifikujúci reťazec pre dopravný prostriedok.
     * Konkrétna implementácia závisí od podtriedy.
     *
     * @return Reťazec popisujúci dopravný prostriedok.
     */
    abstract String identifikujSa();

    /**
     * Abstraktná metóda, ktorá vracia názov dopravného prostriedku.
     * Konkrétna implementácia závisí od podtriedy.
     *
     * @return Názov prostriedku.
     */
    public abstract String getNazov();

    /**
     * Vracia textovú reprezentáciu dopravného prostriedku, ktorá obsahuje
     * jeho názov, ID a cenu za deň.
     *
     * @return Reťazec vo formáte "[názov] (ID: [id], Cena za deň: [cenaZaDen]€)".
     */
    @Override
    public String toString() {
        return getNazov() + " (ID: " + id + ", Cena za deň: " + cenaZaDen + "€)";
    }
}