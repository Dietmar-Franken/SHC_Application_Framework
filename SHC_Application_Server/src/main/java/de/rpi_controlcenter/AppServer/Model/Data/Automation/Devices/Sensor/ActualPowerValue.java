package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Aktueller Energieverbrauch
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class ActualPowerValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_ACTUAL_POWER;

    /**
     * Momentaner Energieverbrauch in Watt
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double actualPower = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public ActualPowerValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt den aktuellen Energieverbrauch zurück
     *
     * @return Energieverbrauch in W
     */
    public double getActualPower() {
        return this.actualPower;
    }

    /**
     * setzt den aktuellen Energieverbrauch
     *
     * @param actualPower aktuellen Energieverbrauch
     */
    public void setActualPower(double actualPower) {

        this.actualPower = actualPower;
    }

    /**
     * fügt einen aktuellen Energieverbrauch
     *
     * @param actualPower Energieverbrauch in W
     */
    void pushActualPower(double actualPower) {

        setActualPower(actualPower);
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
