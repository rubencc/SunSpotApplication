package org.spot.application.Peripherals.Sensors;

/**
 * Clase que modela el comportamiento del sensor de temperatura cuando no esta
 * instalado
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class NotTemperatureSensor extends TemperatureSensor {

    private final String MESSAGE = "This sensor is not installed";

    public NotTemperatureSensor() {
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
