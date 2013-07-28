/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

/**
 *
 * @author rubencc
 */
public class NotTemperatureSensor extends TemperatureSensor {

    private final String MESSAGE = "This sensor is not installed";

    public NotTemperatureSensor() {
        super();
    }

    public String getMeasure() {
        return MESSAGE;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.NOT_INSTALLED);
    }
}
