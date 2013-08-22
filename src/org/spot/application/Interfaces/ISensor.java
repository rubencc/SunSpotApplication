/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Interfaces;

/**
 * Interfaz de metodos comunes para los sensores del dispostivo
 *
 * @author rubencc
 */
public interface ISensor {

    /**
     * Devuelve una medida
     *
     * @return medida
     */
    String getMeasure();

    /**
     * Devuelve el estado del sensor
     *
     * @return estado
     */
    String getStatus();
}
