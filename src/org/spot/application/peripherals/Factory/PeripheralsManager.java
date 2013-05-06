/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

/**
 *
 * @author rubencc
 */
public class PeripheralsManager {

    private Sensor lightSensor;
    private Sensor temperatureSensor;
    private LedArray ledArray;
    private Accelerometer accelerometer;

    public PeripheralsManager() {
        this.lightSensor = new NotLightSensor();
    }

    /**
     * Especifica el estado de los sensores de luz.
     *
     * @param state
     */
    public void setLightSensorState(String state) {

        if (state.equals("on")) {
            this.lightSensor = new LightActiveSensor();
        }

        if (state.equals("off")) {
            this.lightSensor = new LightNotActiveSensor();
        }

        if (state.equals("notpresent")) {
            this.lightSensor = new NotLightSensor();
        }

    }

    public String getLightMeasure() {
        return this.lightSensor.getMeasure();
    }
}
