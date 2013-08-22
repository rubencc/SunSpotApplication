package org.spot.application.ThresholdKeeper;

/**
 * Gestor de los hilos que vigilan los ubrales de luz y temperatura
 *
 * @author rubencc
 */
public class ThresholdManager {

    private static ThresholdManager INSTANCE = new ThresholdManager();
    private ThresholdKeeper light;
    private ThresholdKeeper temperature;

    private ThresholdManager() {
        this.light = new LightThresholdKeeper();
        this.temperature = new TemperatureThresholdKeeper();

    }

    /**
     * Solo una instancia en ejecucion
     *
     * @return Instancia del gestor
     */
    public static ThresholdManager getInstance() {
        return INSTANCE;
    }

    /**
     * Lanza la ejecución del hilo vigilante del umbral de luz
     */
    public void launchLightThresholdKeeper() {
        new Thread(this.light).start();
    }

    /**
     * Lanza la ejecución del hilo vigilante del umbral de temperatura
     */
    public void launchTemperatureThresholdKeeper() {
        new Thread(this.temperature).start();
    }

    /**
     * Detiene la ejecución del hilo vigilante del umbral de luz
     */
    public void stopLightThresholdKeeper() {
        this.light.finish();
    }

    /**
     * Detiene la ejecución del hilo vigilante del umbral de temperatura
     */
    public void stopTemperatureThresholdKeeper() {
        this.temperature.finish();
    }

    /**
     * Configura los parametros para el vigilante del umbral de luz
     *
     * @param maxValue -- Valor maximo del umbral
     * @param minValue -- Valor minimo del umbral
     * @param period -- Perido de toma de medidas
     */
    public void setLightThresholdKeeper(int maxValue, int minValue, long period) {
        this.light.finish();
        this.light.setMaxValue(maxValue);
        this.light.setMinValue(minValue);
        this.light.setPeriod(period);
        new Thread(this.light).start();
    }

    /**
     * Configura los parametros para el vigilante del umbral de temperatura
     *
     * @param maxValue -- Valor maximo del umbral
     * @param minValue -- Valor minimo del umbral
     * @param period -- Perido de toma de medidas
     */
    public void setTemperatureThresholdKeeper(int maxValue, int minValue, long period) {
        this.temperature.finish();
        this.temperature.setMaxValue(maxValue);
        this.temperature.setMinValue(minValue);
        this.temperature.setPeriod(period);
        new Thread(this.temperature).start();
    }
}
