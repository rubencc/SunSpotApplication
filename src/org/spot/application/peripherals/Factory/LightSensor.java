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
    private static final String LIGHT = "Light Sesnor";
    protected final String ACTIVE = "Active";
    protected final String NOT_ACTIVE = "Not Active";
    protected final String NOT_INSTALLED = "NOT INSTALLED";

    public LightSensor() {
        super(LIGHT);
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
    }

    public abstract String getStatus();
}
