package org.spot.application.Peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITemperatureInput;

/**
 * Clase que implementa las funcionalidades del sensor de temperatura
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public abstract class TemperatureSensor extends Sensor {

    protected ITemperatureInput tempSensor;
    private static final String TEMPERATURE = "Temperature Sensor";
    protected final String ACTIVE = "Active";
    protected final String NOT_ACTIVE = "Not Active";
    protected final String NOT_INSTALLED = "NOT INSTALLED";
    private boolean active;

    public TemperatureSensor() {
        super(TEMPERATURE);
        this.tempSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
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
