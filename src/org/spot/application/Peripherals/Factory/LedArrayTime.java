package org.spot.application.Peripherals.Factory;

import com.sun.spot.util.Utils;

/**
 * Clase que provee de la funcionalidad de activar el array de leds durante un
 * tiempo determinado Se ejecuta en un hilo independiente
 *
 * @author Rubén Carretero <rubencc@gmail.com>
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
