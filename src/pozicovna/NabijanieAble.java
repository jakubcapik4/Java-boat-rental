package pozicovna;

/**
 * Rozhranie definujúce schopnosť dopravného prostriedku nabíjať batériu.
 * Implementujúce triedy (napr. elektrické lode) musia poskytnúť logiku na nabíjanie
 * batérie zadaným množstvom energie v kilowatthodinách (kWh).
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public interface NabijanieAble {
    /**
     * Nabije batériu dopravného prostriedku zadaným množstvom energie.
     *
     * @param kw Množstvo energie na nabitie v kilowatthodinách (kWh).
     * @return Aktuálny stav batérie po nabití (zvyčajne v percentách, 0-100).
     */
    float charge(float kw);
}