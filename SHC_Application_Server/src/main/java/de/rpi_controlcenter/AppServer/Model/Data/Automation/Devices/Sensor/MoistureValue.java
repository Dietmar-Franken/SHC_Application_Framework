package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Feuchtigkeit
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class MoistureValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_MOISTURE;

    /**
     * Feuchtigkeit in %
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 100, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double moisture = 0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public MoistureValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Feuchtigkeit zurück
     *
     * @return Feuchtigkeit
     */
    public double getMoisture() {
        return moisture;
    }

    /**
     * setzt die Feuchtigkeit
     *
     * @param moisture Feuchtigkeit
     */
    public void setMoisture(double moisture) {

        this.moisture = moisture;
    }

    /**
     * fügt die Feuchtigkeit hinzu
     *
     * @param moisture Feuchtigkeit
     */
    public void pushMoisture(double moisture) {

        setMoisture(moisture);
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
