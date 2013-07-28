/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Peripherals.Factory;

import com.sun.spot.util.Utils;

/**
 *
 * @author rubencc
 */
public class LedArrayTime implements Runnable {

    private long value;
    private PeripheralsManager pm = PeripheralsManager.getInstance();

    public LedArrayTime(long value) {
        this.value = value;
    }

    public void run() {
        pm.ledSetOn(false);
        pm.ledSetOn();
        Utils.sleep(this.value);
        pm.ledSetOn(false);
    }
}
