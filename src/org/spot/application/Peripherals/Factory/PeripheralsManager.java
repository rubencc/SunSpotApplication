package org.spot.application.Peripherals.Factory;

/**
 * Clase que encapsula la gestion y el comportamiento de los perifericos
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class PeripheralsManager {

    private Sensor lightSensor;
    private Sensor temperatureSensor;
    private LedArray ledArray;
    private Accelerometer accelerometer;
    private static PeripheralsManager INSTANCE = new PeripheralsManager();

    private PeripheralsManager() {
        this.lightSensor = new LightActiveSensor();
        this.temperatureSensor = new TemperatureActiveSensor();
        this.accelerometer = new AccelerometerActive();
        this.ledArray = new LedArrayActive();
        this.ledArray.setColor(9);
    }

    public static PeripheralsManager getInstance() {
        return INSTANCE;
    }

    /**
     * Configura el estado de los sensores de luz.
     *
     * @param state "on","off","notpresent"
     */
    public void setLightSensorStatus(String state) {

        if (state.equals("on")) {
            this.lightSensor = new LightActiveSensor();
        }

        if (state.equals("off")) {
            this.lightSensor = new LightNotActiveSensor();
        }

        if (state.equals("notpresent")) {
            this.lightSensor = new NotLightSensor();
        }

    }

    /**
     * Configura el estado de los sensores de temperatura.
     *
     * @param state "on","off","notpresent"
     */
    public void setTemperatureSensorStatus(String state) {

        if (state.equals("on")) {
            this.temperatureSensor = new TemperatureActiveSensor();
        }

        if (state.equals("off")) {
            this.temperatureSensor = new TemperatureNotActiveSensor();
        }

        if (state.equals("notpresent")) {
            this.temperatureSensor = new NotTemperatureSensor();
        }

    }

    /**
     * Configura el estado del acelerometro.
     *
     * @param state
     */
    public void setAccelerometerStatus(String state) {
        if (state.equals("on")) {
            this.accelerometer = new AccelerometerActive();
        }

        if (state.equals("off")) {
            this.accelerometer = new AccelerometerNotActive();
        }

        if (state.equals("notpresent")) {
            this.accelerometer = new NotAccelerometer();
        }
    }

    /**
     * Configura el estado del array de leds.
     *
     * @param state
     */
    public void setLedArrayStatus(String state) {
        if (state.equals("on")) {
            this.ledArray = new LedArrayActive();
        }

        if (state.equals("off")) {
            this.ledArray = new LedArrayNotActive();
        }

        if (state.equals("notpresent")) {
            this.ledArray = new NotLedArray();
        }
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

    public String[] getStatus() {
        String[] _status = new String[4];
        _status[0] = this.lightSensor.getStatus();
        _status[1] = this.temperatureSensor.getStatus();
        _status[2] = this.accelerometer.getStatus();
        _status[3] = this.ledArray.getStatus();
        return _status;
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
}
