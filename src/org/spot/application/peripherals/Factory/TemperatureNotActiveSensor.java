/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

/**
 *
 * @author rubencc
 */
public class TemperatureNotActiveSensor extends TemperatureSensor {

    private final String MESSAGE = "This sensor is not available";

    public TemperatureNotActiveSensor() {
        super();
    }

    public String getMeasure() {
        return MESSAGE;
    }
}
