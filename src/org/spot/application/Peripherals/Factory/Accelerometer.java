/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import org.spot.application.Interfaces.IAccelerometer;

/**
 *
 * @author rubencc
 */
public abstract class Accelerometer implements IAccelerometer {

    private final String NAME = "Accelerometer";
    private String name;
    protected IAccelerometer3D acc;
    protected final String ACTIVE = "Active";
    protected final String NOT_ACTIVE = "Not Active";
    protected final String NOT_INSTALLED = "NOT INSTALLED";

    public Accelerometer() {
        this.name = NAME;
        this.acc = acc = (IAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
    }

    public abstract String getX();

    public abstract String getY();

    public abstract String getZ();

    public String getName() {
        return this.name;
    }

    public abstract String getStatus();
}
