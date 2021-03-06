package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Entfernung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class DistanceValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_DISTANCE;

    /**
     * Entfernung in mm
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = -100_000, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 100_000, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double distance = 0.0;

    /**
     * Offset in mm
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = -100_000, errorCode = 10206, message = "Ungültiger Offset")
    @ValidateMax(value = 100_000, errorCode = 10206, message = "Ungültiger Offset")
    private double offset = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public DistanceValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Entfernung zurück
     *
     * @return Entfernung
     */
    public double getDistance() {
        return distance;
    }

    /**
     * gibt die Entfernung mit Offset zurück
     *
     * @return Entfernung
     */
    public double getDistanceWithOffset() {
        return distance + this.offset;
    }

    /**
     * setzt die Entfernung
     *
     * @param distance Entfernung
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * fügt die Entfernung hinzu
     *
     * @param distance Entfernung
     */
    public void pushDistance(double distance) {

        setDistance(distance);
        setLastPushTime(LocalDateTime.now());
    }

    /**
     * setzt das Offset
     *
     * @return Offset
     */
    public double getOffset() {
        return offset;
    }

    /**
     * gibt das Offset zurück
     *
     * @param offset Offset
     */
    public void setOffset(double offset) {

        this.offset = offset;
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
