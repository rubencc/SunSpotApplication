/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Interfaces;

import com.sun.spot.resources.transducers.LEDColor;

/**
 *
 * @author rubencc
 */
public interface ILed {

    void setColor(LEDColor clr);

    void setOff();

    void setOn();

    void setOn(boolean on);

    void setOn(int bits);
}
