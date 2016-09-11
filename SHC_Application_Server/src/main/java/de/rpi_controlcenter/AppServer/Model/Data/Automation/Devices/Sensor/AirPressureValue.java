package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Luftdruck
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class AirPressureValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_AIR_PRESSURE;

    /**
     * Luftdruck in hPa
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 500, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 1500, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double airPressure = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public AirPressureValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt den Luftdruck zurück
     *
     * @return Luftdruck
     */
    double getAirPressure() {
        return this.airPressure;
    }

    /**
     * setzt den Luftdruck
     *
     * @param airPressure Luftdruck
     */
    public void setAirPressure(double airPressure) {

        this.airPressure = airPressure;
    }

    /**
     * füght den Luftdruck hinzu
     *
     * @param airPressure Luftdruck
     */
    void pushAirPressure(double airPressure) {

        setAirPressure(airPressure);
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
