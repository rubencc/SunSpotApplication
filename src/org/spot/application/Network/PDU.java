package org.spot.application.Network;

/**
 * PDU para el intercambio de mensajes con el host application
 *
 * @author rubencc
 */
public class PDU {

    private int type;
    private String GUID;
    private String[] values;
    private boolean broadcast;
    private String addressFrom;

    public PDU(int type, String GUID, String[] values, boolean broadcast) {
        this.GUID = GUID;
        this.broadcast = broadcast;
        this.type = type;
        this.values = values;
        this.addressFrom = null;
    }

    public PDU(int type, String GUID, String[] values, boolean broadcast, String addressFrom) {
        this.GUID = GUID;
        this.broadcast = broadcast;
        this.type = type;
        this.values = values;
        this.addressFrom = addressFrom;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the GUID
     */
    public String getGUID() {
        return GUID;
    }

    /**
     * @return the values
     */
    public String[] getValues() {
        return values;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return addressFrom;
    }

    /**
     * @return the broadcast
     */
    public boolean isBroadcast() {
        return broadcast;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @param GUID the GUID to set
     */
    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    /**
     * @param values the values to set
     */
    public void setValues(String[] values) {
        this.values = values;
    }

    /**
     * @param broadcast the broadcast to set
     */
    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    public void setFirsValue(String value) {
        this.values = new String[1];
        this.values[0] = value;
    }
}
