package org.spot.application.Peripherals.Actuators;

/**
 * Clase que modela el comportamiento del acelerometro cuando no esta instalado
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class NotAccelerometer extends Accelerometer {

    private final String MESSAGE = "The accelerometer is not active";

    public NotAccelerometer() {
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
        return new String(this.getName() + " " + this.NOT_INSTALLED);
    }
}
