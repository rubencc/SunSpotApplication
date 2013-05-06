/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import org.spot.application.Interfaces.IAccelerometer;

/**
 *
 * @author rubencc
 */
public abstract class Accelerometer implements IAccelerometer {

    private final String NAME = "Accelerometer";
    private String name;

    public Accelerometer() {
        this.name = NAME;
    }

    public abstract String getX();

    public abstract String getY();

    public String getName() {
        return this.name;
    }
}
