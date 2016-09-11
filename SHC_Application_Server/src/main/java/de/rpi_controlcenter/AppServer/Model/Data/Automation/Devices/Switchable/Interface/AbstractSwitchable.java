package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.AbstractAutomationElement;

import java.time.LocalDateTime;

/**
 * schaltbares ELement
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractSwitchable extends AbstractAutomationElement implements Switchable {

    /**
     * @param id ID
     * @param name Name
     */
    public AbstractSwitchable(String id, String name) {

        setId(id);
        setName(name);
    }

    /**
     * Status
     */
    private State state = State.OFF;

    /**
     * Zeitpunkt des letzten Schaltvorganges
     */
    private LocalDateTime lastToggleTime;

    /**
     * gibt den aktuellen Status zurück
     *
     * @return Status
     */
    public State getState() {
        return state;
    }

    /**
     * setzt den aktuellen Status
     *
     * @param state Status
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * gibt die Zeit des letzten Schaltvorgans zurück
     *
     * @return Zeit
     */
    @Override
    public LocalDateTime getLastToggleTime() {
        return this.lastToggleTime;
    }

    /**
     * setzt die Zeit des letzen Schaltvorganges
     *
     * @param lastToggleTime Zeit
     */
    @Override
    public void setLastToggleTime(LocalDateTime lastToggleTime) {
        this.lastToggleTime = lastToggleTime;
    }
}
