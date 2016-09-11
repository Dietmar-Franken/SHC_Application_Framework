package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface;

/**
 * schaltbares Element mit 2 Schaltmöglichkeiten (an/aus)
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public interface DoubleSwitchable extends Switchable {

    /**
     * gibt an ob die Invertierung aktiviert/deaktiviert ist
     *
     * @return aktiviert/deaktiviert
     */
    boolean isInverse();

    /**
     * aktiviert/deaktiviert die Invertierung
     *
     * @param inverse aktiviert/deaktiviert
     */
    void setInverse(boolean inverse);

    /**
     * Aktion die bei Betätigung des "an" Buttons ausgeführt wird
     */
    void updateTriggerOn();

    /**
     * Aktion die bei Betätigung des "aus" Buttons ausgeführt wird
     */
    void updateTriggerOff();
}
