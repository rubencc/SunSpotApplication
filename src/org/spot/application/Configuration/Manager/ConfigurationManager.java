package org.spot.application.Configuration.Manager;

import org.spot.application.Peripherals.Factory.IPeripheralsFactory;
import org.spot.application.Peripherals.Factory.PeripheralsFactory;
import org.spot.application.Peripherals.Manager.IPeripheralsManager;

/**
 * Clase que implementa IConfigurationManager para la gestion de los perifericos
 *
 * @author Rubén Carretero <rubencc@gmail.com>
 */
public class ConfigurationManager implements IConfigurationManager {

    private final IPeripheralsManager peripheralsManager;
    private final IPeripheralsFactory peripheralsFactory;

    public ConfigurationManager(IPeripheralsManager pm) {
        this.peripheralsManager = pm;
        this.peripheralsFactory = new PeripheralsFactory();
    }

    /**
     * Activa o desactiva sensores
     *
     * @param type
     * @throws IOException
     * @throws NumberFormatException
     */
    public String configFeatures(String value) {

        String _temp = null;

        switch (Integer.parseInt(value)) {
            case org.spot.application.Interfaces.Constans.LIGHTSENSOR_ON:
                this.peripheralsManager.setLightSensor(peripheralsFactory.getLightSensor("on"));
                _temp = "Light sensor ON";
                break;
            case org.spot.application.Interfaces.Constans.LIGHTSENSOR_OFF:
                this.peripheralsManager.setLightSensor(peripheralsFactory.getLightSensor("off"));
                _temp = "Light sensor OFF";
                break;
            case org.spot.application.Interfaces.Constans.LIGHT_SENSOR_NOT_PRESENT:
                this.peripheralsManager.setLightSensor(peripheralsFactory.getLightSensor("notpresent"));
                _temp = "Sensor unavailable";
                break;
            case org.spot.application.Interfaces.Constans.TEMPERATURESENSOR_ON:
                this.peripheralsManager.setTemperatureSensor(peripheralsFactory.getTemperatureSensor("on"));
                _temp = "Temperature sensor ON";
                break;
            case org.spot.application.Interfaces.Constans.TEMPERATURESENSOR_OFF:
                this.peripheralsManager.setTemperatureSensor(peripheralsFactory.getTemperatureSensor("off"));
                _temp = "Temperature sensor OFF";
                break;
            case org.spot.application.Interfaces.Constans.TEMPERATURE_SENSOR_NOT_PRESENT:
                this.peripheralsManager.setTemperatureSensor(peripheralsFactory.getTemperatureSensor("notpresent"));
                _temp = "Sensor unavailable";
                break;
            case org.spot.application.Interfaces.Constans.ACCELEROMETER_ON:
                this.peripheralsManager.setAccelerometer(peripheralsFactory.getAccelerometer("on"));
                _temp = "Accelerometer ON";
                break;
            case org.spot.application.Interfaces.Constans.ACCELEROMETER_OFF:
                this.peripheralsManager.setAccelerometer(peripheralsFactory.getAccelerometer("off"));
                _temp = "Accelerometer OFF";
                break;
            case org.spot.application.Interfaces.Constans.ACCELEROMETER_NOT_PRESENT:
                this.peripheralsManager.setAccelerometer(peripheralsFactory.getAccelerometer("notpresent"));
                _temp = "Acelerometer unavailable";
                break;
            case org.spot.application.Interfaces.Constans.LEDARRAY_ON:
                this.peripheralsManager.setLedArray(peripheralsFactory.getLedArray("on"));
                _temp = "LedArray ON";
                break;
            case org.spot.application.Interfaces.Constans.LEDARRAY_OFF:
                this.peripheralsManager.ledSetOff();
                this.peripheralsManager.setLedArray(peripheralsFactory.getLedArray("off"));
                _temp = "LedArray OFF";
                break;
            case org.spot.application.Interfaces.Constans.LED_ARRAY_NOT_PRESENT:
                this.peripheralsManager.ledSetOff();
                this.peripheralsManager.setLedArray(peripheralsFactory.getLedArray("notpresent"));
                _temp = "LedArray unavailable";
                break;
        }

        return _temp;
    }
}
