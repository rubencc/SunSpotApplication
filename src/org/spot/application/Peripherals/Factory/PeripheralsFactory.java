/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

import org.spot.application.Peripherals.Actuators.Accelerometer;
import org.spot.application.Peripherals.Actuators.AccelerometerActive;
import org.spot.application.Peripherals.Actuators.AccelerometerNotActive;
import org.spot.application.Peripherals.Actuators.LedArray;
import org.spot.application.Peripherals.Actuators.LedArrayActive;
import org.spot.application.Peripherals.Actuators.LedArrayNotActive;
import org.spot.application.Peripherals.Actuators.NotAccelerometer;
import org.spot.application.Peripherals.Actuators.NotLedArray;
import org.spot.application.Peripherals.Sensors.LightActiveSensor;
import org.spot.application.Peripherals.Sensors.LightNotActiveSensor;
import org.spot.application.Peripherals.Sensors.NotLightSensor;
import org.spot.application.Peripherals.Sensors.NotTemperatureSensor;
import org.spot.application.Peripherals.Sensors.Sensor;
import org.spot.application.Peripherals.Sensors.TemperatureActiveSensor;
import org.spot.application.Peripherals.Sensors.TemperatureNotActiveSensor;

/**
 * Factoria que implementa IPeripheralsFactory y genera clases concretas para el
 * estado del sensor o actuador
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class PeripheralsFactory implements IPeripheralsFactory {

    public Sensor getLightSensor(String state) {
        switch (this.getStatus(state)) {
            case 0:
                return new LightActiveSensor();
            case 1:
                return new LightNotActiveSensor();
            case 2:
                return new NotLightSensor();
            default:
                return new NotLightSensor();
        }
    }

    public Sensor getTemperatureSensor(String state) {
        switch (this.getStatus(state)) {
            case 0:
                return new TemperatureActiveSensor();
            case 1:
                return new TemperatureNotActiveSensor();
            case 2:
                return new NotTemperatureSensor();
            default:
                return new NotTemperatureSensor();
        }
    }

    public Accelerometer getAccelerometer(String state) {
        switch (this.getStatus(state)) {
            case 0:
                return new AccelerometerActive();
            case 1:
                return new AccelerometerNotActive();
            case 2:
                return new NotAccelerometer();
            default:
                return new NotAccelerometer();
        }
    }

    public LedArray getLedArray(String state) {
        switch (this.getStatus(state)) {
            case 0:
                return new LedArrayActive();
            case 1:
                return new LedArrayNotActive();
            case 2:
                return new NotLedArray();
            default:
                return new NotLedArray();
        }
    }

    private int getStatus(String state) {
        int stateAsInteger = 2;

        if (state.equals("on")) {
            stateAsInteger = 0;
        }

        if (state.equals("off")) {
            stateAsInteger = 1;
        }

        if (state.equals("notpresent")) {
            stateAsInteger = 2;
        }

        return stateAsInteger;
    }
}
