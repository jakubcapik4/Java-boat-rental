package pozicovna;

/**
 * Trieda reprezentujúca bojovú loď, ktorá je špecifickým typom lode.
 * Bojová loď má spaľovací motor (benzínový alebo naftový), nádrž na palivo a muníciu.
 * Implementuje rozhranie {@link TankujAble}, čo umožňuje tankovanie paliva.
 * Rozširuje triedu {@link Lod}, čím zdedí jej vlastnosti ako motor, počet miest a spotrebu.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public class BojovaLod extends Lod implements TankujAble {

    protected float objem;
    protected float objemNadrze;
    protected float stavNadrze = 0;
    protected Palivo typPaliva;

    /**
     * Vytvorí novú inštanciu bojovej lode s danými parametrami.
     *
     * @param motor Typ motora lode, musí byť {@link Motor#BENZIN} alebo {@link Motor#DIESEL}.
     * @param pocetMiest Počet miest pre pasažierov na lodi.
     * @param spotreba Spotreba paliva v litroch na 100 km.
     * @param objem Objem lode v metroch kubických.
     * @param objemNadrze Celková kapacita nádrže na palivo v litroch.
     * @param typPaliva Typ paliva, ktoré loď používa ({@link Palivo}).
     * @param municia Počet nábojov, ktoré loď nesie.
     * @param cenaZaDen Cena za prenájom lode na jeden deň v eurách.
     * @throws IllegalArgumentException Ak motor nie je {@link Motor#BENZIN} ani {@link Motor#DIESEL}.
     */
    public BojovaLod(Motor motor, int pocetMiest, float spotreba, float objem, float objemNadrze,
                     Palivo typPaliva, int municia, float cenaZaDen) {
        super(motor, pocetMiest, spotreba, cenaZaDen);
        if (motor != Motor.BENZIN && motor != Motor.DIESEL) {
            throw new IllegalArgumentException("BojovaLod musí mať motor BENZIN alebo DIESEL!");
        }
        this.objem = objem;
        this.objemNadrze = objemNadrze;
        this.typPaliva = typPaliva;
        setMunicia(municia);
    }

    /**
     * Natankuje loď daným typom paliva. Tankovanie je úspešné iba ak zadané palivo
     * zodpovedá typu paliva lode.
     *
     * @param palivo Typ paliva na tankovanie ({@link Palivo}).
     * @return {@code true} ak bolo tankovanie úspešné, {@code false} ak zadané palivo
     *         nezodpovedá typu paliva lode.
     */
    @Override
    public boolean tankuj(Palivo palivo) {
        if (palivo == typPaliva) {
            stavNadrze = objemNadrze;
            return true;
        }
        return false;
    }

    /**
     * Vracia objem lode.
     *
     * @return Objem lode v metroch kubických.
     */
    public float getObjem() {
        return objem;
    }

    /**
     * Vracia celkovú kapacitu nádrže lode.
     *
     * @return Kapacita nádrže v litroch.
     */
    public float getObjemNadrze() {
        return objemNadrze;
    }

    /**
     * Vracia aktuálny stav nádrže lode.
     *
     * @return Aktuálne množstvo paliva v nádrži v litroch.
     */
    public float getStavNadrze() {
        return stavNadrze;
    }

    /**
     * Vracia typ paliva, ktoré loď používa.
     *
     * @return Typ paliva ({@link Palivo}) používaný loďou.
     */
    public Palivo getPalivo() {
        return typPaliva;
    }

    /**
     * Vracia názov lode, ktorý je tvorený na základe typu motora.
     *
     * @return Názov lode vo formáte "BojovaLod_[typ motora]".
     */
    @Override
    public String getNazov() {
        return "BojovaLod_" + getMotor().toString();
    }

    /**
     * Vytvára identifikujúci reťazec pre bojovú loď, ktorý obsahuje informácie o
     * motore, objeme, palive, stave nádrže a munícii.
     *
     * @return Reťazec vo formáte "[zdedené info], Objem: [objem]m³, Palivo: [palivo],
     *         Nádrž: [stav]/[objemNadrze]L, Munícia: [municia]".
     */
    @Override
    public String identifikujSa() {
        return super.identifikujSa() + ", Objem: " + objem + "m³, Palivo: " + typPaliva +
               ", Nádrž: " + stavNadrze + "/" + objemNadrze + "L, Munícia: " + getMunicia();
    }
}