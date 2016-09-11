package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;

/**
 * Bedingung Kalenderwoche
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class CalendarWeekCondition extends AbstractCondition {

    private Type type = Type.CONDITION_CALENDAR_WEEK;

    /**
     * @param id ID
     * @param name Name
     */
    public CalendarWeekCondition(String id, String name) {
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
