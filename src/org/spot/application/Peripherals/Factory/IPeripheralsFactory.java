package org.spot.application.Peripherals.Factory;

import org.spot.application.Peripherals.Actuators.Accelerometer;
import org.spot.application.Peripherals.Actuators.LedArray;
import org.spot.application.Peripherals.Sensors.Sensor;

/**
 * Factoria para generar clases concretas para el estado del sensor o actuador
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public interface IPeripheralsFactory {

    Sensor getLightSensor(String state);

    Sensor getTemperatureSensor(String state);

    Accelerometer getAccelerometer(String state);

    LedArray getLedArray(String state);
}
