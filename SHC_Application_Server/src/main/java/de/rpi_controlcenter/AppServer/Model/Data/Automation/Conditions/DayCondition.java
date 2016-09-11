package de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions;

import de.rpi_controlcenter.AppServer.Model.Data.Automation.Conditions.Interface.AbstractCondition;

/**
 * Bedingung Tag (zwischen Sonnenaufgang und Sonnenuntergang
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class DayCondition extends AbstractCondition {

    private Type type = Type.CONDITION_DAY;

    /**
     * @param id ID
     * @param name Name
     */
    public DayCondition(String id, String name) {
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
