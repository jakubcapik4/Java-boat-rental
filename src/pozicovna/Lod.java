package pozicovna;

/**
 * Abstraktná trieda reprezentujúca všeobecnú loď ako typ dopravného prostriedku.
 * Rozširuje triedu {@link DopravnyProstriedok} a pridáva špecifické vlastnosti lodí,
 * ako je motor, počet miest, spotreba, munícia a stav štartovania.
 * Slúži ako základ pre konkrétne typy lodí, ako sú {@link BojovaLod} alebo {@link VyletnaLod}.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public abstract class Lod extends DopravnyProstriedok {

    private Motor motor;
    private int pocetMiest;
    private float spotreba;
    protected int municia = 0;
    private boolean nastartovane = false;
    private float aktualnaSpotreba = 0;

    /**
     * Vytvorí novú inštanciu lode s danými parametrami.
     *
     * @param motor Typ motora lode ({@link Motor}).
     * @param pocetMiest Počet miest pre pasažierov na lodi.
     * @param spotreba Spotreba paliva v litroch na 100 km.
     * @param cenaZaDen Cena za prenájom lode na jeden deň v eurách.
     */
    public Lod(Motor motor, int pocetMiest, float spotreba, float cenaZaDen) {
        super(cenaZaDen);
        this.motor = motor;
        this.pocetMiest = pocetMiest;
        this.spotreba = spotreba;
    }

    /**
     * Naštartuje loď, čím nastaví jej stav na štartované a inicializuje aktuálnu spotrebu.
     *
     * @return {@code true}, ak bolo naštartovanie úspešné.
     */
    public boolean nastartuj() {
        nastartovane = true;
        aktualnaSpotreba = spotreba;
        return true;
    }

    /**
     * Skontroluje, či je loď naštartovaná a môže sa pohybovať.
     *
     * @param pocetKm Počet kilometrov, o ktorý sa loď má pohybovať (použité iba na kontrolu).
     * @return {@code true}, ak je loď naštartovaná, inak {@code false}.
     */
    public boolean move(float pocetKm) {
        return nastartovane;
    }

    /**
     * Nastaví stav štartovania lode.
     *
     * @param nastartovane {@code true}, ak je loď naštartovaná, inak {@code false}.
     */
    public void setNastartovane(boolean nastartovane) {
        this.nastartovane = nastartovane;
    }

    /**
     * Vracia stav štartovania lode.
     *
     * @return {@code true}, ak je loď naštartovaná, inak {@code false}.
     */
    public boolean getNastartovane() {
        return nastartovane;
    }

    /**
     * Vracia aktuálnu spotrebu lode.
     *
     * @return Aktuálna spotreba v litroch na 100 km.
     */
    public float getAktualnaSpotreba() {
        return aktualnaSpotreba;
    }

    /**
     * Nastaví aktuálnu spotrebu lode.
     *
     * @param aktualnaSpotreba Nová spotreba v litroch na 100 km.
     */
    public void setAktualnaSpotreba(float aktualnaSpotreba) {
        this.aktualnaSpotreba = aktualnaSpotreba;
    }

    /**
     * Vracia typ motora lode.
     *
     * @return Typ motora ({@link Motor}).
     */
    public Motor getMotor() {
        return motor;
    }

    /**
     * Vracia počet miest na lodi.
     *
     * @return Počet miest pre pasažierov.
     */
    public int getPocetMiest() {
        return pocetMiest;
    }

    /**
     * Vracia štandardnú spotrebu lode.
     *
     * @return Spotreba v litroch na 100 km.
     */
    public float getSpotreba() {
        return spotreba;
    }

    /**
     * Vracia počet nábojov, ktoré loď nesie.
     *
     * @return Počet munície.
     */
    public int getMunicia() {
        return municia;
    }

    /**
     * Nastaví počet nábojov, ktoré loď nesie.
     *
     * @param municia Nový počet nábojov.
     * @throws IllegalArgumentException Ak je počet munície záporný.
     */
    public void setMunicia(int municia) {
        if (municia < 0) {
            throw new IllegalArgumentException("Munícia nemôže byť záporná!");
        }
        this.municia = municia;
    }

    /**
     * Vytvára identifikujúci reťazec pre loď, ktorý obsahuje informácie o
     * motore, počte miest a spotrebe.
     *
     * @return Reťazec vo formáte "Lod, Motor: [motor], Počet miest: [pocetMiest],
     *         Spotreba: [spotreba] l/100km".
     */
    public String identifikujSa() {
        return "Lod, Motor: " + motor + ", Počet miest: " + pocetMiest + ", Spotreba: " + spotreba + " l/100km";
    }
}