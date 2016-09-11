package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Standorthöhe
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class AltitudeValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_ALTITUDE;

    /**
     * Standorthöhe in Meter
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = -10_000, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 10_000, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double altitude = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public AltitudeValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Standorthöhe zurück
     *
     * @return Standorthöhe
     */
    double getAltitude() {
        return this.altitude;
    }

    /**
     * setzt die Standorthöhe
     *
     * @param altitude Standorthöhe
     */
    public void setAltitude(double altitude) {

        this.altitude = altitude;
    }

    /**
     * fügt die Standorthöhe hinzu
     *
     * @param altitude Standorthöhe
     */
    void pushAltitude(double altitude) {

        setAltitude(altitude);
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
