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

    public boolean setColor(int clr) {
        this.leds.setColor(this.selectColor(clr));
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

    private LEDColor selectColor(int clr) {
        LEDColor _color;
        switch (clr) {
            case 1:
                _color = LEDColor.BLUE;
                break;
            case 2:
                _color = LEDColor.CHARTREUSE;
                break;
            case 3:
                _color = LEDColor.CYAN;
                break;
            case 4:
                _color = LEDColor.GREEN;
                break;
            case 5:
                _color = LEDColor.MAGENTA;
                break;
            case 6:
                _color = LEDColor.MAUVE;
                break;
            case 7:
                _color = LEDColor.ORANGE;
                break;
            case 8:
                _color = LEDColor.PUCE;
                break;
            case 9:
                _color = LEDColor.RED;
                break;
            case 10:
                _color = LEDColor.TURQUOISE;
                break;
            case 11:
                _color = LEDColor.WHITE;
                break;
            case 12:
                _color = LEDColor.YELLOW;
                break;
            default:
                _color = LEDColor.RED;
                break;

        }
        return _color;
    }
}
