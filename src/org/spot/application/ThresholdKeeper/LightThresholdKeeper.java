/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.ThresholdKeeper;

import com.sun.spot.util.Utils;
import org.spot.application.Network.PDU;

/**
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class LightThresholdKeeper extends ThresholdKeeper {

    private final String NAME = "Light Threshold Keeper";
    //Valores por defecto del umbral
    private final double MAXVALUE = 550;
    private final double MINVALUE = 350;
    //Indicación led para valores superiores
    private final int ABOVEWARNING = 80;
    //Indicación led para valores menores
    private final int BELOWWARNING = 5;
    //Indicacion led para valores correctos
    private final int OKVALUE = 255;
    //Colores para las indicaciones led
    private final int BELOWCOLOR = 1;
    private final int OKCOLOR = 2;
    private final int ABOVECOLOR = 3;
    //Valor por defeco de toma de muestras
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
            double _value = Double.parseDouble(this.sensor.getLightMeasure());
            if (_value > this.getMinValue() && _value > this.getMaxValue()) {
                this.sensor.ledSetOn(ABOVEWARNING);
                this.sensor.ledSetColor(ABOVECOLOR);
                sendPDU(formatPDU(String.valueOf(_value), true));
            }
            if (_value < this.getMinValue() && _value < this.getMaxValue()) {
                this.sensor.ledSetOn(BELOWWARNING);
                this.sensor.ledSetColor(BELOWCOLOR);
                sendPDU(formatPDU(String.valueOf(_value), true));
            }
            if (this.getMinValue() < _value && _value < this.getMaxValue()) {
                this.sensor.ledSetOn(OKVALUE);
                this.sensor.ledSetColor(OKCOLOR);
                sendPDU(formatPDU(String.valueOf(_value), false));
            }
            Utils.sleep(this.getPeriod());
        }
        this.sensor.ledSetOff();
    }

    /**
     * Forma una PDU con la informacion de la alerta
     *
     * @param text -- Valor medido
     */
    private PDU formatPDU(String text, boolean isAlert) {
        PDU pdu;
        if (isAlert) {
            pdu = new PDU(QUEUE_ALERT, this.NAME, null, false);
        } else {
            pdu = new PDU(THRESHOLD_INFO, this.NAME, null, false);
        }
        String[] _temp = new String[5];
        _temp[0] = this.getName();
        _temp[1] = String.valueOf("Max value: " + this.getMaxValue());
        _temp[2] = String.valueOf("Min value: " + this.getMinValue());
        _temp[3] = String.valueOf("Period: " + this.getPeriod());
        _temp[4] = String.valueOf("Measured value: " + text);
        pdu.setValues(_temp);
        return pdu;

    }

    /**
     * Devuelve el nombre del vigilante del umbral
     *
     * @return Nombre
     */
    public String getName() {
        return this.NAME;
    }

    /**
     * Devuelve la informacion del vigilante del umbral
     *
     * @return String con el estado del gestor
     */
    public String[] getStatus() {
        String[] _temp = new String[5];
        _temp[0] = this.getName();
        _temp[1] = String.valueOf("Max value: " + this.getMaxValue());
        _temp[2] = String.valueOf("Min value: " + this.getMinValue());
        _temp[3] = String.valueOf("Period: " + this.getPeriod());
        _temp[4] = String.valueOf("Is Running: " + this.running);
        return _temp;
    }

    /**
     * Envia una PDU a traves de la conexion peer
     *
     * @param pdu
     */
    protected void sendPDU(PDU pdu) {
        this.pCon.sendToPeer(pdu);
    }
}
