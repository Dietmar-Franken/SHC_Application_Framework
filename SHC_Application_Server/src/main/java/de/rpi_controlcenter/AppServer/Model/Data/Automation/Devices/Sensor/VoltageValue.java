package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Spannung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class VoltageValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_VOLTAGE;

    /**
     * Spannung in mV
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 100_000_000, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double voltage = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public VoltageValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Spannung zurück
     *
     * @return Spannung
     */
    public double getVoltage() {
        return voltage;
    }

    /**
     * setzt die Spannung
     *
     * @param voltage Spannung
     */
    public void setVoltage(double voltage) {

        this.voltage = voltage;
    }

    /**
     * fügt die aktuelle Spannung hinzu
     *
     * @param voltage Spannung
     */
    public void pushVoltage(double voltage) {

        setVoltage(voltage);
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
