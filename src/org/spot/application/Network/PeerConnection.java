/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Network;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 *
 * @author rubencc
 */
public class PeerConnection {

    private final int PEER_PORT = 100;
    private RadiogramConnection pCon = null;
    private Datagram pDg = null;
    private boolean peerConnected;
    private String peerAddress;
    private static PeerConnection INSTANCE = new PeerConnection();
    private String ourAddress;

    private PeerConnection() {
        this.peerConnected = false;
    }

    public synchronized static PeerConnection getInstance() {
        return INSTANCE;
    }

    /**
     * @return the peerConnected
     */
    public synchronized boolean isPeerConnected() {
        return peerConnected;
    }

    /**
     * @return the peerAddress
     */
    public synchronized String getPeerAddress() {
        return peerAddress;
    }

    /**
     * @param peerConnected the peerConnected to set
     */
    public synchronized void setPeerConnected(boolean peerConnected) {
        this.peerConnected = peerConnected;
    }

    /**
     * @param peerAddress the peerAddress to set
     */
    public synchronized void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

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
     * @return the ourAddress
     */
    public synchronized String getOurAddress() {
        return ourAddress;
    }

    /**
     * @param ourAddress the ourAddress to set
     */
    public synchronized void setOurAddress(String ourAddress) {
        this.ourAddress = ourAddress;
    }

    public synchronized void close() {
        try {
            this.pCon.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized boolean packetsAvailable() {
        if (this.peerConnected) {
            return this.pCon.packetsAvailable();
        } else {
            return false;
        }
    }
}
