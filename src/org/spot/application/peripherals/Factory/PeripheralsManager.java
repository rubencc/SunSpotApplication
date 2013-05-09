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

    /**
     * Especifica el estado de los sensores de temperatura.
     *
     * @param state
     */
    public void setTemperatureSensorState(String state) {

        if (state.equals("on")) {
            this.temperatureSensor = new TemperatureActiveSensor();
        }

        if (state.equals("off")) {
            this.temperatureSensor = new TemperatureNotActiveSensor();
        }

        if (state.equals("notpresent")) {
            this.temperatureSensor = new NotTemperatureSensor();
        }

    }

    /**
     * Medida del sensor de luz.
     *
     * @return
     */
    public String getLightMeasure() {
        return this.lightSensor.getMeasure();
    }

    /**
     * Medida del sensor de temperatura.
     *
     * @return
     */
    public String getTemperatureMeasure() {
        return this.temperatureSensor.getMeasure();
    }
}
