package pozicovna;

/**
 * Trieda reprezentujúca bojovú loď na ovládanie, ktorá je špecifickým typom lode.
 * Ide o elektrickú loď s batériou, ktorá má určený dojazd a muníciu.
 * Implementuje rozhranie {@link NabijanieAble}, čo umožňuje nabíjanie batérie.
 * Rozširuje triedu {@link Lod}, čím zdedí jej vlastnosti ako motor, počet miest a spotrebu.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public class BojovaLodNaOvladanie extends Lod implements NabijanieAble {

    private float velkostBaterie;
    private float stavBaterie;
    private float dojazd;

    /**
     * Vytvorí novú inštanciu bojovej lode na ovládanie s danými parametrami.
     * Batéria je pri vytvorení plná (100 %).
     *
     * @param motor Typ motora lode, musí byť {@link Motor#ELEKTRICKY}.
     * @param pocetMiest Počet miest pre pasažierov na lodi.
     * @param spotreba Spotreba energie v jednotkách definovaných pre elektrické lode.
     * @param velkostBaterie Kapacita batérie v kilowatthodinách (kWh).
     * @param dojazd Maximálny dojazd lode pri plnej batérii v kilometroch.
     * @param cenaZaDen Cena za prenájom lode na jeden deň v eurách.
     * @throws IllegalArgumentException Ak motor nie je {@link Motor#ELEKTRICKY}.
     */
    public BojovaLodNaOvladanie(Motor motor, int pocetMiest, float spotreba, float velkostBaterie,
                                float dojazd, float cenaZaDen) {
        super(motor, pocetMiest, spotreba, cenaZaDen);
        if (motor != Motor.ELEKTRICKY) {
            throw new IllegalArgumentException("BojovaLodNaOvladanie musí mať motor ELEKTRICKY!");
        }
        this.velkostBaterie = velkostBaterie;
        this.stavBaterie = 100.0f; // Plná batéria pri vytvorení
        this.dojazd = dojazd;
        setMunicia(0);
    }

    /**
     * Nabije batériu lode o zadané množstvo energie. Nový stav batérie sa vypočíta
     * na základe pridaných kilowatthodín a kapacity batérie. Stav batérie nemôže
     * prekročiť 100 %.
     *
     * @param kw Množstvo energie na nabitie v kilowatthodinách (kWh).
     * @return Aktuálny stav batérie po nabití v percentách (0-100).
     */
    @Override
    public float charge(float kw) {
        float newStav = stavBaterie + (kw / velkostBaterie) * 100;
        stavBaterie = Math.min(newStav, 100);
        return stavBaterie;
    }

    /**
     * Vracia kapacitu batérie lode.
     *
     * @return Kapacita batérie v kilowatthodinách (kWh).
     */
    public float getVelkostBaterie() {
        return velkostBaterie;
    }

    /**
     * Vracia aktuálny stav batérie lode.
     *
     * @return Stav batérie v percentách (0-100).
     */
    public float getStavBaterie() {
        return stavBaterie;
    }

    /**
     * Vracia aktuálny dojazd lode, ktorý závisí od stavu batérie.
     * Dojazd sa vypočíta ako pomer maximálneho dojazdu a aktuálneho stavu batérie.
     *
     * @return Aktuálny dojazd lode v kilometroch.
     */
    public float getDojazd() {
        return dojazd * (stavBaterie / 100.0f);
    }

    /**
     * Vracia názov lode, ktorý je tvorený na základe typu motora.
     *
     * @return Názov lode vo formáte "BojovaLodNaOvladanie_[typ motora]".
     */
    @Override
    public String getNazov() {
        return "BojovaLodNaOvladanie_" + getMotor().toString();
    }

    /**
     * Vytvára identifikujúci reťazec pre bojovú loď na ovládanie, ktorý obsahuje
     * informácie o motore, batérii, stave batérie a dojazde.
     *
     * @return Reťazec vo formáte "[zdedené info], Batéria: [velkostBaterie]kWh,
     *         Stav batérie: [stavBaterie]%, Dojazd: [dojazd]km".
     */
    @Override
    public String identifikujSa() {
        return super.identifikujSa() + ", Batéria: " + velkostBaterie + "kWh, Stav batérie: " + stavBaterie + "%, Dojazd: " + getDojazd() + "km";
    }
}