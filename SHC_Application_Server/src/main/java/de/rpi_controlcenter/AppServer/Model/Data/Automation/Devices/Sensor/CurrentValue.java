package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Strom
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class CurrentValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_CURRENT;

    /**
     * Strom in mA
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 500_000, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double current = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public CurrentValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt den Strom zurück
     *
     * @return Strom
     */
    public double getCurrent() {
        return current;
    }

    /**
     * setzt den Strom
     *
     * @param current Strom
     */
    public void setCurrent(double current) {

        this.current = current;
    }

    /**
     * fügt den aktuellen Strom hinzu
     *
     * @param current Strom
     */
    public void pushCurrent(double current) {

        setCurrent(current);
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
