package pozicovna;

/**
 * Trieda reprezentujúca vodný skúter, ktorý je špecifickým typom lode.
 * Vodný skúter je elektrický dopravný prostriedok s batériou a určeným dojazdom.
 * Implementuje rozhranie {@link NabijanieAble}, čo umožňuje nabíjanie batérie.
 * Rozširuje triedu {@link Boat}, čím zdedí jej vlastnosti ako veľkosť, typ a cenu za deň.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public class VodnySkuter extends Boat implements NabijanieAble {

    private float velkostBaterie;
    private float stavBaterie;
    private Motor motor;
    private int pocetMiest;
    private float dojazd;

    /**
     * Vytvorí novú inštanciu vodného skútra s danými parametrami.
     * Motor je automaticky nastavený na {@link Motor#ELEKTRICKY}.
     *
     * @param velkost Veľkosť skútra ({@link Velkost}).
     * @param typ Typ lode ({@link BoatType}).
     * @param velkostBaterie Kapacita batérie v kilowatthodinách (kWh).
     * @param stavBaterie Počiatočný stav batérie v percentách (0-100).
     * @param cenaZaDen Cena za prenájom na jeden deň v eurách.
     * @param motor Typ motora (používa sa na inicializáciu, ale vždy sa nastaví na ELEKTRICKY).
     * @param pocetMiest Počet miest pre pasažierov.
     * @param dojazd Maximálny dojazd pri plnej batérii v kilometroch.
     */
    public VodnySkuter(Velkost velkost, BoatType typ, float velkostBaterie, float stavBaterie, float cenaZaDen, Motor motor, int pocetMiest, float dojazd) {
        super(velkost, typ, cenaZaDen);
        this.velkostBaterie = velkostBaterie;
        this.stavBaterie = stavBaterie;
        this.motor = Motor.ELEKTRICKY;
        this.pocetMiest = pocetMiest;
        this.dojazd = dojazd;
    }

    /**
     * Nabije batériu vodného skútra o zadané množstvo energie. Nový stav batérie sa vypočíta
     * na základe pridaných kilowatthodín a kapacity batérie. Stav batérie nemôže prekročiť 100 %.
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
     * Vracia kapacitu batérie vodného skútra.
     *
     * @return Kapacita batérie v kilowatthodinách (kWh).
     */
    public float getVelkostBaterie() {
        return velkostBaterie;
    }

    /**
     * Vracia aktuálny stav batérie vodného skútra.
     *
     * @return Stav batérie v percentách (0-100).
     */
    public float getStavBaterie() {
        return stavBaterie;
    }

    /**
     * Vracia typ motora vodného skútra.
     *
     * @return Typ motora ({@link Motor#ELEKTRICKY}).
     */
    public Motor getMotor() {
        return motor;
    }

    /**
     * Vracia počet miest na vodnom skútri.
     *
     * @return Počet miest pre pasažierov.
     */
    public int getPocetMiest() {
        return pocetMiest;
    }

    /**
     * Vracia maximálny dojazd vodného skútra pri plnej batérii.
     *
     * @return Maximálny dojazd v kilometroch.
     */
    public float getDojazd() {
        return dojazd;
    }

    /**
     * Vracia názov vodného skútra, ktorý je tvorený na základe jeho typu.
     *
     * @return Názov vo formáte "VodnySkuter_[typ]".
     */
    @Override
    public String getNazov() {
        return "VodnySkuter_" + getTyp().toString();
    }

    /**
     * Vytvára identifikujúci reťazec pre vodný skúter, ktorý obsahuje informácie o
     * veľkosti, type, motore, batérii, počte miest a dojazde.
     *
     * @return Reťazec vo formáte "VodnySkuter, Veľkosť: [veľkosť], Typ: [typ], Motor: [motor],
     *         Batéria: [stav]%/ [kapacita]kWh, Miest: [miesta], Dojazd: [dojazd]km".
     */
    @Override
    public String identifikujSa() {
        return "VodnySkuter, Veľkosť: " + getVelkost() + ", Typ: " + getTyp() +
               ", Motor: " + motor + ", Batéria: " + stavBaterie + "%/" + velkostBaterie + "kWh" +
               ", Miest: " + pocetMiest + ", Dojazd: " + dojazd + "km";
    }
}