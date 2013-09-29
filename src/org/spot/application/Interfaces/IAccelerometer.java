package org.spot.application.Interfaces;

/**
 * Interfaz para manejo de las funcionalidades del acelerometro
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public interface IAccelerometer {

    /**
     * Devuelve el valor del acelerometro en el eje X
     *
     * @return Valor del acelerometro
     */
    String getX();

    /**
     * Devuelve el valor del acelerometro en el eje Y
     *
     * @return Valor del acelerometro
     */
    String getY();

    /**
     * Devuelve el valor del acelerometro en el eje Z
     *
     * @return Valor del acelerometro
     */
    String getStatus();
}
