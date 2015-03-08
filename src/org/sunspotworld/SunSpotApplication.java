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
import org.spot.application.Configuration.Manager.ConfigurationManager;
import org.spot.application.Configuration.Manager.IConfigurationManager;
import org.spot.application.Interfaces.Constans;
import org.spot.application.Network.BroadcastConnection;
import org.spot.application.Network.PDU;
import org.spot.application.Network.PeerConnection;
import org.spot.application.Peripherals.Manager.LedArrayBlink;
import org.spot.application.Peripherals.Manager.LedArrayTime;
import org.spot.application.Peripherals.Manager.PeripheralsManager;
import org.spot.application.ThresholdKeeper.ThresholdManager;

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
    private IConfigurationManager configManager;

    protected void startApp() throws MIDletStateChangeException {

        //BootloaderListenerService.getInstance().start();   // monitor the USB (if connected) and recognize commands from host

        //Direccion de red del dispositivo
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        ourAddress = IEEEAddress.toDottedHex(ourAddr);
        //Conexión broadcast con el host application
        bCon = BroadcastConnection.getInstance();
        //Conexión peer con el host application
        pCon = PeerConnection.getInstance();
        //Gestor de perifericos
        pm = PeripheralsManager.getInstance();
        //Gestor de umbrales
        keeper = ThresholdManager.getInstance();
        //Gestor de la configuracion
        configManager = new ConfigurationManager(pm);
        System.out.println("Direccion de red = " + ourAddress);
        pCon.setOurAddress(ourAddress);
        while (true) {
            //Lee paquetes provenientes de la conexion broadcast
            if (this.bCon.packetsAvailable()) {
                PDU pdu = this.bCon.readBroadcast();
                processPDU(pdu);
            }
            //Lee paquetes provenientes de la conexion peer
            if (this.pCon.packetsAvailable()) {
                PDU pdu = this.pCon.readPeer();
                processPDU(pdu);
            }
            //Duerme la ejecucion del hilo principal, en milisegundos.
            Utils.sleep(500);
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
     * Procesa cada una de las PDUs recibidas
     *
     * @param pdu
     * @throws NumberFormatException
     * @throws IOException
     */
    private void processPDU(PDU pdu) {

        String[] values = pdu.getValues();
        String command = "";
        if (values != null && values.length > 0) {
            command = values[0];
        }

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
                getMeassureAccelerometer(pdu);
                break;
            case LED_SET_COLOR:
                System.out.println("LED SET COLOR");
                if (this.pm.ledSetColor(Integer.parseInt(command))) {
                    pdu.setFirsValue("Selected color");
                } else {
                    pdu.setFirsValue("Error");
                }

                break;
            case LED_SET_NUMBER:
                System.out.println("LED SET NUMBER");
                if (this.pm.ledSetOn(Integer.parseInt(command))) {
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
                if (command.equals("true")) {
                    _cond = true;
                }

                if (this.pm.ledSetOn(_cond)) {
                    pdu.setFirsValue("Status of the leds " + _cond);
                } else {
                    pdu.setFirsValue("Error");
                }
                System.out.println("LED SET " + _cond);
                break;
            case LED_TIME:
                System.out.println("LED TIME " + command);
                setLedTime(pdu);
                break;
            case LED_BLINK:
                System.out.println("LED BLINK " + command);
                setLedBlink(pdu);
                break;
            case CHECK:
                System.out.println("CHECK");
                pdu.setFirsValue(PINGREPLY);
                break;
            case FEATURE:
                System.out.println("FEATURES");
                pdu.setFirsValue(configManager.configFeatures(command));
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
                configLightThresholdKeeper(pdu);
                break;
            case TEMPERATURE_THRESHOLD_CONFIG:
                System.out.println("TEMPERATURE THRESHOLD CONFIG");
                configTemperatureThresholdKeeper(pdu);
                break;
            case READ_THRESHOLD_CONFIGURATION:
                System.out.println("READ THRESHOLD CONFIGURATION");
                readThresholdConfiguration(pdu);
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
                if (this.pm.isLightSensorActive()) {
                    this.keeper.launchLightThresholdKeeper();
                    pdu.setValues(this.keeper.getStatusLightThresholdKeeper());
                } else {
                    pdu.setFirsValue(this.pm.getStatusLightSensor());
                }
                break;
            case LIGHT_THRESHOLD_OFF:
                if (this.pm.isLightSensorActive()) {
                    this.keeper.stopLightThresholdKeeper();
                    pdu.setValues(this.keeper.getStatusLightThresholdKeeper());
                } else {
                    pdu.setFirsValue(this.pm.getStatusLightSensor());
                }
                break;
            case TEMPERATURE_THRESHOLD_ON:
                if (this.pm.isTemperatureSensorActive()) {
                    this.keeper.launchTemperatureThresholdKeeper();
                    pdu.setValues(this.keeper.getStatusTemperatureThresholdKeeper());
                } else {
                    pdu.setFirsValue(this.pm.getStatusTemperatureSensor());
                }
                break;
            case TEMPERATURE_THRESHOLD_OFF:
                if (this.pm.isTemperatureSensorActive()) {
                    this.keeper.stopTemperatureThresholdKeeper();
                    pdu.setValues(this.keeper.getStatusTemperatureThresholdKeeper());
                } else {
                    pdu.setFirsValue(this.pm.getStatusTemperatureSensor());
                }
                break;
        }
    }

    private void configLightThresholdKeeper(PDU pdu) {
        try {
            double maxValue = Double.parseDouble(pdu.getValues()[0]);
            double minValue = Double.parseDouble(pdu.getValues()[1]);
            if (this.pm.isLightSensorActive() && (minValue < maxValue)) {
                this.keeper.setLightThresholdKeeper(maxValue, minValue, Long.parseLong(pdu.getValues()[2]));
                pdu.setValues(this.keeper.getStatusLightThresholdKeeper());
            } else if (minValue > maxValue) {
                pdu.setFirsValue("Parameters error: The minimun value is higher than maximun value");
            } else {
                pdu.setFirsValue(this.pm.getStatusLightSensor());
            }
        } catch (NumberFormatException ex) {
            pdu.setFirsValue("Error" + ex.getMessage());
        }
    }

    private void configTemperatureThresholdKeeper(PDU pdu) {
        try {
            double maxValue = Double.parseDouble(pdu.getValues()[0]);
            double minValue = Double.parseDouble(pdu.getValues()[1]);
            if (this.pm.isTemperatureSensorActive() && (minValue < maxValue)) {
                this.keeper.setTemperatureThresholdKeeper(maxValue, minValue, Long.parseLong(pdu.getValues()[2]));
                pdu.setValues(this.keeper.getStatusTemperatureThresholdKeeper());
            } else if (minValue > maxValue) {
                pdu.setFirsValue("Parameters error: The minimun value is higher than maximun value");
            } else {
                pdu.setFirsValue(this.pm.getStatusTemperatureSensor());
            }
        } catch (NumberFormatException ex) {
            pdu.setFirsValue("Error" + ex.getMessage());
        }
    }

    private void readThresholdConfiguration(PDU pdu) {
        String[] _lightInfo = this.keeper.getStatusLightThresholdKeeper();
        String[] _tempInfo = this.keeper.getStatusTemperatureThresholdKeeper();
        int lengthArray = _lightInfo.length + _tempInfo.length;
        String[] _temp = new String[lengthArray];
        int i;
        for (i = 0; i < _lightInfo.length; i++) {
            _temp[i] = _lightInfo[i];
        }
        for (i = 0; i < _tempInfo.length; i++) {
            _temp[i + _lightInfo.length] = _tempInfo[i];
        }
        pdu.setValues(_temp);
    }

    private void getMeassureAccelerometer(PDU pdu) {
        String _measureAccelerometer[] = new String[3];
        _measureAccelerometer[0] = this.pm.getAcceletometerX();
        _measureAccelerometer[1] = this.pm.getAcceletometerY();
        _measureAccelerometer[2] = this.pm.getAcceletometerZ();
        pdu.setValues(_measureAccelerometer);
    }

    private void setLedBlink(PDU pdu) throws NumberFormatException {
        long _blink = Long.parseLong(pdu.getValues()[0]);
        long _period = Long.parseLong(pdu.getValues()[1]);
        LedArrayBlink _ledBlink = new LedArrayBlink(_blink, _period, this.pm);
        new Thread(_ledBlink).start();
        pdu.setFirsValue("Leds blinking while " + _blink + "ms with period " + _period + "ms");
    }

    private void setLedTime(PDU pdu) throws NumberFormatException {
        long _value = Long.parseLong(pdu.getValues()[0]);
        LedArrayTime _ledTime = new LedArrayTime(_value, this.pm);
        new Thread(_ledTime).start();
        pdu.setFirsValue("Leds on while " + _value + "ms");
    }
}
