/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITemperatureInput;

/**
 *
 * @author rubencc
 */
public abstract class TemperatureSensor extends Sensor {

    protected ITemperatureInput tempSensor;
    private static final String TEMPERATURE = "temperature";

    public TemperatureSensor() {
        super(TEMPERATURE);
        tempSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
    }
}
