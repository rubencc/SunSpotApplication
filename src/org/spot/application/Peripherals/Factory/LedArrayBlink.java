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
        if (this.pm.isLedArrayActive()) {
            this.time = System.currentTimeMillis();
            this.pm.ledSetOn(false);
            while (System.currentTimeMillis() < (this.time + this.blink)) {
                this.pm.ledSetOn();
                Utils.sleep(this.period);
                this.pm.ledSetOn(false);
                Utils.sleep(this.period);
            }
        }
    }
}
