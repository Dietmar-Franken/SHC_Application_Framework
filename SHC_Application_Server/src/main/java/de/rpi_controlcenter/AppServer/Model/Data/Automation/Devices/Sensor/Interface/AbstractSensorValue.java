package de.rpi_controlcenter.AppServer.Model.Data.Automation.Devices.Sensor.Interface;

import com.google.common.base.Preconditions;
import de.rpi_controlcenter.AppServer.Model.Data.Automation.AbstractAutomationElement;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidatePattern;

import java.time.LocalDateTime;

/**
 * Standardimplementierung
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public abstract class AbstractSensorValue extends AbstractAutomationElement implements SensorValue {

    /**
     * Identifizierer
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    @ValidatePattern(value = "^[a-zA-Z0-9]{3,50}$", errorCode = 10008, message = "Ungültige Identifizierung")
    private String identifier = "";

    /**
     * Systemwert
     */
    private boolean systemValue = false;

    /**
     * Zeitpunkt des letzten Werte Pushes
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private LocalDateTime lastPushTime;

    /**
     * @param id ID
     * @param identifier Identifizierung
     * @param name Name
     */
    public AbstractSensorValue(String id, String identifier, String name) {

        setId(id);
        setIdentifier(identifier);
        setName(name);
    }

    /**
     * gibt den Identifizierer zurück
     *
     * @return Identifizierer
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * setzt den Identifizierer
     *
     * @param identifier Identifizierer
     */
    public void setIdentifier(String identifier) {

        Preconditions.checkNotNull(identifier);
        Preconditions.checkArgument(identifier.matches("^[a-zA-Z0-9]{3,50}$"), "Ungültige Identifizierung %s", identifier);
        this.identifier = identifier;
    }

    /**
     * gibt an ob der Sensorwert ein Systemwert ist (kann nicht gelöscht werden)
     *
     * @return true wenn Systemwert
     */
    @Override
    public boolean isSystemValue() {
        return this.systemValue;
    }

    /**
     * setzt den Sensorwert als Systemwert
     *
     * @param systemValue Systemwert
     */
    @Override
    public void setSystemValue(boolean systemValue) {
        this.systemValue = systemValue;
    }

    /**
     * gibt die Zeit des letzten Pushs zurück
     *
     * @return Zeit
     */
    @Override
    public LocalDateTime getLastPushTime() {
        return this.lastPushTime;
    }

    /**
     * setzt die Zeit des letzen Pushs
     *
     * @param lastPushTime Zeit
     */
    @Override
    public void setLastPushTime(LocalDateTime lastPushTime) {
        this.lastPushTime = lastPushTime;
    }
}
