/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Manager;

import org.spot.application.Peripherals.Actuators.Accelerometer;
import org.spot.application.Peripherals.Actuators.LedArray;
import org.spot.application.Peripherals.Sensors.Sensor;

/**
 * Interfaz que define la el comportamiento de los perifericos
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public interface IPeripheralsManager {

    /**
     * Medida del acelerometro X
     *
     * @return Medida del acelerometro
     */
    String getAcceletometerX();

    /**
     * Medida del acelerometro Y
     *
     * @return Medida del acelerometro
     */
    String getAcceletometerY();

    /**
     * Medida del acelerometro Z
     *
     * @return Medida del acelerometro
     */
    String getAcceletometerZ();

    /**
     * Medida del sensor de luz.
     *
     * @return Medida del sensor de luz
     */
    String getLightMeasure();

    /**
     * Devuelve el estado de todos los perifericos del dispositivo
     *
     * @return String[] con el estado de los dispositivos
     */
    String[] getStatus();

    /**
     * Devuelve el estado del acelerometro
     *
     * @return String con el estado del sensor
     */
    String getStatusAcelerometer();

    /**
     * Devuelve el estado del array de leds
     *
     * @return String con el estado del array
     */
    String getStatusLedArray();

    /**
     * Devuelve el estado del sensor de luz
     *
     * @return String con el estado del sensor
     */
    String getStatusLightSensor();

    /**
     * Devuelve el estado del sensor de temperatura
     *
     * @return String con el estado del sensor
     */
    String getStatusTemperatureSensor();

    /**
     * Medida del sensor de temperatura.
     *
     * @return Medida del sensor de temperatura
     */
    String getTemperatureMeasure();

    /**
     * Indica si el array de leds esta mostrando algun numero
     *
     * @return false/true segun se haya podido ejecutar la operacion
     */
    boolean hasANumber();

    /**
     * Devuelve el estado de actividad del array de leds
     *
     * @return false/true
     */
    boolean isLedArrayActive();

    /**
     * Devuelve el estado de actividad del sensor de luz
     *
     * @return false/true
     */
    boolean isLightSensorActive();

    /**
     * Devuelve el estado de actividad del sensor de temperatura
     *
     * @return false/true
     */
    boolean isTemperatureSensorActive();

    /**
     * Establece el color de los leds.
     *
     * @param color - Codigo de color
     * @return false/true segun se haya podido ejecutar la operacion
     */
    boolean ledSetColor(int color);

    /**
     * Pone todos los leds en off.
     *
     * @return Medida del acelerometro
     */
    boolean ledSetOff();

    /**
     * Pone todos los leds en el estado indicado.
     *
     * @param condition - false/true
     * @return false/true segun se haya podido ejecutar la operacion
     */
    boolean ledSetOn(boolean condition);

    /**
     * Activa los leds para representar la secuencia numerica.
     *
     * @param value - Valor en decimal que representaran los leds
     * @return false/true segun se haya podido ejecutar la operacion
     */
    boolean ledSetOn(int value);

    /**
     * Activa los leds con la ultima configuracion conocida.
     *
     * @return false/true segun se haya podido ejecutar la operacion
     */
    boolean ledSetOn();

    /**
     * Establece la clase Accelerometer que manejara el manager
     *
     */
    void setAccelerometer(Accelerometer accelerometer);

    /**
     * Establece la clase LedArray que manejara el manager
     *
     */
    void setLedArray(LedArray ledArray);

    /**
     * Establece la clase LightSensor que manejara el manager
     *
     */
    void setLightSensor(Sensor lightSensor);

    /**
     * Establece la clase TemperatureSensor que manejara el manager
     *
     */
    void setTemperatureSensor(Sensor temperatureSensor);
}
