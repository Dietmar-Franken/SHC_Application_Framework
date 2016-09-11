package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractSingleSwitchable;

/**
 * Neustrat/Herunterfahren
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class RebootShutdown extends AbstractSingleSwitchable {

    private Type type = Type.SWITCHABLE_REBOOT_SHUTDOWN;

    enum Function {

        @SerializedName("REBOOT")
        REBOOT,
        @SerializedName("SHUTDOWN")
        SHUTDOWN
    }

    /**
     * Schaltserver
     */
    private String switchServerId;

    /**
     * Funktion
     */
    private Function function = Function.REBOOT;

    /**
     * @param id ID
     * @param name Name
     */
    public RebootShutdown(String id, String name) {
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
