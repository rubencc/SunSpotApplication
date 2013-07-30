/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.ThresholdKeeper.Factory;

import com.sun.spot.util.Utils;
import org.spot.application.Network.PDU;

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
                sendPDU(String.valueOf(_value));
            }
            if (_value < this.getMinValue()) {
                this.sensor.ledSetOn(BELOWWARNING);
                this.sensor.ledSetColor(BELOWCOLOR);
                sendPDU(String.valueOf(_value));
            }
            if (this.getMinValue() < _value && _value < this.getMaxValue()) {
                this.sensor.ledSetOn(OKVALUE);
                this.sensor.ledSetColor(OKCOLOR);
            }
            Utils.sleep(PERIOD);
        }
        this.sensor.ledSetOff();
    }

    private void sendPDU(String value) {
        PDU pdu = new PDU(QUEUE_ALERT, NAME, null, false);
        String[] _temp = new String[5];
        _temp[0] = this.getName();
        _temp[1] = String.valueOf("Max value: " + this.getMaxValue());
        _temp[2] = String.valueOf("Min value: " + this.getMinValue());
        _temp[3] = String.valueOf("Period: " + this.getPeriod());
        _temp[4] = String.valueOf("Measured value: " + value);
        pdu.setValues(_temp);
        this.pCon.sendToPeer(pdu);
    }

    public String getName() {
        return this.NAME;
    }
}
