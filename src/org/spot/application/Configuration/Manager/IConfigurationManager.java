/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.spot.application.Configuration.Manager;

/**
 * Interfaz que define la gestion de la configuracion de los perifericos
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public interface IConfigurationManager {

    /**
     * Activa o desactiva sensores
     *
     * @param type
     * @throws IOException
     * @throws NumberFormatException
     */
    String configFeatures(String value);
}
