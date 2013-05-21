/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import com.sun.spot.resources.transducers.LEDColor;

/**
 *
 * @author rubencc
 */
public class LedArrayActive extends LedArray {

    public LedArrayActive() {
        super();
    }

    public boolean setColor(LEDColor clr) {
        this.leds.setColor(clr);
        return true;
    }

    public boolean setOff() {
        this.leds.setOff();
        return true;
    }

    public boolean setOn(boolean on) {
        this.leds.setOn(on);
        return true;
    }

    public boolean setOn(int bits) {
        this.leds.setOn(bits);
        return true;
    }
}
