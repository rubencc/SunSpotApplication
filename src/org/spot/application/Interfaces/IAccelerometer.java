package org.spot.application.Interfaces;

/**
 * Interfaz para manejo de las funcionalidades del acelerometro
 *
 * @author rubencc
 */
public interface IAccelerometer {

    /**
     * Devuelve el valor del acelerometro en el eje X
     *
     * @return
     */
    String getX();

    /**
     * Devuelve el valor del acelerometro en el eje Y
     *
     * @return
     */
    String getY();

    /**
     * Devuelve el valor del acelerometro en el eje Z
     *
     * @return
     */
    String getStatus();
}
