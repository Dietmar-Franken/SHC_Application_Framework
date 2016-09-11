package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Switchable.Interface.AbstractDoubleSwitchable;

/**
 * AVM Steckdose
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class AvmSocket extends AbstractDoubleSwitchable {

    private Type type = Type.SWITCHABLE_AVM_SOCKET;

    /**
     * Identifizierung
     */
    private String identifier;

    /**
     * Sensorwerte der Steckdose
     */
    private String tempSensorId = "";
    private String powerSensorId = "";
    private String energySensorId = "";

    /**
     * @param id ID
     * @param name Name
     */
    public AvmSocket(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die Identifizierung der Steckdose zurück
     *
     * @return Identifizierung
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * setzt die Identifizierung der Steckdose
     *
     * @param identifier Identifizierung
     */
    public void setIdentifier(String identifier) {

        Preconditions.checkNotNull(identifier);
        Preconditions.checkArgument(identifier.matches("^[0-9 ]{10,15}$"), "Ungültige Identifizierung %s", identifier);
        this.identifier = identifier.replace(" ", "");
    }

    /**
     * gibt den Hash des Temperatur Sensorwertes zurück
     *
     * @return Hash vom Sensorwert
     */
    public String getTempSensorId() {
        return tempSensorId;
    }

    /**
     * setzt den Hash des Temperatur Sensorwertes
     *
     * @param tempSensorId Hash vom Sensorwert
     */
    public void setTempSensorId(String tempSensorId) {

        Preconditions.checkNotNull(tempSensorId);
        Preconditions.checkArgument(tempSensorId.matches("^[a-fA-F0-9]{40}$"), "Ungültige ID %s (SAH-1 Prüfsumme erwartet)", tempSensorId);
        this.tempSensorId = tempSensorId;
    }

    /**
     * gibt den Hash des Verbrauchs Sensorwertes zurück
     *
     * @return Hash vom Sensorwert
     */
    public String getPowerSensorId() {
        return powerSensorId;
    }

    /**
     * setzt den Hash des Verbrauchs Sensorwertes
     *
     * @param powerSensorId Hash vom Sensorwert
     */
    public void setPowerSensorId(String powerSensorId) {

        Preconditions.checkNotNull(powerSensorId);
        Preconditions.checkArgument(powerSensorId.matches("^[a-fA-F0-9]{40}$"), "Ungültige ID %s (SAH-1 Prüfsumme erwartet)", powerSensorId);
        this.powerSensorId = powerSensorId;
    }

    /**
     * gibt den Hash des Energie Sensorwertes zurück
     *
     * @return Hash vom Sensorwert
     */
    public String getEnergySensorId() {
        return energySensorId;
    }

    /**
     * setzt den Hash des Energie Sensorwertes
     *
     * @param energySensorId Hash vom Sensorwert
     */
    public void setEnergySensorId(String energySensorId) {

        Preconditions.checkNotNull(energySensorId);
        Preconditions.checkArgument(energySensorId.matches("^[a-fA-F0-9]{40}$"), "Ungültige ID %s (SAH-1 Prüfsumme erwartet)", energySensorId);
        this.energySensorId = energySensorId;
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
