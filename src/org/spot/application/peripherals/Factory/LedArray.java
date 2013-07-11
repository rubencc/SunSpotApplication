/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import org.spot.application.Interfaces.ILed;

/**
 *
 * @author rubencc
 */
public abstract class LedArray implements ILed {

    private String name;
    private final String NAME = "LedArray";
    protected ITriColorLEDArray leds;
    protected final String ACTIVE = "Active";
    protected final String NOT_ACTIVE = "Not Active";
    protected final String NOT_INSTALLED = "NOT INSTALLED";

    public LedArray() {
        this.name = NAME;
        this.leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    }

    public abstract boolean setColor(int clr);

    public abstract boolean setOff();

    public abstract boolean setOn(boolean on);

    public abstract boolean setOn(int bits);

    public String getName() {
        return this.name;
    }

    public abstract String getStatus();
}
