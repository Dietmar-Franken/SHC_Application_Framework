package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractSingleSwitchable;

/**
 * Script mit nur einem Befehl
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class ScriptSingle extends AbstractSingleSwitchable {

    private Type type = Type.SWITCHABLE_SCRIPT_SINGLE;

    /**
     * Befehle
     */
    private String command;

    /**
     * Schaltserver
     */
    private String switchServerId;

    /**
     * @param id ID
     * @param name Name
     */
    public ScriptSingle(String id, String name) {
        super(id, name);
    }

    /**
     * gibt den Einschaltbefehl zurück
     *
     * @return Einschaltbefehl
     */
    public String getCommand() {
        return command;
    }

    /**
     * setzt den Einschaltbefehl
     *
     * @param command Einschaltbefehl
     */
    public void setCommand(String command) {

        Preconditions.checkNotNull(command);
        Preconditions.checkArgument(command.length() >= 3, "Ungültiger Befehl %s", command);
        this.command = command;
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
     * gibt den Typ des Elementes zurück
     *
     * @return Typ ID
     */
    @Override
    public Type getType() {
        return type;
    }
}
