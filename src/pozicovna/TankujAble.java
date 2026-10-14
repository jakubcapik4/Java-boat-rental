package pozicovna;

/**
 * Rozhranie definujúce schopnosť dopravného prostriedku tankovať palivo.
 * Implementujúce triedy (napr. lode so spaľovacím motorom) musia poskytnúť logiku
 * na tankovanie zadaným typom paliva.
 *
 * @author Jakub Capík
 * @version 1.0
 * @since 2025-05-11
 */
public interface TankujAble {
    /**
     * Natankuje dopravný prostriedok zadaným typom paliva.
     *
     * @param palivo Typ paliva na tankovanie ({@link Palivo}).
     * @return {@code true}, ak bolo tankovanie úspešné, {@code false}, ak palivo nie je kompatibilné.
     */
    boolean tankuj(Palivo palivo);
}