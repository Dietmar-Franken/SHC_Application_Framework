package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AutomationElement;

import java.time.LocalDateTime;

/**
 * schaltbares Element
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface Switchable extends AutomationElement {

    /**
     * gibt den aktuellen Status zurück
     *
     * @return Status
     */
    State getState();

    /**
     * setzt den aktuellen Status
     *
     * @param state Status
     */
    void setState(State state);

    /**
     * gibt die Zeit des letzten Schaltvorgans zurück
     *
     * @return Zeit
     */
    LocalDateTime getLastToggleTime();

    /**
     * setzt die Zeit des letzen Schaltvorganges
     *
     * @param lastToggleTime Zeit
     */
    void setLastToggleTime(LocalDateTime lastToggleTime);
}
