package org.spot.application.Peripherals.Factory;

/**
 * Clase que modela el comportamiento del acelerometro cuando no esta activo
 *
 * @author rubencc
 */
public class AccelerometerNotActive extends Accelerometer {

    private final String MESSAGE = "The accelerometer is off";

    public AccelerometerNotActive() {
        super();
    }

    public String getX() {
        return MESSAGE;
    }

    public String getY() {
        return MESSAGE;
    }

    public String getZ() {
        return MESSAGE;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.NOT_ACTIVE);
    }
}
