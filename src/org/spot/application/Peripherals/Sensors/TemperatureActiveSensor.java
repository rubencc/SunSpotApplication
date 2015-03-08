package org.spot.application.Peripherals.Sensors;

import java.io.IOException;

/**
 * Clase que mopdela el comportamiento del sensor de temperatura cuando esta
 * activo
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class TemperatureActiveSensor extends TemperatureSensor {

    public TemperatureActiveSensor() {
        super();
        this.setActive(true);
    }

    public String getMeasure() {
        String _temp = null;
        try {
            _temp = String.valueOf(this.tempSensor.getCelsius());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return _temp;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.ACTIVE);
    }
}
