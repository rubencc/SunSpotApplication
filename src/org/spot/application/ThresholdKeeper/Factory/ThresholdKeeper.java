/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.ThresholdKeeper.Factory;

import org.spot.application.Peripherals.Factory.PeripheralsManager;

/**
 *
 * @author rubencc
 */
abstract class ThresholdKeeper implements Runnable {

    private long period;
    private int minValue;
    private int maxValue;
    protected boolean runCond;
    protected PeripheralsManager sensor;

    public ThresholdKeeper() {
        this.sensor = PeripheralsManager.getInstance();
        this.runCond = true;
    }

    /**
     * @return the period
     */
    public long getPeriod() {
        return period;
    }

    /**
     * @return the minValue
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * @return the maxValue
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * @return the name
     */
    public abstract String getName();

    /**
     * @param period the period to set
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void finish() {
        this.runCond = false;
    }
}
