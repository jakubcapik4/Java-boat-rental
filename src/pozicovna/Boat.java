package pozicovna;

/**
 * Abstraktná trieda reprezentujúca všeobecnú loď ako typ dopravného prostriedku.
 * Trieda rozširuje {@link DopravnyProstriedok} a definuje základné vlastnosti
 * ako veľkosť a typ lode. Je určená na použitie ako základ pre konkrétne typy lodí.
 *
 * @author [Jakub Capík]
 * @version 1.0
 * @since 2025-05-11
 */
public abstract class Boat extends DopravnyProstriedok {

    private Velkost velkost;
    private BoatType typ;

    /**
     * Vytvorí novú inštanciu lode s určenou veľkosťou, typom a cenou za deň.
     *
     * @param velkost Veľkosť lode, určuje jej rozmery (napr. S, M, L).
     * @param typ Typ lode, určuje špecifické kategórie (napr. KANOE, KABINOVA).
     * @param cenaZaDen Cena za prenájom lode na jeden deň v eurách.
     */
    public Boat(Velkost velkost, BoatType typ, float cenaZaDen) {
        super(cenaZaDen);
        this.velkost = velkost;
        this.typ = typ;
    }

    /**
     * Vracia veľkosť lode.
     *
     * @return Objekt typu {@link Velkost} reprezentujúci veľkosť lode.
     */
    public Velkost getVelkost() {
        return velkost;
    }

    /**
     * Vracia typ lode.
     *
     * @return Objekt typu {@link BoatType} reprezentujúci typ lode.
     */
    public BoatType getTyp() {
        return typ;
    }

    /**
     * Vytvára identifikujúci reťazec pre loď, ktorý obsahuje jej veľkosť a typ.
     *
     * @return Reťazec vo formáte "Boat, Veľkosť: [veľkosť], Typ: [typ]".
     */
    @Override
    public String identifikujSa() {
        return "Boat, Veľkosť: " + velkost + ", Typ: " + typ;
    }
}