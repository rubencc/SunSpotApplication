/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

import org.spot.application.Interfaces.ISensor;

/**
 *
 * @author rubencc
 */
public abstract class Sensor implements ISensor {

    String sensorName;

    public Sensor(String name) {
        this.sensorName = name;
    }

    public abstract String getMeasure();

    public String getName() {
        return this.sensorName;
    }

    public abstract boolean isActive();
}
