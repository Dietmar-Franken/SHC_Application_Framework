package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Temperatur
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class TemperatureValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_TEMPERATURE;

    /**
     * Temperatur in °C
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = -273.15, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 100_000, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double temperature = 0.0;

    /**
     * Offset in °K
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = -20, errorCode = 10206, message = "Ungültiger Offset")
    @ValidateMax(value = 20, errorCode = 10206, message = "Ungültiger Offset")
    private double offset = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public TemperatureValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Temperatur zurück
     *
     * @return Temperatur
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * gibt die Temperatur mit Offset zurück
     *
     * @return Temperatur mit Offset
     */
    public double getTemperatureWithOffset() {
        return this.temperature + this.offset;
    }

    /**
     * setzt die Temperatur
     *
     * @param temperature Temperatur
     */
    public void setTemperature(double temperature) {

        this.temperature = temperature;
    }

    /**
     * fügt eine neue Temperatur ein
     *
     * @param temperature Temperatur
     */
    public void pushTemperature(double temperature) {

        setTemperature(temperature);
        setLastPushTime(LocalDateTime.now());
    }

    /**
     * gibt das Offset zurück
     *
     * @return Offset
     */
    public double getOffset() {
        return this.offset;
    }

    /**
     * setzt das Offset
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
