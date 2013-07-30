/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.ThresholdKeeper.Factory;

/**
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

    public static ThresholdManager getInstance() {
        return INSTANCE;
    }

    public void launchLightThresholdKeeper() {
        new Thread(this.light).start();
    }

    public void launchTemperatureThresholdKeeper() {
        new Thread(this.temperature).start();
    }

    public void stopLightThresholdKeeper() {
        this.light.finish();
    }

    public void stopTemperatureThresholdKeeper() {
        this.temperature.finish();
    }

    public void setLightThresholdKeeper(int maxValue, int minValue, long period) {
        this.light.finish();
        this.light.setMaxValue(maxValue);
        this.light.setMinValue(minValue);
        this.light.setPeriod(period);
        new Thread(this.light).start();
    }

    public void setTemperatureThresholdKeeper(int maxValue, int minValue, long period) {
        this.temperature.finish();
        this.temperature.setMaxValue(maxValue);
        this.temperature.setMinValue(minValue);
        this.temperature.setPeriod(period);
        new Thread(this.light).start();
    }
}
