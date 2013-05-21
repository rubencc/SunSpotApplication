/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import org.spot.application.Interfaces.ILed;

/**
 *
 * @author rubencc
 */
public abstract class LedArray implements ILed {

    private String name;
    private final String NAME = "LedArray";
    protected ITriColorLEDArray leds;

    public LedArray() {
        this.name = NAME;
        this.leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    }

    public abstract boolean setColor(LEDColor clr);

    public abstract boolean setOff();

    public abstract boolean setOn(boolean on);

    public abstract boolean setOn(int bits);

    public String getName() {
        return this.name;
    }
}
