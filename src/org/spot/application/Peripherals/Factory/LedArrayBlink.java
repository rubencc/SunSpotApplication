package org.spot.application.Peripherals.Factory;

import com.sun.spot.util.Utils;

/**
 * Clase que provee de la funcionalidad de parpadeo al array de leds. Se ejecuta
 * en un hilo independiente.
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
            if (this.pm.hasANumber()) {
                this.pm.ledSetOn();
            }
        }
    }
}
