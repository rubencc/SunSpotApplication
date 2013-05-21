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

    boolean setColor(LEDColor clr);

    boolean setOff();

    boolean setOn(boolean on);

    boolean setOn(int bits);
}
