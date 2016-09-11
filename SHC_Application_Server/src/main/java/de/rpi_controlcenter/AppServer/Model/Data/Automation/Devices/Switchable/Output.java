package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractDoubleSwitchable;

/**
 * Ausgang
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class Output extends AbstractDoubleSwitchable {

    private Type type = Type.SWITCHABLE_OUTPUT;

    /**
     * Schaltserver ID
     */
    private String switchServerId = "";

    /**
     * Pin
     */
    private int pin;

    /**
     * @param id ID
     * @param name Name
     */
    public Output(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die Schaltserver ID zurück
     *
     * @return Schaltserver ID
     */
    public String getSwitchServerId() {
        return switchServerId;
    }

    /**
     * setzt die Schaltserver ID
     *
     * @param switchServerId Schaltserver ID
     */
    public void setSwitchServerId(String switchServerId) {

        Preconditions.checkNotNull(switchServerId);
        Preconditions.checkArgument(switchServerId.matches("^[a-fA-F0-9]{40}$"), "Ungültige ID %s (SAH-1 Prüfsumme erwartet)", switchServerId);
        this.switchServerId = switchServerId;
    }

    /**
     * gibt den GPIO Pin zurück
     *
     * @return GPIO Pin
     */
    public int getPin() {
        return pin;
    }

    /**
     * setzt den GPIO Pin
     *
     * @param pin GPIO Pin
     */
    public void setPin(int pin) {

        Preconditions.checkNotNull(pin);
        Preconditions.checkArgument(pin >= 0 && pin <= 100, "Ungültiger Pin %i", pin);
        this.pin = pin;
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
