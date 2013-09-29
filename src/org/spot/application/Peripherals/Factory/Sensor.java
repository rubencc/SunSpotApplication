package org.spot.application.Peripherals.Factory;

import org.spot.application.Interfaces.ISensor;

/**
 * Clase abstracta que implementa los metodos para las funcionalidades de los
 * sensores de luz y temperatura
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public abstract class Sensor implements ISensor {

    String sensorName;

    public Sensor(String name) {
        this.sensorName = name;
    }

    /**
     * Devuelve la medida del sensor
     *
     * @return valor medido
     */
    public abstract String getMeasure();

    /**
     * Devuelve el nombre del periferico
     *
     * @return Nombre del periferico
     */
    public String getName() {
        return this.sensorName;
    }

    /**
     * Devuelve el estado de actividad del periferico
     *
     * @return false/true
     */
    public abstract boolean isActive();
}
