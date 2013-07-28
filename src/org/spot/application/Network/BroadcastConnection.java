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
public class BroadcastConnection {

    private final int BROADCAST_PORT = 66;
    private RadiogramConnection bCon = null;
    private Datagram bDg = null;
    private boolean newBroadcast = false;
    private String peerAddress = null;
    private final String PINGREPLY = "Ping Reply";
    private static BroadcastConnection INSTANCE = new BroadcastConnection();

    private BroadcastConnection() {
        try {
            this.bCon = (RadiogramConnection) Connector.open("radiogram://:" + BROADCAST_PORT);
            this.bDg = this.bCon.newDatagram(this.bCon.getMaximumLength());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static BroadcastConnection getInstance() {
        return INSTANCE;
    }

    /**
     * Lee las PDUs de la conexion broadcast y marca las respuestas como tales
     *
     * @throws NumberFormatException
     * @throws IOException
     */
    public synchronized PDU readBroadcast() {
        PDU pdu = null;
        try {
            this.bCon.receive(bDg);
            if (this.peerAddress == null || !this.peerAddress.equals(bDg.getAddress())) {
                this.newBroadcast = true;
                this.peerAddress = bDg.getAddress();
            }
            int _type = this.bDg.readInt();
            int _count = this.bDg.readInt();
            String _values[] = new String[_count];
            for (int i = 0; i < _count; i++) {
                _values[i] = this.bDg.readUTF();
            }
            String _GUID = this.bDg.readUTF();
            //processPDU(_type, _GUID, _broadcast, _values);
            this.bDg.reset();
            pdu = new PDU(_type, _GUID, _values, true, bDg.getAddress());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pdu;
    }

    public synchronized boolean packetsAvailable() {
        return this.bCon.packetsAvailable();
    }

    public synchronized void close() {
        try {
            this.bCon.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the firstBroadcast
     */
    public synchronized boolean isNewBroadcast() {
        return this.newBroadcast;
    }

    /**
     * @param firstBroadcast the firstBroadcast to set
     */
    public synchronized void setNewBroadcast(boolean cond) {
        this.newBroadcast = cond;
    }

    /**
     * @return the peerAddress
     */
    public synchronized String getPeerAddress() {
        return peerAddress;
    }
}
