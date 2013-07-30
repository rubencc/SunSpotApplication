/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

/**
 *
 * @author rubencc
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
