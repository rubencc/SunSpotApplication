package org.spot.application.Peripherals.Actuators;

import java.io.IOException;

/**
 * Clase que modela el comportamiento del acelerometro cuando esta activo
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class AccelerometerActive extends Accelerometer {

    public AccelerometerActive() {
        super();
    }

    public String getX() {
        String _temp = null;
        try {
            _temp = String.valueOf(this.acc.getAccelX());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return _temp;
    }

    public String getY() {
        String _temp = null;
        try {
            _temp = String.valueOf(this.acc.getAccelY());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return _temp;
    }

    public String getZ() {
        String _temp = null;
        try {
            _temp = String.valueOf(this.acc.getAccelZ());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return _temp;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.ACTIVE);
    }
}