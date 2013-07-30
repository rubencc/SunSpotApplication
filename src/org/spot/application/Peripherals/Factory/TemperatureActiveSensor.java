/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

import java.io.IOException;

/**
 *
 * @author rubencc
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
