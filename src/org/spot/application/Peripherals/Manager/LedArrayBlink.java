package org.spot.application.Peripherals.Manager;

import org.spot.application.Peripherals.Manager.PeripheralsManager;
import com.sun.spot.util.Utils;

/**
 * Clase que provee de la funcionalidad de parpadeo al array de leds. Se ejecuta
 * en un hilo independiente.
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class LedArrayBlink implements Runnable {

    private long period;
    private long blink;
    private long time;
    private IPeripheralsManager pm;

    public LedArrayBlink(long blink, long period, IPeripheralsManager pm) {
        this.blink = blink;
        this.period = period;
        this.pm = pm;
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
            this.pm.ledSetOn(false);
        }
    }
}
