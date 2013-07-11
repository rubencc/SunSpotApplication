/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.peripherals.Factory;

import java.io.IOException;

/**
 *
 * @author rubencc
 */
public class AccelerometerActive extends Accelerometer {

    public AccelerometerActive() {
        super();
    }

    public String getX() {
        String _temp = null;
        try {
            _temp = String.valueOf(this.acc.getAccelX());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return _temp;
    }

    public String getY() {
        String _temp = null;
        try {
            _temp = String.valueOf(this.acc.getAccelY());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return _temp;
    }

    public String getZ() {
        String _temp = null;
        try {
            _temp = String.valueOf(this.acc.getAccelZ());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return _temp;
    }

    public String getStatus() {
        return new String(this.getName() + " " + this.ACTIVE);
    }
}
