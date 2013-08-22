package org.spot.application.Peripherals.Factory;

/**
 * Clase que modela el comportamiento del sensor de luz cuando no esta activo
 *
 * @author rubencc
 */
public class LightNotActiveSensor extends LightSensor {

    private final String MESSAGE = "This sensor is off";

    public LightNotActiveSensor() {
        super();
        this.setActive(false);
    }

    public String getMeasure() {
        return MESSAGE;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.NOT_ACTIVE);
    }
}
