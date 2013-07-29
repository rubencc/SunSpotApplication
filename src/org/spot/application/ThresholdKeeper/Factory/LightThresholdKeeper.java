/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.ThresholdKeeper.Factory;

import com.sun.spot.util.Utils;

/**
 *
 * @author rubencc
 */
public class LightThresholdKeeper extends ThresholdKeeper {

    private final String NAME = "Light Threshold Keeper";
    private final int MAXVALUE = 550;
    private final int MINVALUE = 350;
    private final int DEFAULTVALUE = 350;
    private final int ABOVEWARNING = 80;
    private final int BELOWWARNING = 5;
    private final int OKVALUE = 255;
    private final int BELOWCOLOR = 1;
    private final int OKCOLOR = 2;
    private final int ABOVECOLOR = 3;
    private final long PERIOD = 4000;

    public LightThresholdKeeper() {
        super();
        this.setMaxValue(MAXVALUE);
        this.setMinValue(MINVALUE);
        this.setPeriod(PERIOD);
    }

    public void run() {
        while (this.runCond) {
            int _value = Integer.parseInt(this.sensor.getLightMeasure());
            if (_value > this.getMaxValue()) {
                this.sensor.ledSetOn(ABOVEWARNING);
                this.sensor.ledSetColor(ABOVECOLOR);
            }
            if (_value < this.getMinValue()) {
                this.sensor.ledSetOn(BELOWWARNING);
                this.sensor.ledSetColor(BELOWCOLOR);
            }
            if (this.getMinValue() < _value && _value < this.getMaxValue()) {
                this.sensor.ledSetOn(OKVALUE);
                this.sensor.ledSetColor(OKCOLOR);
            }
            Utils.sleep(PERIOD);
        }
    }

    public String getName() {
        return this.NAME;
    }
}
