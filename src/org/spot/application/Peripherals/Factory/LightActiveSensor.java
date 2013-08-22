package org.spot.application.Peripherals.Factory;

import java.io.IOException;

/**
 * Clase que modela el comportamiento del sensor de luz cuando esta activo
 *
 * @author rubencc
 */
public class LightActiveSensor extends LightSensor {

    public LightActiveSensor() {
        super();
        this.setActive(true);
    }

    public String getMeasure() {
        String _temp = null;
        try {
            _temp = String.valueOf(this.lightSensor.getValue());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return _temp;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.ACTIVE);
    }
}
