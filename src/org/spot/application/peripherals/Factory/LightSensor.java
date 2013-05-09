/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;

/**
 *
 * @author rubencc
 */
public abstract class LightSensor extends Sensor {

    protected ILightSensor lightSensor;
    private static final String LIGHT = "light";

    public LightSensor() {
        super(LIGHT);
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
    }
}
