/*
 * SunSpotApplication.java
 *
 * Created on 06-abr-2013 20:04:12;
 */
package org.sunspotworld;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.spot.application.Interfaces.Constans;
import org.spot.application.Network.BroadcastConnection;
import org.spot.application.Network.PDU;
import org.spot.application.Network.PeerConnection;
import org.spot.application.Peripherals.Factory.LedArrayBlink;
import org.spot.application.Peripherals.Factory.LedArrayTime;
import org.spot.application.Peripherals.Factory.PeripheralsManager;
import org.spot.application.ThresholdKeeper.Factory.ThresholdManager;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 *
 * The manifest specifies this class as MIDlet-1, which means it will be
 * selected for execution.
 */
public class SunSpotApplication extends MIDlet implements Constans {

    private String ourAddress;
    private final String PINGREPLY = "Ping Reply";
    private PeripheralsManager pm;
    private BroadcastConnection bCon;
    private PeerConnection pCon;
    private ThresholdManager keeper;

    protected void startApp() throws MIDletStateChangeException {

        //BootloaderListenerService.getInstance().start();   // monitor the USB (if connected) and recognize commands from host

        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        ourAddress = IEEEAddress.toDottedHex(ourAddr);
        bCon = BroadcastConnection.getInstance();
        pCon = PeerConnection.getInstance();
        pm = PeripheralsManager.getInstance();
        System.out.println("Direccion de red = " + ourAddress);
        pCon.setOurAddress(ourAddress);
        keeper = ThresholdManager.getInstance();
        while (true) {
            if (this.bCon.packetsAvailable()) {
                PDU pdu = this.bCon.readBroadcast();
                processPDU(pdu);
            }
            if (this.pCon.packetsAvailable()) {
                PDU pdu = this.pCon.readPeer();
                processPDU(pdu);
            }
            Utils.sleep(1000);
        }
        //notifyDestroyed();                      // cause the MIDlet to exit
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system. It is not called if
     * MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true the MIDlet must cleanup and release all
     * resources.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        if (this.pCon != null) {
            this.pCon.close();
        }
        if (bCon != null) {
            bCon.close();
        }
    }

    /**
     * Responde a un paquete de PING recibido por la conexion de broadcast
     */
    private void replyToPing(PDU pdu) {
        if (this.bCon.isNewBroadcast()) {
            this.pCon.setPeerAddress(pdu.getAddress());
            this.pCon.connectToPeer();
            this.bCon.setNewBroadcast(false);
        }
        pdu.setType(PING_PACKET_REPLY);
    }

    /**
     * Activa o desactiva sensores
     *
     * @param type
     * @throws IOException
     * @throws NumberFormatException
     */
    private String configFeatures(String value) throws IOException, NumberFormatException {

        String _temp = null;
        switch (Integer.parseInt(value)) {
            case LIGHTSENSOR_ON:
                this.pm.setLightSensorStatus("on");
                _temp = "Light sensor ON";
                break;
            case LIGHTSENSOR_OFF:
                this.pm.setLightSensorStatus("off");
                _temp = "Light sensor OFF";
                break;
            case LIGHT_SENSOR_NOT_PRESENT:
                this.pm.setLightSensorStatus("notpresent");
                _temp = "Sensor unavailable";
                break;
            case TEMPERATURESENSOR_ON:
                this.pm.setTemperatureSensorStatus("on");
                _temp = "Temperature sensor ON";
                break;
            case TEMPERATURESENSOR_OFF:
                this.pm.setTemperatureSensorStatus("off");
                _temp = "Temperature sensor OFF";
                break;
            case TEMPERATURE_SENSOR_NOT_PRESENT:
                this.pm.setTemperatureSensorStatus("notpresent");
                _temp = "Sensor unavailable";
                break;
            case ACCELEROMETER_ON:
                this.pm.setAccelerometerStatus("on");
                _temp = "Accelerometer ON";
                break;
            case ACCELEROMETER_OFF:
                this.pm.setAccelerometerStatus("off");
                _temp = "Accelerometer OFF";
                break;
            case ACCELEROMETER_NOT_PRESENT:
                this.pm.setAccelerometerStatus("notpresent");
                _temp = "Acelerometer unavailable";
                break;
            case LEDARRAY_ON:
                this.pm.setLedArrayStatus("on");
                _temp = "LedArray ON";
                break;
            case LEDARRAY_OFF:
                this.pm.setLedArrayStatus("off");
                _temp = "LedArray OFF";
                break;
            case LED_ARRAY_NOT_PRESENT:
                this.pm.setLedArrayStatus("notprenset");
                _temp = "LedArray unavailable";
                break;
        }
        return _temp;
    }

    /**
     * Procesa cada una de las PDUs recibidas
     *
     * @param pdu
     * @throws NumberFormatException
     * @throws IOException
     */
    private void processPDU(PDU pdu) {

        switch (pdu.getType()) {
            case PING_PACKET_REQUEST:
                replyToPing(pdu);
                break;
            case MEASURE_LIGHT:
                System.out.println("MEASURE LIGHT ");
                pdu.setFirsValue(this.pm.getLightMeasure());
                break;
            case MEASURE_TEMPERATURE:
                System.out.println("MEASURE TEMPERATURE");
                pdu.setFirsValue(this.pm.getTemperatureMeasure());
                break;
            case MEASURE_ACCELEROMETER:
                System.out.println("MEASURE ACCELEROMETER");
                String _measureAccelerometer[] = new String[3];
                _measureAccelerometer[0] = this.pm.getAcceletometerX();
                _measureAccelerometer[1] = this.pm.getAcceletometerY();
                _measureAccelerometer[2] = this.pm.getAcceletometerZ();
                pdu.setValues(_measureAccelerometer);

                break;
            case LED_SET_COLOR:
                System.out.println("LED SET COLOR");
                if (this.pm.ledSetColor(Integer.parseInt(pdu.getValues()[0]))) {
                    pdu.setFirsValue("Selected color");
                } else {
                    pdu.setFirsValue("Error");
                }

                break;
            case LED_SET_NUMBER:
                System.out.println("LED SET NUMBER");
                if (this.pm.ledSetOn(Integer.parseInt(pdu.getValues()[0]))) {
                    pdu.setFirsValue("Leds on");
                } else {
                    pdu.setFirsValue("Error");
                }

                break;
            case LED_SET_OFF:
                System.out.println("LED SET OFF");
                if (this.pm.ledSetOff()) {
                    pdu.setFirsValue("All leds off");
                } else {
                    pdu.setFirsValue("Error");
                }

                break;
            case LED_SET_STATE:
                boolean _cond = false;
                if (pdu.getValues()[0].equals("true")) {
                    _cond = true;
                }

                if (this.pm.ledSetOn(_cond)) {
                    pdu.setFirsValue("Status of the leds " + _cond);
                } else {
                    pdu.setFirsValue("Error");
                }
                break;
            case LED_TIME:
                System.out.println("LED TIME " + pdu.getValues()[0]);
                long _value = Long.parseLong(pdu.getValues()[0]);
                LedArrayTime _ledTime = new LedArrayTime(_value);
                new Thread(_ledTime).start();
                pdu.setFirsValue("Leds on while " + _value + "ms");
                break;
            case LED_BLINK:
                System.out.println("LED BLINK " + pdu.getValues()[0]);
                long _blink = Long.parseLong(pdu.getValues()[0]);
                long _period = Long.parseLong(pdu.getValues()[1]);
                LedArrayBlink _ledBlink = new LedArrayBlink(_blink, _period);
                new Thread(_ledBlink).start();
                pdu.setFirsValue("Leds blinking while " + _blink + "ms with period " + _period + "ms");
                break;
            case CHECK:
                System.out.println("CHECK");
                pdu.setFirsValue(PINGREPLY);
                break;
            case FEATURE:
                System.out.println("FEATURES");
                try {
                    pdu.setFirsValue(configFeatures(pdu.getValues()[0]));
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                break;
            case READ_CONFIGURATION:
                System.out.println("READ FEATURES");
                pdu.setValues(this.pm.getStatus());
                break;
            case THRESHOLD_FEATURE:
                System.out.println("THRESHOLD FEATURE");
                thresholdManager(pdu);
                break;
            case LIGHT_THRESHOLD_CONFIG:
                System.out.println("LIGHT THRESHOLD CONFIG");
                this.keeper.setLightThresholdKeeper(Integer.parseInt(pdu.getValues()[0]), Integer.parseInt(pdu.getValues()[1]), Long.parseLong(pdu.getValues()[2]));
                pdu.setFirsValue("Light threshold configured");
                break;
            case TEMPERATURE_THRESHOLD_CONFIG:
                System.out.println("TEMPERATURE THRESHOLD CONFIG");
                this.keeper.setTemperatureThresholdKeeper(Integer.parseInt(pdu.getValues()[0]), Integer.parseInt(pdu.getValues()[1]), Long.parseLong(pdu.getValues()[2]));
                pdu.setFirsValue("Temperature threshold configured");
                break;
            default:
                pdu = null;
                break;
        }
        if (pdu != null) {
            this.pCon.sendToPeer(pdu);
        }
    }

    /**
     * Activa o desactiva la ejecucion de los hilos de vigilancia de umbrales.
     *
     * @param pdu
     */
    private void thresholdManager(PDU pdu) {
        switch (Integer.parseInt(pdu.getValues()[0])) {
            case LIGHT_THRESHOLD_ON:
                this.keeper.launchLightThresholdKeeper();
                pdu.setFirsValue("Light Threshold Keeper ON");
                break;
            case LIGHT_THRESHOLD_OFF:
                this.keeper.stopLightThresholdKeeper();
                pdu.setFirsValue("Light Threshold Keeper OFF");
                break;
            case TEMPERATURE_THRESHOLD_ON:
                this.keeper.launchTemperatureThresholdKeeper();
                pdu.setFirsValue("Temperature Threshold Keeper ON");
                break;
            case TEMPERATURE_THRESHOLD_OFF:
                this.keeper.stopTemperatureThresholdKeeper();
                pdu.setFirsValue("Temperature Threshold Keeper OFF");
                break;
        }
    }
}
