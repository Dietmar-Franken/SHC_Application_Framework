package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Gasmenge
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class GasAmountValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_GAS_AMOUNT;

    /**
     * Gasmenge in Liter
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = Double.MAX_VALUE, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double gasAmount = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public GasAmountValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Gasmenge zurück
     *
     * @return Gasmenge
     */
    public double getGasAmount() {
        return gasAmount;
    }

    /**
     * setzt die Gasmenge
     *
     * @param gasAmount Gasmenge
     */
    public void setGasAmount(double gasAmount) {

        this.gasAmount = gasAmount;
    }

    /**
     * fügt eine Gasmenge hinzu
     *
     * @param gasAmount Gasmenge
     */
    public void pushGasAmount(double gasAmount) {

        setGasAmount(gasAmount);
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
