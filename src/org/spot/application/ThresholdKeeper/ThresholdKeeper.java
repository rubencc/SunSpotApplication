package org.spot.application.ThresholdKeeper;

import org.spot.application.Network.PeerConnection;
import org.spot.application.Peripherals.Factory.PeripheralsManager;

/**
 * Clase abstracta para el funcionamiento de los umbrales
 *
 * @author rubencc
 */
abstract class ThresholdKeeper implements Runnable {

    private long period;
    private int minValue;
    private int maxValue;
    protected boolean runCond;
    protected PeripheralsManager sensor;
    protected PeerConnection pCon;
    protected final int QUEUE_ALERT = 0x20;

    public ThresholdKeeper() {
        this.sensor = PeripheralsManager.getInstance();
        this.pCon = PeerConnection.getInstance();
        this.runCond = true;
    }

    /**
     * @return Periodo de toma de muestras
     */
    public long getPeriod() {
        return period;
    }

    /**
     * @return Valor minimo del umbral
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * @return Valor maximo del umbral
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * @return Nombre del vigilante de umbral
     */
    public abstract String getName();

    /**
     * @param period -- Periodo de toma de muestras
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * @param minValue -- Valor minimo del umbral
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * @param maxValue -- Valor maximo del umbral
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Detiene la ejecución del hilo que vigila el umbral
     */
    public void finish() {
        this.runCond = false;
        this.sensor.ledSetOff();
    }
}
