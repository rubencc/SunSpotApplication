/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import com.sun.spot.resources.transducers.LEDColor;

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
        this.lightSensor = new LightActiveSensor();
        this.temperatureSensor = new TemperatureActiveSensor();
        this.accelerometer = new AccelerometerActive();
        this.ledArray = new LedArrayActive();
        this.ledArray.setColor(9);
    }

    /**
     * Especifica el estado de los sensores de luz.
     *
     * @param state "on","off","notpresent"
     */
    public void setLightSensorStatus(String state) {

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
     * @param state "on","off","notpresent"
     */
    public void setTemperatureSensorStatus(String state) {

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
     * Especifica el estado del acelerometro.
     *
     * @param state
     */
    public void setAccelerometerStatus(String state) {
        if (state.equals("on")) {
            this.accelerometer = new AccelerometerActive();
        }

        if (state.equals("off")) {
            this.accelerometer = new AccelerometerNotActive();
        }

        if (state.equals("notpresent")) {
            this.accelerometer = new NotAccelerometer();
        }
    }

    /**
     * Especifica el estado del array de leds.
     *
     * @param state
     */
    public void setLedArrayStatus(String state) {
        if (state.equals("on")) {
            this.ledArray = new LedArrayActive();
        }

        if (state.equals("off")) {
            this.ledArray = new LedArrayNotActive();
        }

        if (state.equals("notpresent")) {
            this.ledArray = new NotLedArray();
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

    /**
     * Medida del acelerometro X
     *
     * @return
     */
    public String getAcceletometerX() {
        return this.accelerometer.getX();
    }

    /**
     * Medida del acelerometro Y
     *
     * @return
     */
    public String getAcceletometerY() {
        return this.accelerometer.getY();
    }

    /**
     * Medida del acelerometro Z
     *
     * @return
     */
    public String getAcceletometerZ() {
        return this.accelerometer.getZ();
    }

    /**
     * Pone todos los leds en off.
     *
     * @return
     */
    public boolean ledSetOff() {
        return this.ledArray.setOff();
    }

    /**
     * Pone todos los leds en el estado indicado.
     *
     * @param on
     * @return
     */
    public boolean ledSetOn(boolean on) {
        return this.ledArray.setOn(on);
    }

    /**
     * Activa los leds para representar la secuencia numerica.
     *
     * @param value
     * @return
     */
    public boolean ledSetOn(int value) {
        return this.ledArray.setOn(value);
    }

    /**
     * Activa los leds con la ultima configuracion conocida.
     *
     * @return
     */
    public boolean ledSetOn() {
        return this.ledArray.setOn();
    }

    /**
     * Establece el color de los leds.
     *
     * @param color
     * @return
     */
    public boolean ledSetColor(int color) {
        return this.ledArray.setColor(color);
    }

    public String[] getStatus() {
        String[] _status = new String[4];
        _status[0] = this.lightSensor.getStatus();
        _status[1] = this.temperatureSensor.getStatus();
        _status[2] = this.accelerometer.getStatus();
        _status[3] = this.ledArray.getStatus();
        return _status;
    }
}
