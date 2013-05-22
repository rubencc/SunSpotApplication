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
public class LedArrayNotActive extends LedArray {

    public LedArrayNotActive() {
        super();
    }

    public boolean setColor(int clr) {
        return false;
    }

    public boolean setOff() {
        return false;
    }

    public boolean setOn(boolean on) {
        return false;
    }

    public boolean setOn(int bits) {
        return false;
    }
}
