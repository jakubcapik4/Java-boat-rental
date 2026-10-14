package pozicovna;

/**
 * Trieda {@code VyletnaLod} reprezentuje výletnú loď s hybridným pohonom, 
 * ktorá môže byť poháňaná na palivo aj elektrickú energiu.
 * Dedi z triedy {@code Lod} a implementuje rozhrania {@code NabijanieAble} a {@code TankujAble}.
 *
 * Obsahuje informácie o stave nádrže, batérie, dojazde a type paliva.
 *
 * @author (Jakub Capík )
 */
public class VyletnaLod extends Lod implements NabijanieAble, TankujAble {

    private float objemNadrze;
    private float stavNadrze;
    private Palivo typPaliva;
    private float velkostBaterie;
    private float stavBaterie;
    private float dojazd;

    /**
     * Konštruktor pre vytvorenie inštancie výletnej lode.
     *
     * @param motor typ motora, musí byť {@code Motor.HYBRID}
     * @param pocetMiest počet miest na lodi
     * @param spotreba spotreba paliva v L/100km
     * @param objemNadrze maximálna kapacita nádrže
     * @param stavNadrze aktuálny stav nádrže
     * @param typPaliva typ paliva, ktoré loď používa
     * @param velkostBaterie kapacita batérie v kWh
     * @param stavBaterie aktuálny stav batérie v %
     * @param dojazd maximálny dojazd na plne nabitú batériu
     * @param cenaZaDen cena prenájmu na deň
     * @throws IllegalArgumentException ak motor nie je {@code Motor.HYBRID}
     */
    public VyletnaLod(Motor motor, int pocetMiest, float spotreba, float objemNadrze, float stavNadrze,
                      Palivo typPaliva, float velkostBaterie, float stavBaterie, float dojazd, float cenaZaDen) {
        super(motor, pocetMiest, spotreba, cenaZaDen);
        if (motor != Motor.HYBRID) {
            throw new IllegalArgumentException("VyletnaLod musí mať motor HYBRID!");
        }
        this.objemNadrze = objemNadrze;
        this.stavNadrze = stavNadrze;
        this.typPaliva = typPaliva;
        this.velkostBaterie = velkostBaterie;
        this.stavBaterie = stavBaterie;
        this.dojazd = dojazd;
        setMunicia(0);
    }

    /**
     * Štartuje loď, ak má dostatok paliva alebo batérie.
     *
     * @return {@code true} ak bola loď úspešne naštartovaná, inak {@code false}
     */
    @Override
    public boolean nastartuj() {
        if (stavNadrze > 0 || stavBaterie > 0) {
            setNastartovane(true);
            setAktualnaSpotreba(getSpotreba());
            return true;
        }
        return false;
    }

    /**
     * Pohne loďou na zadaný počet kilometrov, ak má dostatok energie.
     *
     * @param pocetKm počet kilometrov, ktoré má loď prejsť
     * @return {@code true} ak sa pohla, inak {@code false}
     */
    @Override
    public boolean move(float pocetKm) {
        if (getNastartovane()) {
            float potrebnePalivo = pocetKm * getAktualnaSpotreba() / 100;
            if (stavNadrze >= potrebnePalivo || stavBaterie >= potrebnePalivo) {
                if (stavNadrze >= potrebnePalivo) {
                    stavNadrze -= potrebnePalivo;
                } else {
                    stavBaterie -= potrebnePalivo;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Nabije batériu o zadané množstvo energie.
     *
     * @param kw množstvo energie v kWh
     * @return nový stav batérie v %
     */
    @Override
    public float charge(float kw) {
        float newStav = stavBaterie + (kw / velkostBaterie) * 100;
        stavBaterie = Math.min(newStav, 100);
        return stavBaterie;
    }

    /**
     * Natankuje loď, ak typ paliva zodpovedá.
     *
     * @param palivo typ paliva
     * @return {@code true} ak bolo tankovanie úspešné, inak {@code false}
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
     * Získa veľkosť batérie.
     *
     * @return veľkosť batérie v kWh
     */
    public float getVelkostBaterie() {
        return velkostBaterie;
    }

    /**
     * Získa aktuálny stav batérie.
     *
     * @return stav batérie v %
     */
    public float getStavBaterie() {
        return stavBaterie;
    }

    /**
     * Vypočíta aktuálny dojazd podľa stavu batérie.
     *
     * @return dojazd v kilometroch
     */
    public float getDojazd() {
        return dojazd * (stavBaterie / 100.0f);
    }

    /**
     * Získa objem nádrže.
     *
     * @return objem nádrže v litroch
     */
    public float getObjemNadrze() {
        return objemNadrze;
    }

    /**
     * Získa aktuálny stav nádrže.
     *
     * @return stav nádrže v litroch
     */
    public float getStavNadrze() {
        return stavNadrze;
    }

    /**
     * Získa typ paliva.
     *
     * @return typ paliva
     */
    public Palivo getTypPaliva() {
        return typPaliva;
    }

    /**
     * Získa názov lode vo formáte {@code VyletnaLod_TYP_MOTORA}.
     *
     * @return názov lode
     */
    @Override
    public String getNazov() {
        return "VyletnaLod_" + getMotor().toString();
    }

    /**
     * Vráti identifikačný reťazec lode vrátane stavu nádrže, batérie a dojazdu.
     *
     * @return reťazec s popisom stavu lode
     */
    @Override
    public String identifikujSa() {
        return super.identifikujSa() + ", Nádrž: " + stavNadrze + "/" + objemNadrze + "L, " +
               "Batéria: " + stavBaterie + "%/" + velkostBaterie + "kWh, Dojazd: " + getDojazd() + "km";
    }
}
