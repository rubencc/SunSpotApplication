/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Interfaces;

/**
 * Interfaz para la gestion del array de leds del dispositivo
 *
 * @author rubencc
 */
public interface ILed {

    /**
     * Selecciona el color que muestra el array de leds
     *
     * @param clr -- Valor del color
     * @return
     */
    boolean setColor(int clr);

    /**
     * Apaga los leds y elimina parte de la configuración
     *
     * @return
     */
    boolean setOff();

    /**
     * Selecciona el estado de los leds (condition/off) sin eliminar la
     * configuración
     *
     * @param condition
     * @return
     */
    boolean setOn(boolean condition);

    /**
     * Selecciona el valor que se va a mostrar en el array de leds
     *
     * @param bits
     * @return
     */
    boolean setOn(int bits);

    /**
     * Activa los leds con el valor a mostrar por defecto o el ultimo que se
     * haya configurado
     *
     * @return
     */
    boolean setOn();

    /**
     * Devuelve el mensaje con el estado en el que se encuentra el array de leds
     *
     * @return Estado
     */
    String getStatus();
}
