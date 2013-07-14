/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Interfaces;

/**
 *
 * @author rubencc
 */
public interface ILed {

    boolean setColor(int clr);

    boolean setOff();

    boolean setOn(boolean on);

    boolean setOn(int bits);

    boolean setOn();

    String getStatus();
}
