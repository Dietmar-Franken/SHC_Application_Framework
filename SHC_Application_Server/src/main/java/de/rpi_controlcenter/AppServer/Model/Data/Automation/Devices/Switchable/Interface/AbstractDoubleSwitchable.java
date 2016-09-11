package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface;

import java.time.LocalDateTime;

/**
 * schaltbares Element mit 2 Schaltmöglichkeiten (an/aus)
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractDoubleSwitchable extends AbstractSwitchable implements DoubleSwitchable {

    /**
     * Invertierung
     */
    private boolean inverse = false;

    /**
     * @param id ID
     * @param name Name
     */
    public AbstractDoubleSwitchable(String id, String name) {
        super(id, name);
    }

    /**
     * gibt an ob die Invertierung aktiviert/deaktiviert ist
     *
     * @return aktiviert/deaktiviert
     */
    public boolean isInverse() {
        return inverse;
    }

    /**
     * aktiviert/deaktiviert die Invertierung
     *
     * @param inverse aktiviert/deaktiviert
     */
    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    /**
     * Aktion die bei Betätigung des "an" Buttons ausgeführt wird
     */
    @Override
    public void updateTriggerOn() {

        setState(inverse ? State.OFF : State.ON);
        setLastToggleTime(LocalDateTime.now());
    }

    /**
     * Aktion die bei Betätigung des "aus" Buttons ausgeführt wird
     */
    @Override
    public void updateTriggerOff() {

        setState(inverse ? State.ON : State.OFF);
        setLastToggleTime(LocalDateTime.now());
    }
}
