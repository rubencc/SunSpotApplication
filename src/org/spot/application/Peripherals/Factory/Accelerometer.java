package org.spot.application.Peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import org.spot.application.Interfaces.IAccelerometer;

/**
 * Clase abstracta que maneja las funcionalidades del acelerometro
 *
 * @author rubencc
 */
public abstract class Accelerometer implements IAccelerometer {

    private final String NAME = "Accelerometer";
    private String name;
    protected IAccelerometer3D acc;
    protected final String ACTIVE = "Active";
    protected final String NOT_ACTIVE = "Not Active";
    protected final String NOT_INSTALLED = "NOT INSTALLED";

    public Accelerometer() {
        this.name = NAME;
        this.acc = acc = (IAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
    }

    /**
     * Devuelve el valor del acelerometro en el eje X
     *
     * @return
     */
    public abstract String getX();

    /**
     * Devuelve el valor del acelerometro en el eje Y
     *
     * @return
     */
    public abstract String getY();

    /**
     * Devuelve el valor del acelerometro en el eje Z
     *
     * @return
     */
    public abstract String getZ();

    /**
     * Devuelve el nombre del periferico
     *
     * @return Nombre
     */
    public String getName() {
        return this.name;
    }

    /**
     * Devuelve el estado del periferico
     *
     * @return Estado
     */
    public abstract String getStatus();
}
