package pozicovna;

/**
 * Enum reprezentujúci rôzne typy lodí dostupné v požičovni.
 * Každá hodnota označuje špecifickú kategóriu lode, ktorá môže byť
 * použitá pre rôzne účely (napr. rybolov, plachtenie, kajakovanie).
 *
 * @author [Jakub Capík]
 * @version 1.0
 * @since 2025-05-11
 */
public enum BoatType {
    /**
     * Typ lode s kabínou, vhodný pre dlhšie výlety a ubytovanie.
     */
    KABINOVA,
    
    /**
     * Typ plachetnice, určený na plachtenie a športové aktivity.
     */
    PLACHTOVA,
    
    /**
     * Typ lode určený na rybolov, vybavený potrebným vybavením.
     */
    RYBARSKA,
    
    /**
     * Typ kanoe, ľahká loď vhodná na pokojnú vodu a rekreáciu.
     */
    KANOE;
}