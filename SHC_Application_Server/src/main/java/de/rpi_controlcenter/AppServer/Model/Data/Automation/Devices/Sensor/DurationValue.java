package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Laufzeit
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class DurationValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_DURATION;

    /**
     * Laufzeit in Sekunden
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = Double.MAX_VALUE, errorCode = 10205, message = "Ungültiger Sensorwert")
    private long duration = 0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public DurationValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Laufzeit zurück
     *
     * @return Laufzeit
     */
    public long getDuration() {
        return duration;
    }

    /**
     * setzt die Laufzeit
     *
     * @param duration Laufzeit
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * fügt die Laufzeit hinzu
     *
     * @param duration Laufzeit
     */
    public void pushDuration(long duration) {

        setDuration(duration);
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
