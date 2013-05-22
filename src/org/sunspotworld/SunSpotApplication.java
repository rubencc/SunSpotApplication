/*
 * SunSpotApplication.java
 *
 * Created on 06-abr-2013 20:04:12;
 */
package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.NoRouteException;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.resources.transducers.LEDColor;

import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;


import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.spot.application.peripherals.Factory.PeripheralsManager;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 *
 * The manifest specifies this class as MIDlet-1, which means it will be
 * selected for execution.
 */
public class SunSpotApplication extends MIDlet {

    private final int BROADCAST_PORT = 66;
    private final int PEER_PORT = 100;
    private RadiogramConnection pCon = null;
    private RadiogramConnection bCon = null;
    private Datagram pDg = null;
    private Datagram bDg = null;
    private String peerAddress;
    private boolean peerConnected;
    private boolean firstBroadcast;
    private final int PING_PACKET_REQUEST = 0x30;
    private final int PING_PACKET_REPLY = 0x33;
    private final int MEASURE_LIGHT = 0x40;
    private final int MEASURE_TEMPERATURE = 0x41;
    private final int ACCELEROMETER_X = 0x42;
    private final int ACCELEROMETER_Y = 0x43;
    private final int ACCELEROMETER_Z = 0x44;
    private final int MEASURE_ACCELEROMETER = 0x45;
    private final int LED_SET_COLOR = 0x46;
    private final int LED_SET_OFF = 0x47;
    private final int LED_SET_STATE = 0x48;
    private final int LED_SET_NUMBER = 0x49;
    private final int CHECK = 0x50;
    private final int FEATURE = 0x60;
    private final int LIGHTSENSORON = 0x61;
    private final int LIGHTSENSOROFF = 0x62;
    private final int TEMPERATURESENSORON = 0x63;
    private final int TEMPERATURESENSOROFF = 0x64;
    private final int ACCELEROMETERON = 0x65;
    private final int ACCELEROMETEROFF = 0x66;
    private final int LEDARRAYON = 0x67;
    private final int LEDARRAYOFF = 0x68;
    private final boolean BROADCAST = true;
    private final boolean NO_BROADCAST = false;
    private String ourAddress;
    private final String EMPTY = "";
    private final String PINGREPLY = "Ping Reply";
    private PeripheralsManager pm;

    protected void startApp() throws MIDletStateChangeException {

        //BootloaderListenerService.getInstance().start();   // monitor the USB (if connected) and recognize commands from host

        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        ourAddress = IEEEAddress.toDottedHex(ourAddr);
        pm = new PeripheralsManager();
        System.out.println("Our radio address = " + ourAddress);
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
            pDg.reset();
            /*El formato de la PDU es {direccion, tipo, valor, tiempo, guid, broadcast}*/
            pDg.writeUTF(ourAddress);
            pDg.writeInt(type);
            pDg.writeInt(values.length);
            for (int i = 0; i < values.length; i++) {
                pDg.writeUTF(values[i]);
            }
            pDg.writeLong(System.currentTimeMillis());
            pDg.writeUTF(GUID);
            pDg.writeBoolean(broadcast);
            pCon.send(pDg);
        } catch (Exception ex) {
            System.out.println(ex);
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
            case LIGHTSENSORON:
                this.pm.setLightSensorState("on");
                _temp = "Light sensor ON";
                break;
            case LIGHTSENSOROFF:
                this.pm.setLightSensorState("off");
                _temp = "Light sensor OFF";
                break;
            case TEMPERATURESENSORON:
                this.pm.setTemperatureSensorState("on");
                _temp = "Temperature sensor ON";
                break;
            case TEMPERATURESENSOROFF:
                this.pm.setTemperatureSensorState("off");
                _temp = "Temperature sensor OFF";
                break;
            case ACCELEROMETERON:
                this.pm.setAccelerometerState("on");
                _temp = "Accelerometer ON";
                break;
            case ACCELEROMETEROFF:
                this.pm.setAccelerometerState("off");
                _temp = "Accelerometer OFF";
                break;
            case LEDARRAYON:
                this.pm.setLedArrayState("on");
                _temp = "LedArray ON";
                break;
            case LEDARRAYOFF:
                this.pm.setLedArrayState("off");
                _temp = "LedArray OFF";
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
        System.out.println("Dato recibido mediante peer:" + _type + " Valor: " + _values[0] + " GUID:" + _GUID);
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
        String _temp[] = new String[3];
        switch (type) {
            case PING_PACKET_REQUEST:
                replyToPing();
                break;
            case MEASURE_LIGHT:
                System.out.println("MEASURE LIGHT");
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
                _temp[0] = this.pm.getAcceletometerX();
                _temp[1] = this.pm.getAcceletometerY();
                _temp[2] = this.pm.getAcceletometerZ();
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case LED_SET_COLOR:
                System.out.println("LED SET COLOR");
                if (this.pm.ledSetColor(Integer.parseInt(values[0]))) {
                    _temp[0] = "Color seleccionado";
                } else {
                    _temp[0] = "Error al modificar color";
                }
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case LED_SET_NUMBER:
                System.out.println("LED SET NUMBER");
                if (this.pm.ledSetOn(Integer.parseInt(values[0]))) {
                    _temp[0] = "Leds activados";
                } else {
                    _temp[0] = "Error al activar leds";
                }
                sendToPeer(type, _temp, GUID, broadcast);
                break;
            case LED_SET_OFF:
                System.out.println("LED SET OFF");
                if (this.pm.ledSetOff()) {
                    _temp[0] = "Leds apagados";
                } else {
                    _temp[0] = "Error al desactivar leds";
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
                    _temp[0] = "Leds cambiados a estado " + _cond;
                } else {
                    _temp[0] = "Error al desactivar leds";
                }
                sendToPeer(type, _temp, GUID, broadcast);
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
        }
    }
}
