package org.spot.application.Network;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 * Clase de instancia unica para el envio y recepcion de mensajes en modo peer
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class PeerConnection {

    //Puerto de la conexión peer
    private final int PEER_PORT = 100;
    //Conexión peer
    private RadiogramConnection pCon = null;
    //Datagrama
    private Datagram pDg = null;
    //Condición de conectado con el peer
    private boolean peerConnected;
    //Dirección del peer
    private String peerAddress;
    //Instancia de la clase
    private static PeerConnection INSTANCE = new PeerConnection();
    //Dirección del dispostivo
    private String ourAddress;

    private PeerConnection() {
        this.peerConnected = false;
    }

    /**
     * Instancia unica del objeto segun patron singlenton
     *
     * @return Instancia unica de la clase
     */
    public synchronized static PeerConnection getInstance() {
        return INSTANCE;
    }

    /**
     * Informa si el dispostivio esta conectado al peer
     *
     * @return condición
     */
    public synchronized boolean isPeerConnected() {
        return peerConnected;
    }

    /**
     * Establece el estado de la conexión con el peer
     *
     * @param condition
     */
    public synchronized void setPeerConnected(boolean condition) {
        this.peerConnected = condition;
    }

    /**
     * Devuelve la dirección de red del peer
     *
     * @return Dirección de red
     */
    public synchronized String getPeerAddress() {
        return peerAddress;
    }

    /**
     * Establece la direccion del peer
     *
     * @param peerAddress -- Dirección de red
     */
    public synchronized void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    /**
     * Devuelve la dirección de red del dispostivo
     *
     * @return Dirección de red
     */
    public synchronized String getOurAddress() {
        return ourAddress;
    }

    /**
     * Establece la direccion de red del dispostivo
     *
     * @param ourAddress -- Dirección de red
     */
    public synchronized void setOurAddress(String ourAddress) {
        this.ourAddress = ourAddress;
    }

    /**
     * Conecta con el dispositivo peer indicado en peerAddress
     */
    public synchronized void connectToPeer() {
        try {
            //Inicia una conexion peer
            this.pCon = (RadiogramConnection) Connector.open("radiogram://" + peerAddress + ":" + PEER_PORT);
            //pCon.setTimeout(5000);
            this.pDg = this.pCon.newDatagram(this.pCon.getMaximumLength());
            peerConnected = true;
            System.out.println("[Spot] Conectado a: " + peerAddress);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Envia una PDU a traves de la conexion peer.
     *
     * @param pdu con la informacion a enviar
     */
    public synchronized void sendToPeer(PDU pdu) {
        try {
            String[] _values = pdu.getValues();
            //System.out.print(type + " " + GUID + " " + broadcast);
            this.pDg.reset();
            /*El formato de la PDU es {direccion, tipo, logitud array, valor, tiempo, guid, broadcast}*/
            this.pDg.writeUTF(getOurAddress());
            this.pDg.writeInt(pdu.getType());
            this.pDg.writeInt(_values.length);
            for (int i = 0; i < _values.length; i++) {
                if (_values[i] != null) {
                    this.pDg.writeUTF(_values[i]);
                }
            }
            this.pDg.writeLong(System.currentTimeMillis());
            this.pDg.writeUTF(pdu.getGUID());
            this.pDg.writeBoolean(pdu.isBroadcast());
            this.pCon.send(this.pDg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Recibe una PDU a traves de la conexion peer.
     *
     * @return PDU PDU recibida
     */
    public synchronized PDU readPeer() {
        PDU pdu = null;
        try {
            this.pCon.receive(pDg);
            int _type = this.pDg.readInt();
            int _count = this.pDg.readInt();
            String _values[] = new String[_count];
            for (int i = 0; i < _count; i++) {
                _values[i] = this.pDg.readUTF();
            }
            String _GUID = this.pDg.readUTF();
            pDg.reset();
            pdu = new PDU(_type, _GUID, _values, false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pdu;
    }

    /**
     * Cierra la conexion peer
     */
    public synchronized void close() {
        try {
            if (this.peerConnected) {
                this.pCon.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Comprueba si hay paquetes disposibles en caso de estar conectado al peer.
     *
     * @return false/true segun haya paquetes disponibles para leer
     */
    public synchronized boolean packetsAvailable() {
        if (this.peerConnected) {
            return this.pCon.packetsAvailable();
        } else {
            return false;
        }
    }
}
