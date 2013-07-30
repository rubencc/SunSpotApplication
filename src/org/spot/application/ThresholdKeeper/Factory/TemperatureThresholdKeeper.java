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
public class TemperatureThresholdKeeper extends ThresholdKeeper {

    private final String NAME = "Temperature Threshold Keeper";
    private final int MAXVALUE = 50;
    private final int MINVALUE = 0;
    private final int DEFAULTVALUE = 21;
    private final int ABOVEWARNING = 160;
    private final int BELOWWARNING = 10;
    private final int OKVALUE = 255;
    private final int BELOWCOLOR = 11;
    private final int OKCOLOR = 12;
    private final int ABOVECOLOR = 9;
    private final long PERIOD = 5000;

    public TemperatureThresholdKeeper() {
        super();
        this.setMaxValue(MAXVALUE);
        this.setMinValue(MINVALUE);
        this.setPeriod(PERIOD);
    }

    public void run() {

        this.runCond = true;
        while (this.runCond && this.sensor.isTemperatureSensorActive()) {
            double _value = Double.parseDouble(this.sensor.getTemperatureMeasure());
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
        this.sensor.ledSetOff();
    }

    public String getName() {
        return this.NAME;
    }
}
