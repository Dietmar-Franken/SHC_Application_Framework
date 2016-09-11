package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;
import de.rpi_controlcenter.Util.Validation.Annotation.ValidateNotNull;

import java.time.LocalTime;

/**
 * Bedingung Zeit
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class TimeCondition extends AbstractCondition {

    private Type type = Type.CONDITION_TIME;

    /**
     * Start & End-Zeit
     */
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private LocalTime startTime;
    @ValidateNotNull(errorCode = 10000, message = "Das Feld %s ist Null, sollte aber nicht")
    private LocalTime endTime;

    /**
     * @param id   ID
     * @param name Name
     */
    public TimeCondition(String id, String name) {
        super(id, name);
    }

    /**
     * gibt die Startzeit zurück
     *
     * @return Startzeit
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * setzt die Startzeit
     *
     * @param startTime Startzeit
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * gibt die Endzeit zurück
     *
     * @return Endzeit
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * setzt die Endzeit
     *
     * @param endTime Endzeit
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
