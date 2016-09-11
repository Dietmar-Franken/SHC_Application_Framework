package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Energieverbrauch
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class EnergyValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_ENERGY;

    /**
     * Energieverbrauch in Wh
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = Double.MAX_VALUE, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double energy = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public EnergyValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt den Energieverbrauch zurück
     *
     * @return Energieverbrauch
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * setzt den Energieverbrauch
     *
     * @param energy Energieverbrauch
     */
    public void setEnergy(double energy) {

        this.energy = energy;
    }

    /**
     * fügt den Energieverbrauch hinzu
     *
     * @param energy Energieverbrauch
     */
    public void pushEnergy(double energy) {

        setEnergy(energy);
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
