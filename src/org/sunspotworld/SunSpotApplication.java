/*
 * SunSpotApplication.java
 *
 * Created on 06-abr-2013 20:04:12;
 */
package org.sunspotworld;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.NoRouteException;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.ITemperatureInput;

import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;


import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

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
    private final int CHECK = 0x50;
    private final int FEATURE = 0x60;
    private final int LIGHTSENSORON = 0x61;
    private final int LIGHTSENSOROFF = 0x62;
    private final int TEMPERATURESENSORON = 0x63;
    private final int TEMPERATURESENSOROFF = 0x64;
    private final boolean BROADCAST = true;
    private final boolean NO_BROADCAST = false;
    private String ourAddress;
    private ILightSensor lightSensor;
    private ITemperatureInput tempSensor;
    private boolean condls;
    private boolean condts;
    private final String EMPTY = "";

    protected void startApp() throws MIDletStateChangeException {

        //BootloaderListenerService.getInstance().start();   // monitor the USB (if connected) and recognize commands from host

        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        ourAddress = IEEEAddress.toDottedHex(ourAddr);
        lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
        tempSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
        condls = true;
        condts = true;
        System.out.println("Our radio address = " + ourAddress);
        try {
            //Abre la conexion en modo servidor para recibir datos broadcast
            bCon = (RadiogramConnection) Connector.open("radiogram://:" + BROADCAST_PORT);
            bDg = bCon.newDatagram(bCon.getMaximumLength());
            System.out.println("Escuchando brodascast");
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
        sendToPeer(PING_PACKET_REPLY, EMPTY, "GUID", BROADCAST);
    }

    /**
     * Envia una respuesta a traves de la conexion peer.
     *
     * @param type Tipo de respuesta
     * @param value Valor de la respuesta
     * @param GUID Identificador de la petición
     * @param broadcast Condicion de mensaje de broadcast
     */
    private void sendToPeer(int type, String value, String GUID, boolean broadcast) {
        try {
            pDg.reset();
            /*El formato de la PDU es {direccion, tipo, valor, tiempo, guid, broadcast}*/
            pDg.writeUTF(ourAddress);
            pDg.writeInt(type);
            pDg.writeUTF(value);
            pDg.writeLong(System.currentTimeMillis());
            pDg.writeUTF(GUID);
            pDg.writeBoolean(broadcast);
            pCon.send(pDg);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Obtiene el valor del sensor de luz si esta activo en caso de no estarlo
     * devuelve un mensaje de error
     *
     * @return
     */
    private String getLightMeasure() {
        String _temp = null;
        try {
            if (condls) {
                int _value = lightSensor.getValue();
                _temp = String.valueOf(_value);
                System.out.println("Light sensor value " + _value);
            } else {
                _temp = String.valueOf("Sensor OFF");
                System.out.println("Sensor OFF");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return _temp;
    }

    /**
     * Obtiene el valor del sensor de temperatura si esta activo en caso de no
     * estarlo devuelve un mensaje de error
     *
     * @return
     */
    private String getTemperatureMeasure() {
        String _temp = null;
        try {
            if (condls) {
                double _value = tempSensor.getCelsius();
                _temp = String.valueOf(_value);
                System.out.println("Temperature sensor value " + _value);
            } else {
                _temp = String.valueOf("Sensor OFF");
                System.out.println("Sensor OFF");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return _temp;
    }

    private void setLightSensorOn() {
        this.condls = true;
        System.out.println("Light sensor " + condls);
    }

    private void setLightSensorOff() {
        this.condls = false;
        System.out.println("Light sensor " + condls);
    }

    private void setTemperatureSensorOn() {
        this.condts = true;
        System.out.println("Temperature sensor " + condts);
    }

    private void setTemperatureSensorOff() {
        this.condts = false;
        System.out.println("Temperature sensor " + condts);
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
        if (Integer.parseInt(value) == LIGHTSENSORON) {
            setLightSensorOn();
            _temp = "Light sensor ON";
        }
        if (Integer.parseInt(value) == LIGHTSENSOROFF) {
            setLightSensorOff();
            _temp = "Light sensor OFF";
        }
        if (Integer.parseInt(value) == TEMPERATURESENSORON) {
            setTemperatureSensorOn();
            _temp = "Temperature sensor ON";
        }
        if (Integer.parseInt(value) == TEMPERATURESENSOROFF) {
            setTemperatureSensorOff();
            _temp = "Temperature sensor OFF";
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
        String _value = bDg.readUTF();
        String _GUID = bDg.readUTF();
        processPDU(_type, _GUID, _broadcast, _value);
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
        String _value = pDg.readUTF();
        String _GUID = pDg.readUTF();
        System.out.println("Dato recibido mediante peer:" + _type + " Valor: " + _value + " GUID:" + _GUID);
        processPDU(_type, _GUID, _broadcast, _value);
        pDg.reset();
    }

    /**
     * Procesa cada una de las PDUs recibidas
     *
     * @param _type Tipo de PDU
     * @param _GUID Identificador asignado a la petición
     * @param _broadcast Condicion de broadcast
     * @param _value Valor del comando
     * @throws NumberFormatException
     * @throws IOException
     */
    private void processPDU(int _type, String _GUID, boolean _broadcast, String _value) throws NumberFormatException, IOException {
        String _temp;
        switch (_type) {
            case PING_PACKET_REQUEST:
                replyToPing();
                break;
            case MEASURE_LIGHT:
                _temp = getLightMeasure();
                sendToPeer(_type, _temp, _GUID, _broadcast);
                break;
            case MEASURE_TEMPERATURE:
                _temp = getTemperatureMeasure();
                sendToPeer(_type, _temp, _GUID, _broadcast);
                break;
            case CHECK:
                System.out.println("CHECK");
                sendToPeer(_type, "Ping Reply", _GUID, _broadcast);
                break;
            case FEATURE:
                System.out.println("FEATURES");
                _temp = configFeatures(_value);
                sendToPeer(_type, _temp, _GUID, _broadcast);
                break;
        }
    }
}
