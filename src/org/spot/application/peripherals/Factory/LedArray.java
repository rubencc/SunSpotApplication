/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import com.sun.spot.resources.transducers.LEDColor;
import org.spot.application.Interfaces.ILed;

/**
 *
 * @author rubencc
 */
public abstract class LedArray implements ILed {

    private String name;
    private final String NAME = "LedArray";

    public LedArray() {
        this.name = NAME;
    }

    public abstract void setColor(LEDColor clr);

    public abstract void setOff();

    public abstract void setOn();

    public abstract void setOn(boolean on);

    public abstract void setOn(int bits);

    public String getName() {
        return this.name;
    }
}
