package org.spot.application.Peripherals.Sensors;

/**
 * Clase que modela el comportamiento del sensor de temperatura cuando no esta
 * activo
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class TemperatureNotActiveSensor extends TemperatureSensor {

    private final String MESSAGE = "This sensor is off";

    public TemperatureNotActiveSensor() {
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
