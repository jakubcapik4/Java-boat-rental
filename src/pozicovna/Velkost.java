package pozicovna;

/**
 * Enum reprezentujúci rôzne veľkosti dopravných prostriedkov, ako sú vodné skútre.
 * Každá hodnota označuje špecifickú veľkostnú kategóriu, ktorá môže ovplyvniť
 * kapacitu alebo použitie prostriedku.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public enum Velkost {
    /**
     * Extra malá veľkosť (XS), vhodná pre jednu osobu alebo minimálnu kapacitu.
     */
    XS,

    /**
     * Malá veľkosť (S), vhodná pre 1-2 osoby.
     */
    S,

    /**
     * Stredná veľkosť (M), vhodná pre 2-4 osoby.
     */
    M,

    /**
     * Veľká veľkosť (L), vhodná pre 4-6 osôb.
     */
    L,

    /**
     * Extra veľká veľkosť (XL), vhodná pre 6 a viac osôb.
     */
    XL;
}