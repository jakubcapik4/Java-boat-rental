package pozicovna;

/**
 * Enum reprezentujúci rôzne typy motorov používaných v dopravných prostriedkoch požičovne.
 * Každá hodnota označuje špecifický typ motora, ktorý môže byť použitý v lodiach
 * alebo iných prostriedkoch, a určuje ich pohonný systém.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public enum Motor {
    /**
     * Benzínový motor, spaľovací motor poháňaný benzínom.
     */
    BENZIN,

    /**
     * Naftový motor, spaľovací motor poháňaný naftou.
     */
    DIESEL,

    /**
     * Hybridný motor, kombinácia spaľovacieho a elektrického pohonu.
     */
    HYBRID,

    /**
     * Elektrický motor, poháňaný batériou.
     */
    ELEKTRICKY
}