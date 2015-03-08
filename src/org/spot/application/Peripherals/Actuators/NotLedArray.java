package org.spot.application.Peripherals.Actuators;

/**
 * Clase que modela el comportamiento de array de leds cuando no esta instalado
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class NotLedArray extends LedArray {

    public NotLedArray() {
        super();
        this.setActive(false);
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

    public boolean setOn() {
        return false;
    }

    public boolean hasANumber() {
        return false;
    }
}