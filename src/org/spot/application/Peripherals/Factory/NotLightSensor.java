package org.spot.application.Peripherals.Factory;

/**
 * Clase que modela el comportamiento del sensor de luz cuando no esta instalado
 *
 * @author rubencc
 */
public class NotLightSensor extends LightSensor {

    private final String MESSAGE = "This sensor is not installed";

    public NotLightSensor() {
        super();
        this.setActive(false);
    }

    public String getMeasure() {
        return MESSAGE;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.NOT_INSTALLED);
    }
}
