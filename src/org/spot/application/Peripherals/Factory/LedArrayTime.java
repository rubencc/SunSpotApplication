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
        if (this.pm.isLedArrayActive()) {
            this.pm.ledSetOn(false);
            this.pm.ledSetOn();
            Utils.sleep(this.value);
            this.pm.ledSetOn(false);
        }
    }
}
