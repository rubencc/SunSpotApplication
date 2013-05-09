/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

/**
 *
 * @author rubencc
 */
public class NotLightSensor extends LightSensor {

    private final String MESSAGE = "This sensor is not installed";

    public NotLightSensor() {
        super();
    }

    public String getMeasure() {
        return MESSAGE;
    }
}
