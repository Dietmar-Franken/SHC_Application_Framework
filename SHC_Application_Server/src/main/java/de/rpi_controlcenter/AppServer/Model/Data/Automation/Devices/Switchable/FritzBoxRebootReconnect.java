package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractSingleSwitchable;

/**
 * Fritz!Box Neustart und Neu Verbinden
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class FritzBoxRebootReconnect extends AbstractSingleSwitchable {

    private Type type = Type.SWITCHABLE_FRITZ_BOX_REBOOT_RECONNECT;

    enum Function {

        @SerializedName("REBOOT")
        REBOOT,
        @SerializedName("RECONNECT_WAN")
        RECONNECT_WAN
    }

    /**
     * Funktion
     */
    private Function function = Function.RECONNECT_WAN;

    /**
     * @param id ID
     * @param name Name
     */
    public FritzBoxRebootReconnect(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die Funktion zurück
     *
     * @return Funktion
     */
    public Function getFunction() {
        return function;
    }

    /**
     * setzt die Funktion
     *
     * @param function Funktion
     */
    public void setFunction(Function function) {

        Preconditions.checkNotNull(function);
        this.function = function;
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
