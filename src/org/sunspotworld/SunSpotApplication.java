/*
 * SunSpotApplication.java
 *
 * Created on 06-abr-2013 20:04:12;
 */
package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.NoRouteException;
import com.sun.spot.peripheral.radio.RadioFactory;

import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;


import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.spot.application.Interfaces.Constans;
import org.spot.application.peripherals.Factory.PeripheralsManager;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 *
 * The manifest specifies this class as MIDlet-1, which means it will be
 * selected for execution.
 */
public class SunSpotApplication extends MIDlet implements Constans {

    private final int BROADCAST_PORT = 66;
    private final int PEER_PORT = 100;
    private RadiogramConnection pCon = null;
    private RadiogramConnection bCon = null;
    private Datagram pDg = null;
    private Datagram bDg = null;
    private String peerAddress;
    private boolean peerConnected;
    private boolean firstBroadcast;
    private String ourAddress;
    private final String EMPTY = "";
    private final String PINGREPLY = "Ping Reply";
    private PeripheralsManager pm;

    protected void startApp() throws MIDletStateChangeException {

        //BootloaderListenerService.getInstance().start();   // monitor the USB (if connected) and recognize commands from host

        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        ourAddress = IEEEAddress.toDottedHex(ourAddr);
        pm = new PeripheralsManager();
        System.out.println("Direccion de red = " + ourAddress);
        try {
            //Abre la conexion en modo servidor para recibir datos broadcast
            bCon = (RadiogramConnection) Connector.open("radiogram://:" + BROADCAST_PORT);
            bDg = bCon.newDatagram(bCon.getMaximumLength());
            System.out.println("Escuchando brodascast --");
            while (true) {
                if (bCon.packetsAvailable()) {
                    readBroadcast();
                }
                if (this.isPeerPacketsAvailable()) {
                    readPeer();
                }
                Utils.sleep(1000);
            }
        } catch (NoRouteException e) {
            System.out.println("No route to " + pDg.getAddress());
        } catch (IOException ex) {
        }
        notifyDestroyed();                      // cause the MIDlet to exit
    }

    /**
     * Conecta con el peer por primera vez
     */
    protected void connectToPeer() {
        try {
            //Inicia una conexion peer
            pCon = (RadiogramConnection) Connector.open("radiogram://" + peerAddress + ":" + PEER_PORT);
            //pCon.setTimeout(5000);
            pDg = pCon.newDatagram(pCon.getMaximumLength());
            peerConnected = true;
            System.out.println("[Spot] Conectado a: " + peerAddress);
        } catch (IOException ex) {
        }

    }

    /**
     * Comprueba si hay datos disponibles en la conexion con el peer y si esta
     * esta establecidapreviamente
     *
     * @return
     */
    private boolean isPeerPacketsAvailable() {
        if (peerConnected) {
            return pCon.packetsAvailable();
        } else {
            return false;
        }
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
        try {
            if (pCon != null) {
                pCon.close();
            }
            if (bCon != null) {
                bCon.close();
            }
        } catch (IOException ex) {
        }
    }

    /**
     * Responde a un paquete de PING recibido por la conexion de broadcast
     */
    private void replyToPing() {

        if (peerAddress != null && (!peerAddress.equals(bDg.getAddress()))) {
            peerAddress = bDg.getAddress();
            firstBroadcast = true;
            peerConnected = false;
            try {
                if (pCon != null) {
                    pCon.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            peerAddress = bDg.getAddress();
        }
        if (!peerConnected && firstBroadcast) {
            this.connectToPeer();
        }
        //System.out.println("[Spot " + ourAddress + "] Ping recibido");
        pDg.reset();
        String[] _temp = {EMPTY};
        sendToPeer(PING_PACKET_REPLY, _temp, "GUID", BROADCAST);
    }

    /**
     * Envia una respuesta a traves de la conexion peer.
     *
     * @param type Tipo de respuesta
     * @param value Valor de la respuesta
     * @param GUID Identificador de la petición
     * @param broadcast Condicion de mensaje de broadcast
     */
    private void sendToPeer(int type, String[] values, String GUID, boolean broadcast) {
        try {
            int _count = 0;
            //System.out.print(type + " " + GUID + " " + broadcast);
            pDg.reset();
            /*El formato de la PDU es {direccion, tipo, valor, tiempo, guid, broadcast}*/
            pDg.writeUTF(ourAddress);
            pDg.writeInt(type);
            pDg.writeInt(values.length);
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    pDg.writeUTF(values[i]);
                }
            }
            pDg.writeLong(System.currentTimeMillis());
            pDg.writeUTF(GUID);
            pDg.writeBoolean(broadcast);
            pCon.send(pDg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
     * Lee las PDUs de la conexion broadcast y marca las respuestas como tales
     *
     * @throws NumberFormatException
     * @throws IOException
     */
    private void readBroadcast() throws NumberFormatException, IOException {
        bCon.receive(bDg);
        boolean _broadcast = BROADCAST;
        if (!firstBroadcast) {
            firstBroadcast = true;
        }
        int _type = bDg.readInt();
        int _count = bDg.readInt();
        String _values[] = new String[_count];
        for (int i = 0; i < _count; i++) {
            _values[i] = bDg.readUTF();
        }

        String _GUID = bDg.readUTF();
        processPDU(_type, _GUID, _broadcast, _values);
        bDg.reset();
    }

    /**
     * Lee las PDUs de la conexion peer y marca las respuestas como tales.
     *
     * @throws NumberFormatException
     * @throws IOException
     */
    private void readPeer() throws NumberFormatException, IOException {
        pCon.receive(pDg);
        boolean _broadcast = NO_BROADCAST;
        int _type = pDg.readInt();
        int _count = pDg.readInt();
        String _values[] = new String[_count];
        for (int i = 0; i < _count; i++) {
            _values[i] = pDg.readUTF();
        }
        String _GUID = pDg.readUTF();
        processPDU(_type, _GUID, _broadcast, _values);
        pDg.reset();
    }

    /**
     * Procesa cada una de las PDUs recibidas
     *
     * @param type Tipo de PDU
     * @param GUID Identificador asignado a la petición
     * @param broadcast Condicion de broadcast
     * @param values Valor del comando
     * @throws NumberFormatException
     * @throws IOException
     */
    private void processPDU(int type, String GUID, boolean broadcast, String[] values) throws NumberFormatException, IOException {
        String _temp[] = new String[1];
        switch (type) {
            case PING_PACKET_REQUEST:
                replyToPing();
                break;
            case MEASURE_LIGHT:
                System.out.println("MEASURE LIGHT ");
                _temp[0] = this.pm.getLightMeasure();
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case MEASURE_TEMPERATURE:
                System.out.println("MEASURE TEMPERATURE");
                _temp[0] = this.pm.getTemperatureMeasure();
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case MEASURE_ACCELEROMETER:
                System.out.println("MEASURE ACCELEROMETER");
                String _measureAccelerometer[] = new String[3];
                _measureAccelerometer[0] = this.pm.getAcceletometerX();
                _measureAccelerometer[1] = this.pm.getAcceletometerY();
                _measureAccelerometer[2] = this.pm.getAcceletometerZ();
                sendToPeer(type, _measureAccelerometer, GUID, broadcast);
                break;
            case LED_SET_COLOR:
                System.out.println("LED SET COLOR");
                if (this.pm.ledSetColor(Integer.parseInt(values[0]))) {
                    _temp[0] = "Selected color";
                } else {
                    _temp[0] = "Error";
                }
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case LED_SET_NUMBER:
                System.out.println("LED SET NUMBER");
                if (this.pm.ledSetOn(Integer.parseInt(values[0]))) {
                    _temp[0] = "Leds on";
                } else {
                    _temp[0] = "Error";
                }
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case LED_SET_OFF:
                System.out.println("LED SET OFF");
                if (this.pm.ledSetOff()) {
                    _temp[0] = "All leds off";
                } else {
                    _temp[0] = "Error";
                }
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case LED_SET_STATE:
                System.out.println("LED SET STATE" + values[0]);
                boolean _cond = false;
                if (values[0].equals("true")) {
                    _cond = true;
                }

                if (this.pm.ledSetOn(_cond)) {
                    _temp[0] = "Status of the leds " + _cond;
                } else {
                    _temp[0] = "Error";
                }
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case LED_TIME:
                System.out.println("LED TIME " + values[0]);
                long _value = Long.parseLong(values[0]);
                pm.ledSetOn(false);
                pm.ledSetOn();
                Utils.sleep(_value);
                pm.ledSetOn(false);
                String[] _replyTime = new String[1];
                _replyTime[0] = new String("Leds on while " + _value + "ms");
                sendToPeer(type, _replyTime, GUID, broadcast);
                break;
            case LED_BLINK:
                System.out.println("LED BLINK " + values[0]);
                long _blink = Long.parseLong(values[0]);
                long _period = Long.parseLong(values[1]);
                long _time = System.currentTimeMillis();
                pm.ledSetOn(false);
                while (System.currentTimeMillis() < (_time + _blink)) {
                    pm.ledSetOn();
                    Utils.sleep(_period);
                    pm.ledSetOn(false);
                    Utils.sleep(_period);
                }
                String[] _reply = new String[1];
                _reply[0] = new String("Leds blinking while " + _blink + "ms with period " + _period + "ms");
                sendToPeer(type, _reply, GUID, broadcast);
                break;
            case CHECK:
                System.out.println("CHECK");
                String[] _pingReply = {PINGREPLY};
                sendToPeer(type, _pingReply, GUID, broadcast);
                break;
            case FEATURE:
                System.out.println("FEATURES");
                String[] _features = {configFeatures(values[0])};
                sendToPeer(type, _features, GUID, broadcast);
                break;
            case READ_CONFIGURATION:
                System.out.println("READ FEATURES");
                String[] _status = this.pm.getStatus();
                sendToPeer(type, _status, GUID, broadcast);
                break;
        }
    }
}
