package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface.AbstractSensorValue;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMax;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateMin;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalDateTime;

/**
 * Batterie Ladestand
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class BatteryLevelValue extends AbstractSensorValue {

    private Type type = Type.SENSORVALUE_BATTERY_LEVEL;

    /**
     * Ladezustand in %
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidateMin(value = 0, errorCode = 10205, message = "Ungültiger Sensorwert")
    @ValidateMax(value = 100, errorCode = 10205, message = "Ungültiger Sensorwert")
    private double batteryLevel = 0;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public BatteryLevelValue(String id, String identifier, String name) {
        super(id, identifier, name);
    }

    /**
     * gibt den Ladezustand zurück
     *
     * @return Ladezustand
     */
    public double getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * setzt den Ladezustand
     *
     * @param batteryLevel Ladezustand
     */
    public void setBatteryLevel(double batteryLevel) {

        this.batteryLevel = batteryLevel;
    }

    /**
     * fügt den Ladezustand hinzu
     *
     * @param batteryLevel Ladezustand
     */
    public void pushBatteryLevel(int batteryLevel) {

        setBatteryLevel(batteryLevel);
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
