/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

/**
 *
 * @author rubencc
 */
public class LightNotActiveSensor extends LightSensor {

    private final String MESSAGE = "This sensor is off";

    public LightNotActiveSensor() {
        super();
    }

    public String getMeasure() {
        return MESSAGE;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.NOT_ACTIVE);
    }
}
