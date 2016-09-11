package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractDoubleSwitchable;

/**
 * Script mit an/aus Funktion
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class ScriptDouble extends AbstractDoubleSwitchable {

    private Type type = Type.SWITCHABLE_SCRIPT_DOUBLE;

    /**
     * Befehle
     */
    private String onCommand;
    private String offCommand;

    /**
     * Schaltserver
     */
    private String switchServerId;

    /**
     * @param id ID
     * @param name Name
     */
    public ScriptDouble(String id, String name) {
        super(id, name);
    }

    /**
     * gibt den Ausschaltbefehl zurück
     *
     * @return Ausschaltbefehl
     */
    public String getOffCommand() {
        return offCommand;
    }

    /**
     * setzt den Ausschaltbefehl
     *
     * @param offCommand Ausschaltbefehl
     */
    public void setOffCommand(String offCommand) {

        Preconditions.checkNotNull(offCommand);
        Preconditions.checkArgument(offCommand.length() >= 3, "Ungültiger Befehl %s", offCommand);
        this.offCommand = offCommand;
    }

    /**
     * gibt den Einschaltbefehl zurück
     *
     * @return Einschaltbefehl
     */
    public String getOnCommand() {
        return onCommand;
    }

    /**
     * setzt den Einschaltbefehl
     *
     * @param onCommand Einschaltbefehl
     */
    public void setOnCommand(String onCommand) {

        Preconditions.checkNotNull(onCommand);
        Preconditions.checkArgument(onCommand.length() >= 3, "Ungültiger Befehl %s", onCommand);
        this.onCommand = onCommand;
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
