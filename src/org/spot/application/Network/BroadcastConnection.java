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

    //Puerto para la conexion broadcast
    private final int BROADCAST_PORT = 66;
    //Conexión broadcast
    private RadiogramConnection bCon = null;
    //Datagrama
    private Datagram bDg = null;
    //Condición de nuevo host application
    private boolean newBroadcast = false;
    //Dirección de red del emisor broadcast
    private String senderAddress = null;
    //Instnacia de la clase
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
     * @return PDU
     */
    public synchronized PDU readBroadcast() {
        PDU pdu = null;
        try {
            this.bCon.receive(bDg);
            if (this.senderAddress == null || !this.senderAddress.equals(bDg.getAddress())) {
                this.newBroadcast = true;
                this.senderAddress = bDg.getAddress();
            }
            int _type = this.bDg.readInt();
            int _count = this.bDg.readInt();
            String _values[] = new String[_count];
            for (int i = 0; i < _count; i++) {
                _values[i] = this.bDg.readUTF();
            }
            String _GUID = this.bDg.readUTF();
            this.bDg.reset();
            pdu = new PDU(_type, _GUID, _values, true, bDg.getAddress());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pdu;
    }

    /**
     * Comprueba si hay paquetes disponibles en la conexion broadcast.
     *
     * @return
     */
    public synchronized boolean packetsAvailable() {
        return this.bCon.packetsAvailable();
    }

    /**
     * Cierra la conexion.
     */
    public synchronized void close() {
        try {
            this.bCon.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Devuelve la condicion de nuevo host application enviando a traves de la
     * conexión broadcast
     *
     * @return
     */
    public synchronized boolean isNewBroadcast() {
        return this.newBroadcast;
    }

    /**
     * Establece la condición de nuevo host application enviando a traves de la
     * conexión broadcast
     *
     * @param condition
     */
    public synchronized void setNewBroadcast(boolean condition) {
        this.newBroadcast = condition;
    }

    /**
     * Obtiene la dirección de red del host application que usa esta conexión
     * broadcast
     *
     * @return Dirección de red del
     */
    public synchronized String getSenderAddress() {
        return senderAddress;
    }
}
