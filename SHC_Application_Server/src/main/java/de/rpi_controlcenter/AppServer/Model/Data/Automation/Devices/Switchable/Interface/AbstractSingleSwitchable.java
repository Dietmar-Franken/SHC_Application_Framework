package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface;

import java.time.LocalDateTime;

/**
 * schaltbares Element mit einer Schaltmögkichkeit
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractSingleSwitchable extends AbstractSwitchable implements SingleSwitchable {

    /**
     * @param id ID
     * @param name Name
     */
    public AbstractSingleSwitchable(String id, String name) {
        super(id, name);
    }

    /**
     * Aktion die bei Betätigung des "an" Buttons ausgeführt wird
     */
    @Override
    public void updateTriggerOn() {

        setLastToggleTime(LocalDateTime.now());
    }
}
