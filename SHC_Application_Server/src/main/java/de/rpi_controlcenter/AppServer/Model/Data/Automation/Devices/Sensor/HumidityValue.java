package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Luftfeuchte
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class HumidityValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_HUMIDITY;

    /**
     * Luftfeuchte in Prozent
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 100, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double humidity = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public HumidityValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Luftfeuchte zurück
     *
     * @return Luftfeuchte
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * setzt die Luftfeuchte
     *
     * @param humidity Luftfeuchte
     */
    public void setHumidity(double humidity) {

        this.humidity = humidity;
    }

    /**
     * fügt die Luftfeuchte hinzu
     *
     * @param humidity Luftfeuchte
     */
    public void pushHumidity(double humidity) {

        setHumidity(humidity);
        setLastPushTime(LocalDateTime.now());
    }

    /**
     * gibt den Typ des Elementes zurück
     *
     * @return Typ ID
     */
    @Override
    public Type getType() {
        return type;
    }
}
