package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractDoubleSwitchable;

/**
 * Virtuelle Steckdose
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class VirtualSocket extends AbstractDoubleSwitchable {

    private Type type = Type.SWITCHABLE_VIRTUAL_SOCKET;

    /**
     * @param id ID
     * @param name Name
     */
    public VirtualSocket(String id, String name) {
        super(id, name);
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
