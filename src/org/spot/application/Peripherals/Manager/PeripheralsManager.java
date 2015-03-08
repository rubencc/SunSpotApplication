package org.spot.application.Peripherals.Manager;

import org.spot.application.Peripherals.Actuators.Accelerometer;
import org.spot.application.Peripherals.Actuators.LedArray;
import org.spot.application.Peripherals.Factory.IPeripheralsFactory;
import org.spot.application.Peripherals.Factory.PeripheralsFactory;
import org.spot.application.Peripherals.Sensors.Sensor;

/**
 * Clase que implementa IPeripheralsManager encapsulando el comportamiento de
 * los perifericos
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class PeripheralsManager implements IPeripheralsManager {

    private Sensor lightSensor;
    private Sensor temperatureSensor;
    private LedArray ledArray;
    private Accelerometer accelerometer;
    private IPeripheralsFactory peripheralsFactory;
    private static PeripheralsManager INSTANCE = new PeripheralsManager();

    private PeripheralsManager() {
        this.peripheralsFactory = new PeripheralsFactory();
        this.lightSensor = this.peripheralsFactory.getLightSensor("on");
        this.temperatureSensor = this.peripheralsFactory.getTemperatureSensor("on");
        this.accelerometer = this.peripheralsFactory.getAccelerometer("on");
        this.ledArray = this.peripheralsFactory.getLedArray("on");
        this.ledArray.setColor(9);
    }

    public static PeripheralsManager getInstance() {
        return INSTANCE;
    }

    /**
     * Medida del sensor de luz.
     *
     * @return Medida del sensor de luz
     */
    public String getLightMeasure() {
        return this.lightSensor.getMeasure();
    }

    /**
     * Medida del sensor de temperatura.
     *
     * @return Medida del sensor de temperatura
     */
    public String getTemperatureMeasure() {
        return this.temperatureSensor.getMeasure();
    }

    /**
     * Medida del acelerometro X
     *
     * @return Medida del acelerometro
     */
    public String getAcceletometerX() {
        return this.accelerometer.getX();
    }

    /**
     * Medida del acelerometro Y
     *
     * @return Medida del acelerometro
     */
    public String getAcceletometerY() {
        return this.accelerometer.getY();
    }

    /**
     * Medida del acelerometro Z
     *
     * @return Medida del acelerometro
     */
    public String getAcceletometerZ() {
        return this.accelerometer.getZ();
    }

    /**
     * Pone todos los leds en off.
     *
     * @return Medida del acelerometro
     */
    public synchronized boolean ledSetOff() {
        return this.ledArray.setOff();
    }

    /**
     * Pone todos los leds en el estado indicado.
     *
     * @param condition - false/true
     * @return false/true segun se haya podido ejecutar la operacion
     */
    public synchronized boolean ledSetOn(boolean condition) {
        return this.ledArray.setOn(condition);
    }

    /**
     * Activa los leds para representar la secuencia numerica.
     *
     * @param value - Valor en decimal que representaran los leds
     * @return false/true segun se haya podido ejecutar la operacion
     */
    public synchronized boolean ledSetOn(int value) {
        return this.ledArray.setOn(value);
    }

    /**
     * Activa los leds con la ultima configuracion conocida.
     *
     * @return false/true segun se haya podido ejecutar la operacion
     */
    public synchronized boolean ledSetOn() {
        return this.ledArray.setOn();
    }

    /**
     * Indica si el array de leds esta mostrando algun numero
     *
     * @return false/true segun se haya podido ejecutar la operacion
     */
    public synchronized boolean hasANumber() {
        return this.ledArray.hasANumber();
    }

    /**
     * Establece el color de los leds.
     *
     * @param color - Codigo de color
     * @return false/true segun se haya podido ejecutar la operacion
     */
    public synchronized boolean ledSetColor(int color) {
        return this.ledArray.setColor(color);
    }

    /**
     * Devuelve el estado de todos los perifericos del dispositivo
     *
     * @return String[] con el estado de los dispositivos
     */
    public String[] getStatus() {
        String[] _status = new String[4];
        _status[0] = this.lightSensor.getStatus();
        _status[1] = this.temperatureSensor.getStatus();
        _status[2] = this.accelerometer.getStatus();
        _status[3] = this.ledArray.getStatus();
        return _status;
    }

    /**
     * Devuelve el estado del sensor de luz
     *
     * @return String con el estado del sensor
     */
    public String getStatusLightSensor() {
        return this.lightSensor.getStatus();
    }

    /**
     * Devuelve el estado del sensor de temperatura
     *
     * @return String con el estado del sensor
     */
    public String getStatusTemperatureSensor() {
        return this.temperatureSensor.getStatus();
    }

    /**
     * Devuelve el estado del acelerometro
     *
     * @return String con el estado del sensor
     */
    public String getStatusAcelerometer() {
        return this.accelerometer.getStatus();
    }

    /**
     * Devuelve el estado del array de leds
     *
     * @return String con el estado del array
     */
    public String getStatusLedArray() {
        return this.ledArray.getStatus();
    }

    /**
     * Devuelve el estado de actividad del sensor de luz
     *
     * @return false/true
     */
    public boolean isLightSensorActive() {
        return this.lightSensor.isActive();
    }

    /**
     * Devuelve el estado de actividad del sensor de temperatura
     *
     * @return false/true
     */
    public boolean isTemperatureSensorActive() {
        return this.temperatureSensor.isActive();
    }

    /**
     * Devuelve el estado de actividad del array de leds
     *
     * @return false/true
     */
    public boolean isLedArrayActive() {
        return this.ledArray.isActive();
    }

    /**
     * Establece la clase Accelerometer que manejara el manager
     *
     */
    public void setAccelerometer(Accelerometer accelerometer) {
        this.accelerometer = accelerometer;
    }

    /**
     * Establece la clase LedArray que manejara el manager
     *
     */
    public void setLedArray(LedArray ledArray) {
        this.ledArray = ledArray;
    }

    /**
     * Establece la clase LightSensor que manejara el manager
     *
     */
    public void setLightSensor(Sensor lightSensor) {
        this.lightSensor = lightSensor;
    }

    /**
     * Establece la clase TemperatureSensor que manejara el manager
     *
     */
    public void setTemperatureSensor(Sensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }
}
