/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Interfaces;

/**
 * Interfaz para la gestion del array de leds del dispositivo
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public interface ILed {

    /**
     * Selecciona el color que muestra el array de leds
     *
     * @param clr -- Valor del color
     * @return false/true segun se haya realizado la operacion
     */
    boolean setColor(int clr);

    /**
     * Apaga los leds y elimina parte de la configuración
     *
     * @return false/true segun se haya realizado la operacion
     */
    boolean setOff();

    /**
     * Selecciona el estado de los leds (condition/off) sin eliminar la
     * configuración
     *
     * @param condition
     * @return false/true segun se haya realizado la operacion
     */
    boolean setOn(boolean condition);

    /**
     * Selecciona el valor que se va a mostrar en el array de leds
     *
     * @param bits
     * @return false/true segun se haya realizado la operacion
     */
    boolean setOn(int bits);

    /**
     * Activa los leds con el valor a mostrar por defecto o el ultimo que se
     * haya configurado
     *
     * @return false/true segun se haya realizado la operacion
     */
    boolean setOn();

    /**
     * Devuelve el mensaje con el estado en el que se encuentra el array de leds
     *
     * @return Estado del periferico
     */
    String getStatus();
}
