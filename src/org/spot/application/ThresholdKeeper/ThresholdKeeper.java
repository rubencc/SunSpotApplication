package org.spot.application.ThresholdKeeper;

import org.spot.application.Network.PDU;
import org.spot.application.Network.PeerConnection;
import org.spot.application.Peripherals.Manager.PeripheralsManager;

/**
 * Clase abstracta para el funcionamiento de los umbrales
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
abstract class ThresholdKeeper implements Runnable {

    private long period;
    private double minValue;
    private double maxValue;
    protected boolean runCond;
    protected boolean running;
    protected PeripheralsManager sensor;
    protected PeerConnection pCon;
    protected final int QUEUE_ALERT = 0x20;
    protected final int THRESHOLD_INFO = 0x97;

    public ThresholdKeeper() {
        this.sensor = PeripheralsManager.getInstance();
        this.pCon = PeerConnection.getInstance();
        this.runCond = false;
        this.running = false;
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
    public double getMinValue() {
        return minValue;
    }

    /**
     * @return Valor maximo del umbral
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * @return Nombre del vigilante de umbral
     */
    public abstract String getName();

    /**
     * @return Estado del hilo
     */
    public abstract String[] getStatus();

    /**
     * Envia una PDU con la informacion
     *
     * @param pdu -- Informacion que se va a enviar
     */
    protected abstract void sendPDU(PDU pdu);

    /**
     * @param period -- Periodo de toma de muestras
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * @param minValue -- Valor minimo del umbral
     */
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    /**
     * @param maxValue -- Valor maximo del umbral
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Detiene la ejecución del hilo que vigila el umbral
     */
    public void finish() {
        this.runCond = false;
        this.running = false;
        this.sensor.ledSetOff();
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
}
