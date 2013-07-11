/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

/**
 *
 * @author rubencc
 */
public class NotLedArray extends LedArray {

    public NotLedArray() {
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

    public String getStatus() {
        return new String(this.getName() + " " + this.NOT_INSTALLED);
    }
}
