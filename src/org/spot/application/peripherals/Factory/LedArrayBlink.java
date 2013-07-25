/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import com.sun.spot.util.Utils;

/**
 *
 * @author rubencc
 */
public class LedArrayBlink implements Runnable {

    private long period;
    private long blink;
    private long time;
    private PeripheralsManager pm = PeripheralsManager.getInstance();

    public LedArrayBlink(long blink, long period) {
        this.blink = blink;
        this.period = period;
    }

    public void run() {
        this.time = System.currentTimeMillis();
        pm.ledSetOn(false);
        while (System.currentTimeMillis() < (this.time + this.blink)) {
            pm.ledSetOn();
            Utils.sleep(this.period);
            pm.ledSetOn(false);
            Utils.sleep(this.period);
        }
    }
}
