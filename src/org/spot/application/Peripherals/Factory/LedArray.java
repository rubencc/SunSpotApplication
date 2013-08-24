package org.spot.application.Peripherals.Factory;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import org.spot.application.Interfaces.ILed;

/**
 * Clase abstracta para la gestion de las funcionalidades del array de leds
 *
 * @author rubencc
 */
public abstract class LedArray implements ILed {

    private String name;
    private final String NAME = "LedArray";
    protected ITriColorLEDArray leds;
    protected final String ACTIVE = "Active";
    protected final String NOT_ACTIVE = "Not Active";
    protected final String NOT_INSTALLED = "NOT INSTALLED";
    private boolean active;

    public LedArray() {
        this.name = NAME;
        this.leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        this.active = false;
    }

    /**
     * Indica si el array de leds esta mostrando algun numero
     *
     * @return
     */
    public abstract boolean hasANumber();

    /**
     * Selecciona el color que muestra el array de leds
     *
     * @param clr -- Valor del color
     * @return
     */
    public abstract boolean setColor(int clr);

    /**
     * Apaga los leds y elimina parte de la configuración
     *
     * @return
     */
    public abstract boolean setOff();

    /**
     * Selecciona el estado de los leds (on/off) sin eliminar la configuración
     *
     * @param condition
     * @return
     */
    public abstract boolean setOn(boolean condition);

    /**
     * Selecciona el valor que se va a mostrar en el array de leds
     *
     * @param bits
     * @return
     */
    public abstract boolean setOn(int bits);

    /**
     * Activa los leds con el valor a mostrar por defecto o el ultimo que se
     * haya configurado
     *
     * @return
     */
    public abstract boolean setOn();

    /**
     * Devuelve el nombre del dispostivo
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    public abstract String getStatus();

    /**
     * Devuelve el estado de actividad del array de leds
     *
     * @return Estado del array de leds
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Marca si el array de leds esta activo o no
     *
     * @param condition -- Condición de actividad
     */
    public void setActive(boolean condition) {
        this.active = condition;
    }
}
