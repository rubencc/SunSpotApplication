package org.spot.application.Peripherals.Factory;

import com.sun.spot.resources.transducers.LEDColor;

/**
 * Clase que modela el comportamiento del array de leds cuando esta activo
 *
 * @author rubencc
 */
public class LedArrayActive extends LedArray {

    private int number;

    public LedArrayActive() {
        super();
        this.number = 0;
        this.setActive(true);
    }

    public boolean hasANumber() {
        boolean _result = false;
        if (this.number != 0) {
            _result = true;
        }
        return _result;
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
        this.number = bits;
        return true;
    }

    public boolean setOn() {
        this.leds.setOn(number);
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

    public String getStatus() {
        return this.getName() + " " + this.ACTIVE;
    }
}
