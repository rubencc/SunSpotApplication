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
        this.light.setRunning(true);
        new Thread(this.light).start();
    }

    /**
     * Lanza la ejecución del hilo vigilante del umbral de temperatura
     */
    public void launchTemperatureThresholdKeeper() {
        this.temperature.setRunning(true);
        new Thread(this.temperature).start();
    }

    /**
     * Detiene la ejecución del hilo vigilante del umbral de luz
     */
    public void stopLightThresholdKeeper() {
        this.light.setRunning(false);
        this.light.finish();
    }

    /**
     * Detiene la ejecución del hilo vigilante del umbral de temperatura
     */
    public void stopTemperatureThresholdKeeper() {
        this.temperature.setRunning(false);
        this.temperature.finish();
    }

    /**
     * Configura los parametros para el vigilante del umbral de luz
     *
     * @param maxValue -- Valor maximo del umbral
     * @param minValue -- Valor minimo del umbral
     * @param period -- Perido de toma de medidas
     */
    public void setLightThresholdKeeper(double maxValue, double minValue, long period) {
        this.light.finish();
        this.light.setMaxValue(maxValue);
        this.light.setMinValue(minValue);
        this.light.setPeriod(period);
        this.light.setRunning(true);
        new Thread(this.light).start();
    }

    /**
     * Configura los parametros para el vigilante del umbral de temperatura
     *
     * @param maxValue -- Valor maximo del umbral
     * @param minValue -- Valor minimo del umbral
     * @param period -- Perido de toma de medidas
     */
    public void setTemperatureThresholdKeeper(double maxValue, double minValue, long period) {
        this.temperature.finish();
        this.temperature.setMaxValue(maxValue);
        this.temperature.setMinValue(minValue);
        this.temperature.setPeriod(period);
        this.temperature.setRunning(true);
        new Thread(this.temperature).start();
    }

    /**
     * Devuelve el estado del hilo vigilante de la temperatura
     *
     * @return
     */
    public String[] getStatusTemperatureThresholdKeeper() {
        return this.temperature.getStatus();
    }

    /**
     * Devuelve el estado del hilo vigilante de la luz
     *
     * @return
     */
    public String[] getStatusLightThresholdKeeperr() {
        return this.light.getStatus();
    }
}
