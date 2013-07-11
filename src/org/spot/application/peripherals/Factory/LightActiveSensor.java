/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import java.io.IOException;

/**
 *
 * @author rubencc
 */
public class LightActiveSensor extends LightSensor {

    public LightActiveSensor() {
        super();
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
