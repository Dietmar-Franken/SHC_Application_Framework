package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface;

/**
 * schaltbares Element mit einer Schaltmögkichkeit
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface SingleSwitchable extends Switchable {

    /**
     * Aktion die bei Betätigung des "an" Buttons ausgeführt wird
     */
    void updateTriggerOn();
}
