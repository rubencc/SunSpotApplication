package org.spot.application.Peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;

/**
 * Clase que implementa las funcionalidades del sensor de luz
 *
 * @author rubencc
 */
public abstract class LightSensor extends Sensor {

    protected ILightSensor lightSensor;
    private static final String LIGHT = "Light Sensor";
    protected final String ACTIVE = "Active";
    protected final String NOT_ACTIVE = "Not Active";
    protected final String NOT_INSTALLED = "NOT INSTALLED";
    private boolean active;

    public LightSensor() {
        super(LIGHT);
        this.lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
        this.active = false;
    }

    public abstract String getStatus();

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
