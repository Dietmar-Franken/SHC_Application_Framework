package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;

import java.time.LocalDateTime;

/**
 * Sensorwert
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface SensorValue extends AutomationElement {

    /**
     * gibt den Identifizierer zurück
     *
     * @return Identifizierer
     */
    public String getIdentifier();

    /**
     * setzt den Identifizierer
     *
     * @param identifier Identifizierer
     */
    public void setIdentifier(String identifier);

    /**
     * gibt an ob der Sensorwert ein Systemwert ist (kann nicht gelöscht werden)
     *
     * @return true wenn Systemwert
     */
    boolean isSystemValue();

    /**
     * setzt den Sensorwert als Systemwert
     *
     * @param systemValue Systemwert
     */
    void setSystemValue(boolean systemValue);

    /**
     * gibt die Zeit des letzten Pushs zurück
     *
     * @return Zeit
     */
    LocalDateTime getLastPushTime();

    /**
     * setzt die Zeit des letzen Pushs
     *
     * @param lastPushTime Zeit
     */
    void setLastPushTime(LocalDateTime lastPushTime);
}
