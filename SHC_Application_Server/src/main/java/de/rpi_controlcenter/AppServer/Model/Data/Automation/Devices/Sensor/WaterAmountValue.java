package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Wassermenge
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class WaterAmountValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_WATER_AMOUNT;

    /**
     * Wassermenge in Liter
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = Double.MAX_VALUE, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double waterAmount = 0.0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public WaterAmountValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt die Wassermenge zurück
     *
     * @return Wassermenge
     */
    public double getWaterAmount() {
        return waterAmount;
    }

    /**
     * setzt die Wassermenge
     *
     * @param waterAmount Wassermenge
     */
    public void setWaterAmount(double waterAmount) {

        this.waterAmount = waterAmount;
    }

    /**
     * addiert eine Wassermenge hinzu
     *
     * @param waterAmount Wassermenge
     */
    public void pushWaterAmount(double waterAmount) {

        setWaterAmount(waterAmount);
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
