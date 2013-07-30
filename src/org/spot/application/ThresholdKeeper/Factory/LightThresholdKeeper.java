/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.ThresholdKeeper.Factory;

import com.sun.spot.util.Utils;
import org.spot.application.Network.PDU;
import org.spot.application.Interfaces.Constans;

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
        this.runCond = true;
        while (this.runCond && this.sensor.isLightSensorActive()) {
            int _value = Integer.parseInt(this.sensor.getLightMeasure());
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
