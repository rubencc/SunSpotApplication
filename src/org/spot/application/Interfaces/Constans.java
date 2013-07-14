/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Interfaces;

/**
 *
 * @author rubencc
 */
public interface Constans {

    public final int PING_PACKET_REQUEST = 0x30;
    public final int PING_PACKET_REPLY = 0x33;
    public final int MEASURE_LIGHT = 0x40;
    public final int MEASURE_TEMPERATURE = 0x41;
    public final int ACCELEROMETER_X = 0x42;
    public final int ACCELEROMETER_Y = 0x43;
    public final int ACCELEROMETER_Z = 0x44;
    public final int MEASURE_ACCELEROMETER = 0x45;
    public final int LED_SET_COLOR = 0x46;
    public final int LED_SET_OFF = 0x47;
    public final int LED_SET_STATE = 0x48;
    public final int LED_SET_NUMBER = 0x49;
    public final int LED_TIME = 0x4A;
    public final int LED_BLINK = 0x4B;
    public final int CHECK = 0x50;
    public final int FEATURE = 0x60;
    public final int LIGHTSENSOR_ON = 0x61;
    public final int LIGHTSENSOR_OFF = 0x62;
    public final int TEMPERATURESENSOR_ON = 0x63;
    public final int TEMPERATURESENSOR_OFF = 0x64;
    public final int ACCELEROMETER_ON = 0x65;
    public final int ACCELEROMETER_OFF = 0x66;
    public final int LEDARRAY_ON = 0x67;
    public final int LEDARRAY_OFF = 0x68;
    public final int LIGHT_SENSOR_NOT_PRESENT = 0x69;
    public final int TEMPERATURE_SENSOR_NOT_PRESENT = 0x6A;
    public final int ACCELEROMETER_NOT_PRESENT = 0x6B;
    public final int LED_ARRAY_NOT_PRESENT = 0x6C;
    public final int READ_CONFIGURATION = 0x80;
    public final boolean BROADCAST = true;
    public final boolean NO_BROADCAST = false;
}
