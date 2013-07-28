/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

/**
 *
 * @author rubencc
 */
public class AccelerometerNotActive extends Accelerometer {

    private final String MESSAGE = "The accelerometer is off";

    public AccelerometerNotActive() {
        super();
    }

    public String getX() {
        return MESSAGE;
    }

    public String getY() {
        return MESSAGE;
    }

    public String getZ() {
        return MESSAGE;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.NOT_ACTIVE);
    }
}
